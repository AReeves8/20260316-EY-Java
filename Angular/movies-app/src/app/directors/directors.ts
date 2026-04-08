import { Component, signal } from '@angular/core';
import { DirectorService } from '../services/DirectorService';
import { Director } from '../types/Director';
import { ButtonModule } from 'primeng/button';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';

@Component({
  selector: 'app-directors',
  imports: [TableModule, ButtonModule],
  templateUrl: './directors.html',
  styleUrl: './directors.css',
})
export class Directors {

  directors = signal<Director[]>([]);
  totalRecords = signal<number>(0);
  loading = signal<boolean>(false);

  constructor(
    private directorService: DirectorService
  ) {}

  ngOnInit() {
    this.loadDirectors();
  }


  loadDirectors(event? : TableLazyLoadEvent) {

    /**
     * TableLazyLoadEvent
     *    - first: index of first element currently in table
     *    - rows: current number of rows in the table
     */

    const page = event ? event?.first! / event?.rows! : 0;
    const size = event ? event?.rows! : 2;

    this.loading.set(true);

    this.directorService.getDirectorsPage(page, size).subscribe({
      next: (data) => {
        this.directors.set(data.content);
        this.totalRecords.set(data.totalElements);
        this.loading.set(false);
      },
      error: (error) => {
        console.error(error);
      }
    });

  }

}
