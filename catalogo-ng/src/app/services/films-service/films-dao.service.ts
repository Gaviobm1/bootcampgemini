import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

export interface IFilm {
  filmId?: number;
  title?: string;
  description?: string;
  releaseYear?: number;
  language?: string;
  originalLanguage?: string;
  actors?: string[];
  categorias?: string[];
}

export class Film implements IFilm {
  constructor(
    filmId?: number,
    title?: string,
    description?: string,
    releaseYear?: number,
    language?: string,
    originalLanguage?: string,
    actors?: string[],
    categorias?: string[]
  ) {}
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
    return this.http.put<T>(`${this.baseUrl}/${id}`, item, this.option);
  }
  remove(id: K): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option);
  }
}

@Injectable({
  providedIn: 'root',
})
export class FilmsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('films/v1');
  }

  page(
    page: number,
    rows: number = 20,
    mode: string
  ): Observable<{
    page: number;
    pages: number;
    rows: number;
    list: Film[];
  }> {
    return new Observable((subscriber) => {
      const url = `${this.baseUrl}?page=${page}&size=${rows}&sort=title%2Casc&mode=${mode}`;
      console.log(url);
      this.http.get<any>(url, this.option).subscribe({
        next: (data) =>
          subscriber.next({
            page: data.number,
            pages: data.totalPages,
            rows: data.totalElements,
            list: data.content,
          }),
        error: (err) => subscriber.error(err),
      });
    });
  }
}
