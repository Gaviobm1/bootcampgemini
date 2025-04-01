import { Routes } from '@angular/router';
import { HomeComponent, PageNotFoundComponent } from './main';
import { DemosComponent } from './demos/demos.component';
import { ForumulariosComponent } from './ejemplos/forumularios/forumularios.component';

export const routes: Routes = [
  { path: 'demo', component: DemosComponent, title: 'Demostración' },
  { path: 'formulario', component: ForumulariosComponent },
  {
    path: 'personas',
    children: [
      { path: '', component: ForumulariosComponent },
      { path: ':id/edit', component: ForumulariosComponent },
      { path: ':id', component: ForumulariosComponent },
      { path: 'add', component: ForumulariosComponent },
    ],
  },
  { path: '', component: HomeComponent },
  { path: '**', component: PageNotFoundComponent },
];
