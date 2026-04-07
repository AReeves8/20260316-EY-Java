import { Director } from "./Director";
import { Genre } from "./Genre";

// using type keyword here just as an example - would prefer interface in this situation normally
export type Movie = {
    id?: number;
    title: string, 
    rating: number;
    genre: Genre;
    director: Director;
}