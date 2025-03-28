import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { LoggerService, ERROR_LEVEL } from '@my/core';

import { routes } from './app.routes';
import { environment } from 'src/environments/environment';

export const appConfig: ApplicationConfig = {
  providers: [
    LoggerService,
    { provide: ERROR_LEVEL, useValue: environment.ERROR_LEVEL },
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
  ],
};
