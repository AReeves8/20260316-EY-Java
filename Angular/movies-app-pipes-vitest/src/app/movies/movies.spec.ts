import { ComponentFixture, TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { Movies } from './movies';
import { MovieService } from '../services/MovieService';
import { DirectorService } from '../services/DirectorService';
import { Movie } from '../types/Movie';
import { Director } from '../types/Director';
import { Genre } from '../types/Genre';

// ---------------------------------------------------------------------------
// Test fixtures: small, hand-rolled objects that match our domain types.
// Keeping them at the top of the file makes the actual tests below much
// easier to read.
// ---------------------------------------------------------------------------
const nolan: Director = { id: 1, firstName: 'Christopher', lastName: 'Nolan' };
const villeneuve: Director = { id: 2, firstName: 'Denis', lastName: 'Villeneuve' };

const inception: Movie = {
  id: 10,
  title: 'Inception',
  rating: 9,
  genre: 'ACTION' as Genre,
  director: nolan,
};

const dune: Movie = {
  id: 11,
  title: 'Dune',
  rating: 8,
  genre: 'ACTION' as Genre,
  director: villeneuve,
};

describe('Movies component', () => {
  let component: Movies;
  let fixture: ComponentFixture<Movies>;

  // Service mocks. Each method returns an Observable so the component's
  // .subscribe() calls just work — no HttpClient required.
  let movieServiceMock: {
    getMovies: ReturnType<typeof vi.fn>;
    createNewMovie: ReturnType<typeof vi.fn>;
    updateMovie: ReturnType<typeof vi.fn>;
    deleteMovie: ReturnType<typeof vi.fn>;
  };
  let directorServiceMock: {
    getAllDirectors: ReturnType<typeof vi.fn>;
  };

  beforeEach(async () => {
    movieServiceMock = {
      getMovies: vi.fn().mockReturnValue(of([inception, dune])),
      createNewMovie: vi.fn().mockReturnValue(of(inception)),
      updateMovie: vi.fn().mockReturnValue(of(inception)),
      deleteMovie: vi.fn().mockReturnValue(of(void 0)),
    };
    directorServiceMock = {
      getAllDirectors: vi.fn().mockReturnValue(of([nolan, villeneuve])),
    };

    await TestBed.configureTestingModule({
      imports: [Movies],
      providers: [
        { provide: MovieService, useValue: movieServiceMock },
        { provide: DirectorService, useValue: directorServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Movies);
    component = fixture.componentInstance;
    // detectChanges() triggers ngOnInit, which is where the component
    // calls loadMovies() and loadDirectors().
    fixture.detectChanges();
    await fixture.whenStable();
  });

  // -------------------------------------------------------------------------
  // 1. Smoke test
  // -------------------------------------------------------------------------
  it('creates the component', () => {
    expect(component).toBeTruthy();
  });

  // -------------------------------------------------------------------------
  // 2. ngOnInit wiring — the component should pull movies + directors
  //    into its signals on load.
  // -------------------------------------------------------------------------
  describe('ngOnInit', () => {
    it('loads movies into the allMovies signal', () => {
      expect(movieServiceMock.getMovies).toHaveBeenCalledTimes(1);
      expect(component.allMovies()).toEqual([inception, dune]);
    });

    it('loads directors into the allDirectors signal', () => {
      expect(directorServiceMock.getAllDirectors).toHaveBeenCalledTimes(1);
      expect(component.allDirectors()).toEqual([nolan, villeneuve]);
    });

    it('builds a form with the expected controls', () => {
      expect(component.form).toBeTruthy();
      expect(Object.keys(component.form.controls)).toEqual([
        'title',
        'genre',
        'rating',
        'directorId',
      ]);
    });
  });

  // -------------------------------------------------------------------------
  // 3. Dialog open/close handlers
  // -------------------------------------------------------------------------
  describe('handleCreateMovie', () => {
    it('clears the selected movie, resets the form, and opens the dialog', () => {
      // pre-poison the form so we can prove it gets reset
      component.form.setValue({
        title: 'old',
        rating: 5,
        genre: 'ACTION' as Genre,
        directorId: nolan,
      });

      component.handleCreateMovie();

      expect(component.selectedMovie()).toBeNull();
      expect(component.form.value).toEqual({
        title: '',
        rating: 0,
        genre: null,
        directorId: null,
      });
      expect(component.showFormDialog()).toBe(true);
    });
  });

  describe('handleUpdateMovie', () => {
    it('stores the movie, pre-fills the form, and opens the dialog', () => {
      component.handleUpdateMovie(inception);

      expect(component.selectedMovie()).toBe(inception);
      expect(component.form.value).toEqual({
        title: 'Inception',
        rating: 9,
        genre: Genre[inception.genre as unknown as keyof typeof Genre],
        directorId: nolan,
      });
      expect(component.showFormDialog()).toBe(true);
    });
  });

  describe('handleDeleteMovie', () => {
    it('stores the movie and opens the delete confirmation dialog', () => {
      component.handleDeleteMovie(dune);

      expect(component.selectedMovie()).toBe(dune);
      expect(component.showDeleteDialog()).toBe(true);
    });
  });

  // -------------------------------------------------------------------------
  // 4. saveMovie — covers the validation guard and the update path.
  //
  // > Teaching aside: the create branch in saveMovie has a latent bug —
  // > `this.selectedMovie === null` compares the *signal function* to null
  // > (always false), instead of `this.selectedMovie() === null`. As a
  // > result the create branch is unreachable from saveMovie today. We
  // > leave that bug in place on purpose and use it as the homework
  // > exercise in Part 8.4 of the README. The tests below only assert
  // > behavior that the current code actually exhibits.
  // -------------------------------------------------------------------------
  describe('saveMovie', () => {
    it('does nothing when the form is invalid', () => {
      // form starts empty -> required validators fail
      component.saveMovie();

      expect(movieServiceMock.createNewMovie).not.toHaveBeenCalled();
      expect(movieServiceMock.updateMovie).not.toHaveBeenCalled();
    });

    it('calls updateMovie when a movie has been selected', () => {
      component.handleUpdateMovie(inception);

      component.saveMovie();

      expect(movieServiceMock.updateMovie).toHaveBeenCalledTimes(1);
      const [calledId, calledPayload] = movieServiceMock.updateMovie.mock.calls[0];
      expect(calledId).toBe(inception.id);
      expect(calledPayload.title).toBe('Inception');
      // dialog should close after a successful save
      expect(component.showFormDialog()).toBe(false);
    });

    it('replaces the matching movie in the allMovies signal after update', () => {
      const updated: Movie = { ...inception, title: 'Inception (Director\u2019s Cut)' };
      movieServiceMock.updateMovie.mockReturnValueOnce(of(updated));

      component.handleUpdateMovie(inception);
      component.saveMovie();

      expect(component.allMovies()).toEqual([updated, dune]);
    });
  });

  // -------------------------------------------------------------------------
  // 5. deleteMovie
  // -------------------------------------------------------------------------
  describe('deleteMovie', () => {
    it('does nothing when no movie is selected', () => {
      // selectedMovie defaults to null
      component.deleteMovie();
      expect(movieServiceMock.deleteMovie).not.toHaveBeenCalled();
    });

    it('removes the movie from allMovies and closes the dialog on success', () => {
      component.handleDeleteMovie(inception);

      component.deleteMovie();

      expect(movieServiceMock.deleteMovie).toHaveBeenCalledWith(inception.id);
      expect(component.allMovies()).toEqual([dune]);
      expect(component.showDeleteDialog()).toBe(false);
    });
  });
});
