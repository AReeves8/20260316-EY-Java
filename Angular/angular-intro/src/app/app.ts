import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';


// Decorators in Angular - not annotations
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  
  // define whatever functions and or properties you want to exist within the component

  // TypeScript is good at inferencing what type something is so you don't always need to impicitly define types
  message = "Hello World! This is Austin speaking."; 
  count = 0;
  isHighlighted = false;
  searchText = "";


  allMovies = [
    {
      id: 1, 
      title: "The Hunger Games",
      genre: "Sci-Fi",
      rating: 7, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    },
    {
      id: 2, 
      title: "Catching Fire",
      genre: "Sci-Fi",
      rating: 8, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    },
    {
      id: 3, 
      title: "Mockingjay",
      genre: "Sci-Fi",
      rating: 8, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    }
  ];



  increment() {
    this.count++;

    if(this.count > 5) {
      this.isHighlighted = true;
      this.searchText = "You've clicked a lot!"
    }
  }


}
