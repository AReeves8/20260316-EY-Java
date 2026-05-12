import { Component, signal } from '@angular/core';
import { MovieService } from '../services/MovieService';
import { Movie } from '../types/Movie';
import { CardModule } from 'primeng/card';
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";
import { InputNumberModule } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { SelectModule } from "primeng/select";
import { IftaLabelModule } from "primeng/iftalabel";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Genre } from '../types/Genre';
import { Director } from '../types/Director';
import { DirectorService } from '../services/DirectorService';
import { Title } from '@angular/platform-browser';
import { DeleteConfirmationModal } from '../components/delete-confirmation-modal/delete-confirmation-modal';

@Component({
  selector: 'app-movies',   // custom HTML tag for the HTML template of this component
  imports: [CardModule, ButtonModule, DialogModule, 
    InputNumberModule, InputTextModule, SelectModule, IftaLabelModule, 
    ReactiveFormsModule, DeleteConfirmationModal
  ],
  templateUrl: './movies.html',
  styleUrl: './movies.css',
})
export class Movies {

  allMovies = signal<Movie[]>([]);                // master list of all movies
  allDirectors = signal<Director[]>([]);          // all directors from server


  // Dialog state handling variables
  showFormDialog = signal<boolean>(false);
  showDeleteDialog = signal<boolean>(false);
  selectedMovie = signal<Movie | null>(null);

  genreOptions = Object.values(Genre);    // Gets all options for genres out of the enum


  // FormGroup is similar to FormData, just makes it really easier to get data out of forms in Angular
  // Also makes it easy to create Validators for validating form data
  form!: FormGroup;

  constructor(
    // constructor injection
    private movieService: MovieService,         // gives us a MovieService instance from the DI container      
    private directorService: DirectorService,
    private formBuilder: FormBuilder
  ) {}

  // Initialization function for Angular components
  // runs as soon as the component is loaded to the DOM
  ngOnInit(): void {
    this.loadMovies();      // send request to GET movies when component loads
    this.loadDirectors();   // send request to GET directors when component loads

    // use FormBuilder to create the form that the FormGroup will use
    this.form = this.formBuilder.group({
      title: ["", [Validators.required, Validators.minLength(2)]],
      genre: [null, [Validators.required]],
      rating: [0, [Validators.required, Validators.min(1), Validators.max(10)]],
      directorId: [null, [Validators.required]] 
    });

  }

  // pull movies from server
  loadMovies(): void {

    // SUBSCRIBING to the observable so it will fire
    this.movieService.getMovies().subscribe({

      // subscribe takes in an object with some functions as params
      next: (data) => {
        // call setter method of signal -> signal knows to tell Angular that a change ocurred
        this.allMovies.set(data);
      }, 

      error: (err) => {
        console.error(err);
      }
    });
  }

  // loading all the directors from the server be subscribing to the service observable
  loadDirectors(): void {
    this.directorService.getAllDirectors().subscribe({
      next: (data) => this.allDirectors.set(data), 
      error: (err) => console.error(err)
    });
  }

  // handling movie creation and updates
  saveMovie(){

    // exit function if form is invalid
    if(this.form.invalid) {
      return;
    }

    const {title, genre, rating, directorId: director} = this.form.value;
    const genreKey = Object.entries(Genre).find(([, val]) => val === genre)?.[0];   // returns the first key that matches our given value
    const payload: Movie = {
      title, 
      genre: genreKey as Genre,
      rating,
      director
    }

    // if no movie is slected, create. otherwise, update it.
    if(this.selectedMovie === null) {
      this.movieService.createNewMovie(payload).subscribe({
        next: (data) => {
          // update function gives you access to the current state of the signal
          // allows us to copy existing vaues into a new array and add the movie to it
          this.allMovies.update((currentList) => [...currentList, data]);
          
          // close dialog when movie is saved
          this.showFormDialog.set(false); 
        },
        error: (err) => {
          console.error(err);

          // close dialog if error occurs. 
          this.showFormDialog.set(false); 
        }
      });
    }
    else {
      payload.id = this.selectedMovie()!.id;    // ! tells TS that this value WILL NOT be null
      this.movieService.updateMovie(payload!.id!, payload).subscribe({
        next: (data) => {
          // update function gives you access to the current state of the signal
          // allows us to copy existing vaues into a new array and add the movie to it
          this.allMovies.update((currentList) => currentList.map(movie => movie.id === data.id ? data : movie));
          
          // close dialog when movie is saved
          this.showFormDialog.set(false); 
        },
        error: (err) => {
          console.error(err);

          // close dialog if error occurs. 
          this.showFormDialog.set(false); 
        }
      });
    }

    

  }

  // handling what needs to happen to open create form
  handleCreateMovie() {
    
    // track which movie was selected
    this.selectedMovie.set(null);

    // making sure form is cleared out
    this.form.setValue({
      title: "",
      rating: 0,
      genre: null,
      directorId: null
    })

    // open the dialog
    this.showFormDialog.set(true);
  }

  // handling what needs to happen to open update form
  handleUpdateMovie(movie: Movie) {

    console.log("SELECTED MOVIE:");
    console.log(movie);
    
    // track which movie was selected
    this.selectedMovie.set(movie);

    // pre-filling form with values
    this.form.setValue({
      title: movie.title,
      rating: movie.rating,
      genre: Genre[movie.genre as string as keyof typeof Genre],
      directorId: movie.director
    })

    // open the dialog
    this.showFormDialog.set(true);
  }

  // handling what needs to happen before deleting a movie
  handleDeleteMovie(movie: Movie) {
    this.selectedMovie.set(movie);
    this.showDeleteDialog.set(true);
  }

  deleteMovie() {
    if(this.selectedMovie() === null || this.selectedMovie()!.id === null) {
      return;
    }

    this.movieService.deleteMovie(this.selectedMovie()!.id!).subscribe({
      next: () => {
          this.allMovies.update((currentList) => currentList.filter(movie => movie.id !== this.selectedMovie()!.id));
          this.showDeleteDialog.set(false); 
        },
        error: (err) => {
          console.error(err);
          this.showDeleteDialog.set(false); 
        }
    })
  }
}
