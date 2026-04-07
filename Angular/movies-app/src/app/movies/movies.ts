import { Component } from '@angular/core';
import { MovieService } from '../services/MovieService';
import { Movie } from '../types/Movie';
import { CardModule } from 'primeng/card';
import { ButtonModule } from "primeng/button";

@Component({
  selector: 'app-movies',   // custom HTML tag for the HTML template of this component
  imports: [CardModule, ButtonModule],
  templateUrl: './movies.html',
  styleUrl: './movies.css',
})
export class Movies {

  allMovies: Movie[] = [];


  // gives us a MovieSerice instance from the DI container
  constructor(
    private movieService: MovieService  // constructor injection
  ) {}

  // Initialization function for Angular components
  // runs as soon as the component is loaded to the DOM
  ngOnInit(): void {
    this.loadMovies();  // send request to GET movies when component loads
  }


  loadMovies(): void {

    // SUBSCRIBING to the observable so it will fire
    this.movieService.getMovies().subscribe({

      // subscribe takes in an object with some functions as params
      next: (data) => {
        this.allMovies = data;
      }, 

      error: (err) => {
        console.error(err);
      }
    });
  }

}
