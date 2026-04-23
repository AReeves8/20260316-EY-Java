// Vitest global setup for the movies-app test suite.
//
// jsdom (the DOM that Vitest runs tests inside) does not implement
// `window.matchMedia`, but several PrimeNG components — Menubar in
// particular — call it from `ngOnInit`. Stub it out so component tests
// that touch PrimeNG don't crash.
if (typeof window !== 'undefined' && typeof window.matchMedia !== 'function') {
  window.matchMedia = (query: string): MediaQueryList =>
    ({
      matches: false,
      media: query,
      onchange: null,
      addListener: () => {},
      removeListener: () => {},
      addEventListener: () => {},
      removeEventListener: () => {},
      dispatchEvent: () => false,
    }) as MediaQueryList;
}
