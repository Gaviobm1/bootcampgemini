import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CONTACTOS_COMPONENTES } from '@my/contactos';
import { HttpClient } from '@angular/common/http';

@NgModule({
  declarations: [],
  imports: [CommonModule, CONTACTOS_COMPONENTES, HttpClient],
})
export class ContactosModule {}
