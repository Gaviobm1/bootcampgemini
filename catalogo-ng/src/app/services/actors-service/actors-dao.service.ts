import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

export interface IActor {
  id?: number;
  nombre?: string;
  apellidos?: string;
}

export class Actor implements IActor {
  constructor(id?: number, nombre?: string, apellidos?: string) {}
}

export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiURL;
  protected http: HttpClient = inject(HttpClient);
  constructor(entidad: string, protected option = {}) {
    this.baseUrl += entidad;
  }
  query(): Observable<Array<T>> {
    return this.http.get<Array<T>>(this.baseUrl, this.option);
  }
  get(id: K): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}/${id}`, this.option);
  }
  add(item: T): Observable<T> {
    return this.http.post<T>(this.baseUrl, item, this.option);
  }
  change(id: K, item: T): Observable<T> {
    console.log(item);
    return this.http.put<T>(`${this.baseUrl}/${id}`, item, this.option);
  }
  remove(id: K): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option);
  }
}

@Injectable({
  providedIn: 'root',
})
export class ActorsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('actors/v1');
  }

  page(
    page: number,
    rows: number = 20
  ): Observable<{
    page: number;
    pages: number;
    pageSize: number;
    rows: number;
    list: Actor[];
  }> {
    return new Observable((subscriber) => {
      const url = `${this.baseUrl}?page=${page}&size=${rows}`;
      this.http.get<any>(url, this.option).subscribe({
        next: (data) =>
          subscriber.next({
            page: data.page.number,
            pages: data.page.totalPages,
            rows: data.page.totalElements,
            pageSize: data.page.size,
            list: data.content,
          }),
        error: (err) => subscriber.error(err),
      });
    });
  }
}
