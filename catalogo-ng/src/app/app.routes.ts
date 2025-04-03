import { Routes } from '@angular/router';
import {
  FilmsComponent,
  FilmsAddComponent,
  FilmsEditComponent,
  FilmsListComponent,
  FilmsViewComponent,
} from './films';
import { PageNotFoundComponent } from '@my/main';
import {
  ActorsAddComponent,
  ActorsEditComponent,
  ActorsListComponent,
  ActorsViewComponent,
} from './actors/componente.component';
import {
  LanguagesAddComponent,
  LanguagesEditComponent,
  LanguagesListComponent,
  LanguagesViewComponent,
} from './languages/componente.component';
import {
  CategoriesAddComponent,
  CategoriesEditComponent,
  CategoriesListComponent,
  CategoriesViewComponent,
} from './categories';

export const routes: Routes = [
  {
    path: 'films',
    children: [
      { path: '', component: FilmsListComponent },
      { path: 'add', component: FilmsAddComponent },
      { path: ':id/edit', component: FilmsEditComponent },
      { path: ':id', component: FilmsViewComponent },
    ],
  },
  {
    path: 'actors',
    children: [
      { path: '', component: ActorsListComponent },
      { path: 'add', component: ActorsAddComponent },
      { path: ':id/edit', component: ActorsEditComponent },
      { path: ':id', component: ActorsViewComponent },
    ],
  },
  {
    path: 'languages',
    children: [
      { path: '', component: LanguagesListComponent },
      { path: 'add', component: LanguagesAddComponent },
      { path: ':id/edit', component: LanguagesEditComponent },
      { path: ':id', component: LanguagesViewComponent },
    ],
  },
  {
    path: 'categories',
    children: [
      { path: '', component: CategoriesListComponent },
      { path: 'add', component: CategoriesAddComponent },
      { path: ':id/edit', component: CategoriesEditComponent },
      { path: ':id', component: CategoriesViewComponent },
    ],
  },
  { path: '', redirectTo: 'films', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent },
];
