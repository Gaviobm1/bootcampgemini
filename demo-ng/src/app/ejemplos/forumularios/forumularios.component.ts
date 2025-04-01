import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-forumularios',
  imports: [FormsModule],
  templateUrl: './forumularios.component.html',
  styleUrl: './forumularios.component.css',
})
export class ForumulariosComponent {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public elemento: any = {};

  public modo: 'add' | 'edit' = 'add';

  add() {
    this.elemento = {};
    this.modo = 'add';
  }

  edit(key: number) {
    this.elemento = {
      id: key,
      nombre: 'Daryl',
      apellidos: 'McCormack',
      correo: 'daryl@actur.com',
      fAlta: '2025-01-01',
      edad: 32,
      nif: '12345678z',
    };
    this.modo = 'edit';
  }

  cancel() {
    this.elemento = {};
  }

  send() {
    switch (this.modo) {
      case 'add':
        alert(`POST ${JSON.stringify(this.elemento)}`);
        this.cancel();
        break;
      case 'edit':
        alert(`PUT ${JSON.stringify(this.elemento)}`);
        this.cancel();
        break;
    }
  }
}
