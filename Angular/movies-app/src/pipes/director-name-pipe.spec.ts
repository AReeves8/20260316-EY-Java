import { describe, it, expect, beforeEach } from 'vitest';
import { DirectorNamePipe } from './director-name-pipe';
import { Director } from '../app/types/Director';

describe('DirectorNamePipe', () => {
  let pipe: DirectorNamePipe;

  // Test fixture - a tiny helper to build a Director
  const makeDirector = (over: Partial<Director> = {}): Director =>
    ({ id: 1, firstName: 'Christopher', lastName: 'Nolan', ...over }) as Director;

  beforeEach(() => {
    pipe = new DirectorNamePipe();
  });

  it('formats first-last by default', () => {
    expect(pipe.transform(makeDirector())).toBe('Christopher Nolan');
  });

  it('formats last-first when requested', () => {
    expect(pipe.transform(makeDirector(), 'last-first')).toBe('Nolan, Christopher');
  });

  it('returns "Unknown Director" for null', () => {
    expect(pipe.transform(null)).toBe('Unknown Director');
  });

  it('returns "Unknown Director" for undefined', () => {
    expect(pipe.transform(undefined)).toBe('Unknown Director');
  });

  it('returns "Unknown Director" when both names blank', () => {
    expect(pipe.transform(makeDirector({ firstName: '', lastName: '' }))).toBe('Unknown Director');
  });

  it('handles missing first name gracefully', () => {
    expect(pipe.transform(makeDirector({ firstName: '' }))).toBe('Nolan');
  });

  it('handles missing last name gracefully', () => {
    expect(pipe.transform(makeDirector({ lastName: '' }))).toBe('Christopher');
  });

  it('trims whitespace on input names', () => {
    expect(pipe.transform(makeDirector({ firstName: '  Greta  ', lastName: '  Gerwig ' })))
      .toBe('Greta Gerwig');
  });
});
