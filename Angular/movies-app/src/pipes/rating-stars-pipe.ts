import { Pipe, PipeTransform } from '@angular/core';

/**
 * Converts a numeric rating (1-10) into a visual star string.
 *
 * Usage in template:
 *   {{ movie.rating | ratingStars }}            -> "★★★★★★★☆☆☆"
 *   {{ movie.rating | ratingStars:5 }}          -> scales to a 5-star scale
 *
 * @param value      The numeric rating from the Movie model
 * @param outOf      Optional max scale (default 10)
 */
@Pipe({
  name: 'ratingStars',
  standalone: true,    // standalone pipes don't need an NgModule - import directly into a component
  pure: true,          // pure pipes only re-run when inputs change (performance win)
})
export class RatingStarsPipe implements PipeTransform {
  transform(value: number | null | undefined, outOf: number = 10): string {
    // Defensive: handle null/undefined/NaN gracefully
    if (value == null || isNaN(value)) {
      return '';
    }

    // Clamp into valid range
    const clamped = Math.max(0, Math.min(outOf, Math.round(value)));
    const filled = '★'.repeat(clamped);
    const empty = '☆'.repeat(outOf - clamped);
    return filled + empty;
  }
}
