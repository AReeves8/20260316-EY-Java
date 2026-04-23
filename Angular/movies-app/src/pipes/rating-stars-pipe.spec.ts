import { describe, it, expect, beforeEach } from 'vitest';
import { RatingStarsPipe } from './rating-stars-pipe';

/**
 * Pipes are PURE FUNCTIONS wrapped in a class. That makes them
 * the *easiest* thing in Angular to unit test - no TestBed needed.
 * Just `new` it up and call .transform().
 */
describe('RatingStarsPipe', () => {
  let pipe: RatingStarsPipe;

  beforeEach(() => {
    pipe = new RatingStarsPipe();
  });

  it('renders 7 filled and 3 empty stars for a rating of 7', () => {
    expect(pipe.transform(7)).toBe('★★★★★★★☆☆☆');
  });

  it('renders all empty stars for 0', () => {
    expect(pipe.transform(0)).toBe('☆☆☆☆☆☆☆☆☆☆');
  });

  it('renders all filled stars when value equals max', () => {
    expect(pipe.transform(10)).toBe('★★★★★★★★★★');
  });

  it('clamps values above the max scale', () => {
    expect(pipe.transform(99)).toBe('★★★★★★★★★★');
  });

  it('clamps negative values to zero', () => {
    expect(pipe.transform(-5)).toBe('☆☆☆☆☆☆☆☆☆☆');
  });

  it('rounds fractional ratings', () => {
    expect(pipe.transform(7.6)).toBe('★★★★★★★★☆☆');
  });

  it('supports a custom outOf scale', () => {
    expect(pipe.transform(3, 5)).toBe('★★★☆☆');
  });

  // Parameterized test - vitest's `it.each` is great for table-driven cases
  it.each([
    [null, ''],
    [undefined, ''],
    [NaN, ''],
  ])('returns empty string for %s', (input, expected) => {
    expect(pipe.transform(input as any)).toBe(expected);
  });
});
