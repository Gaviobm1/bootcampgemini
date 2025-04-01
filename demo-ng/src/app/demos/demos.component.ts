import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '@my/common-services';
import { Unsubscribable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { ButtonDemoComponent } from '../main/button-demo/button-demo.component';
import { ForumulariosComponent } from '../ejemplos/forumularios/forumularios.component';

@Component({
  selector: 'app-demos',
  imports: [
    FormsModule,
    CommonModule,
    ButtonDemoComponent,
    ForumulariosComponent,
  ],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent {
  private fecha = new Date('2025-03-31');
  public readonly nombre = signal<string>('mundo');
  public readonly fontSize = signal<number>(16);
  public readonly listado = signal([
    { id: 1, nombre: 'Daryl' },
    { id: 2, nombre: 'tom' },
    { id: 3, nombre: 'JOSHA' },
    { id: 4, nombre: 'jacob' },
  ]);
  public readonly idActor = signal<number>(2);

  public result = signal<string>('');
  public visible = signal<boolean>(true);
  public invisible = computed<boolean>(() => !this.visible);
  public readonly estetica = signal({
    importante: true,
    urgente: true,
    error: false,
  });

  private suscriptor: Unsubscribable | undefined;

  constructor(public vm: NotificationService) {}

  public get Fecha(): string {
    return this.fecha.toISOString();
  }

  public set Fecha(value: string) {
    this.fecha = new Date(value);
  }

  saludar() {
    this.result.set(`Hola ${this.nombre()}`);
  }

  despide() {
    this.result.set(`Adios ${this.nombre()}`);
  }

  di(algo: string) {
    this.result.set(`Dice ${algo}`);
  }

  cambia() {
    this.visible.update((valor) => !valor);
    this.estetica.update((valor) => ({
      ...valor,
      importante: !valor.importante,
    }));
    this.estetica.update((valor) => ({ ...valor, error: !valor.error }));
  }

  add(actor: string) {
    const id = this.listado()[this.listado().length - 1].id + 1;
    this.listado.update((valor) => [...valor, { id, nombre: actor }]);
    this.idActor.set(id);
  }

  calcula(...args: number[]) {
    return args.reduce((a, b) => a + b, 0);
  }
}
