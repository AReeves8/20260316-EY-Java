import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ButtonModule } from "primeng/button";
import { Movies } from "./movies/movies";

@Component({
  selector: 'app-root',

  // import the module for the corresponding PrimeNG component
  imports: [RouterOutlet, ButtonModule, Movies],    
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('movies-app');
}
