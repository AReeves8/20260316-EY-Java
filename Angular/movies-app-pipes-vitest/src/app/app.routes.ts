import { Routes } from '@angular/router';
import { Movies } from './movies/movies';
import { Directors } from './directors/directors';

export const routes: Routes = [
    {path: "movies", component: Movies},
    {path: "directors", component: Directors},
    {path: "", redirectTo: "movies", pathMatch: 'full'}
];
