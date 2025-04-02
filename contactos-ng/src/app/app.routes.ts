import { Routes } from '@angular/router';
import {
  ContactosAddComponent,
  ContactosEditComponent,
  ContactosListComponent,
  ContactosViewComponent,
} from '@my/contactos';

export const routes: Routes = [
  {
    path: 'contactos',
    children: [
      { path: '', component: ContactosListComponent },
      { path: 'add', component: ContactosAddComponent },
      { path: ':id/edit', component: ContactosEditComponent },
      { path: ':id', component: ContactosViewComponent },
      { path: ':id/:kk', component: ContactosViewComponent },
    ],
  },
];
