import { Routes } from '@angular/router';
import {
  FilmsComponent,
  FilmsAddComponent,
  FilmsEditComponent,
  FilmsListComponent,
  FilmsViewComponent,
} from './films';

export const routes: Routes = [
  {
    path: 'films',
    children: [
      { path: '', component: FilmsListComponent },
      { path: 'add', component: FilmsAddComponent },
      { path: ':id/edit', component: FilmsEditComponent },
      { path: ':id?mode=details', component: FilmsViewComponent },
      { path: ':id/:kk', component: FilmsViewComponent },
    ],
  },
];
