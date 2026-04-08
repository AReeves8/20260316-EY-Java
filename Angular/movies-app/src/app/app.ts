import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import {ButtonModule } from "primeng/button";
import {MenubarModule } from "primeng/menubar";

@Component({
  selector: 'app-root',

  // import the module for the corresponding PrimeNG component
  imports: [RouterOutlet, ButtonModule, MenubarModule],    
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  constructor(
    private router: Router
  ) {}
  
  navItems: MenuItem[] = [
    {label: "Movies", command: () => this.router.navigate(["/movies"])},
    {label: "Directors", command: () => this.router.navigate(["/directors"])}
  ]

}
