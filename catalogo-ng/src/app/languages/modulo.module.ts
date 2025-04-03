import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FILMS_COMPONENTES } from '@my/films';
import { HttpClient } from '@angular/common/http';

@NgModule({
  declarations: [],
  imports: [CommonModule, FILMS_COMPONENTES, HttpClient],
})
export class FilmsModule {}
