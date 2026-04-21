import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { Director } from "../types/Director";
import { environment } from "../../environments/environments";

// only create types in an existing file, if you're only using that type in THIS file
export interface DirectorPage {
    content: Director[]
    totalElements: number
    totalPages: number
    size: number
    number: number
}



@Injectable({providedIn: "root"})
export class DirectorService {

    private readonly URL = `${environment.baseApiUrl}/directors`;

    constructor(
        private http: HttpClient
    ) {}

    // GET request for all directors
    getAllDirectors(): Observable<Director[]> {
        return this.http.get<Director[]>(this.URL)
            .pipe(
                catchError(() => throwError(() => new Error("Failed to load directors.")))
            )
    }

    // GET request for page of directors
    getDirectorsPage(page = 0, size = 2, sortBy = "id"): Observable<DirectorPage> {
        const params = new HttpParams()
            .set("page", page)
            .set("size", size)
            .set("sortBy", sortBy)

        return this.http.get<DirectorPage>(this.URL + "/page", {params})
            .pipe(
                catchError(() => throwError(() => new Error("Failed to load directors.")))
            )
    }
}