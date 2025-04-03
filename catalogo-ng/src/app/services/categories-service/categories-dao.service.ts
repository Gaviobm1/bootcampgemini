import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

export interface ICategory {
  categoryId?: number;
  name?: string;
}

export class Category implements ICategory {
  constructor(categoryId?: number, name?: string) {}
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
export class CategoriesDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('categories/v1');
  }

  page(
    page: number,
    rows: number = 20
  ): Observable<{
    page: number;
    pages: number;
    pageSize: number;
    rows: number;
    list: Category[];
  }> {
    return new Observable((subscriber) => {
      const url = `${this.baseUrl}?page=${page}&size=${rows}&sort=title%2Casc`;
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
