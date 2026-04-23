import { Pipe, PipeTransform } from '@angular/core';
import { Director } from '../app/types/Director';

/**
 * Formats a Director object as a display name.
 *
 * Usage:
 *   {{ movie.director | directorName }}              -> "Christopher Nolan"
 *   {{ movie.director | directorName:'last-first' }} -> "Nolan, Christopher"
 *
 * Demonstrates a pipe that takes an OBJECT input + a string argument.
 */
@Pipe({
  name: 'directorName',
  standalone: true,
})
export class DirectorNamePipe implements PipeTransform {
  transform(
    director: Director | null | undefined,
    format: 'first-last' | 'last-first' = 'first-last',
  ): string {
    if (!director) return 'Unknown Director';

    const first = director.firstName?.trim() ?? '';
    const last = director.lastName?.trim() ?? '';

    if (!first && !last) return 'Unknown Director';

    return format === 'last-first'
      ? `${last}, ${first}`.replace(/^, |, $/g, '')
      : `${first} ${last}`.trim();
  }
}
