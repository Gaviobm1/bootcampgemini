/* eslint-disable @typescript-eslint/array-type */
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe } from 'src/lib/my-core/pipes/cadenas.pipe';
import { NifValidator } from '@my/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment.development';
import { Injectable } from '@angular/core';
import { NotificationService } from '@my/common-services';

export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiUrl;
  protected http = inject(HttpClient);
  constructor(entidad: string, protected option = {}) {
    this.baseUrl += entidad;
  }
  query(extras = {}): Observable<Array<T>> {
    return this.http.get<Array<T>>(
      this.baseUrl,
      Object.assign({}, this.option, extras)
    );
  }
  get(id: K, extras = {}): Observable<T> {
    return this.http.get<T>(
      `${this.baseUrl}/${id}`,
      Object.assign({}, this.option, extras)
    );
  }
  add(item: T, extras = {}): Observable<T> {
    return this.http.post<T>(
      this.baseUrl,
      item,
      Object.assign({}, this.option, extras)
    );
  }
  change(id: K, item: T, extras = {}): Observable<T> {
    return this.http.put<T>(
      `${this.baseUrl}/${id}`,
      item,
      Object.assign({}, this.option, extras)
    );
  }
  remove(id: K, extras = {}): Observable<T> {
    return this.http.delete<T>(
      `${this.baseUrl}/${id}`,
      Object.assign({}, this.option, extras)
    );
  }
}

@Injectable({
  providedIn: 'root',
})
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class PersonDaoService extends RESTDAOService<any, number> {
  constructor() {
    super('personas');
  }
}

@Component({
  selector: 'app-forumularios',
  imports: [FormsModule, ErrorMessagePipe, NifValidator],
  templateUrl: './forumularios.component.html',
  styleUrl: './forumularios.component.css',
})
export class ForumulariosComponent {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public elemento: any = {};

  public modo: 'add' | 'edit' = 'add';

  constructor(
    private dao: PersonDaoService,
    private notify: NotificationService
  ) {}

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
    this.dao.get(key).subscribe({
      next: (datos) => {
        this.elemento = datos;
        this.modo = 'edit';
      },
    });
  }

  cancel() {
    this.elemento = {};
  }

  send() {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.elemento).subscribe({
          next: () => this.cancel(),
          error: (err) => this.notify.add(JSON.stringify(err)),
        });
        //  alert(`POST ${JSON.stringify(this.elemento)}`)
        // this.cancel()
        break;
      case 'edit':
        this.dao.change(this.elemento.id, this.elemento).subscribe({
          next: () => this.cancel(),
          error: (err) => this.notify.add(JSON.stringify(err)),
        });
        // alert(`PUT ${JSON.stringify(this.elemento)}`)
        // this.cancel()
        break;
    }
  }
}
