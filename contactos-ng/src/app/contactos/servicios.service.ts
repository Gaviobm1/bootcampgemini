import { HttpContextToken, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ContactosDAOService } from '../services/contactos-service/contactos-dao.service';
import { LoggerService } from '../../lib/my-core/services/logger.service';
import { NotificationService } from '../services/Notifications/notification.service';
import { Router } from '@angular/router';

export type ModoCRUD = 'list' | 'add' | 'edit' | 'view' | 'delete';

export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({
  providedIn: 'root',
})
export class ContactosViewModelService {
  protected modo: ModoCRUD = 'list';
  protected listado: Array<any> = [];
  protected elemento: any = {};
  protected idOriginal: any = null;
  protected listURL = '/contactos';

  constructor(
    protected notify: NotificationService,
    protected out: LoggerService,
    protected dao: ContactosDAOService,
    protected router: Router
  ) {}

  public get Modo(): ModoCRUD {
    return this.modo;
  }
  public get Listado(): Array<any> {
    return this.listado;
  }
  public get Elemento(): any {
    return this.elemento;
  }

  public list(): void {
    this.dao.query().subscribe({
      next: (data) => {
        this.listado = data;
        this.modo = 'list';
      },
      error: (err) => this.handleError(err),
    });
  }

  public add(): void {
    this.elemento = {};
    this.modo = 'add';
  }
  public edit(key: any): void {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.elemento = data;
        this.idOriginal = key;
        this.modo = 'edit';
      },
      error: (err) => this.handleError(err),
    });
  }

  public view(key: any): void {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.elemento = data;
        this.modo = 'view';
      },
      error: (err) => this.handleError(err),
    });
  }
  public delete(key: any): void {
    if (!window.confirm('¿Seguro?')) {
      return;
    }
    this.dao.remove(key).subscribe({
      next: () => this.list(),
      error: (err) => this.handleError(err),
    });
  }

  public cancel(): void {
    this.clear();
    this.router.navigateByUrl(this.listURL);
  }
  public send(): void {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.elemento).subscribe({
          next: () => this.cancel(),
          error: (err) => this.handleError(err),
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.elemento).subscribe({
          next: () => this.cancel(),
          error: (err) => this.handleError(err),
        });
        break;
      case 'view':
        this.cancel();
        break;
    }
  }

  clear() {
    this.elemento = {};
    this.idOriginal = undefined;
    this.listado = [];
  }

  handleError(err: HttpErrorResponse) {
    let msg = '';
    switch (err.status) {
      case 0:
        msg = err.message;
        break;
      case 404:
        this.router.navigateByUrl('/404.html');
        break;
      default:
        msg = `ERROR ${err.status}: ${err.error?.['title'] ?? err.statusText}.${
          err.error?.['detail'] ? ` Detalles: ${err.error['detail']}` : ''
        }`;
        break;
    }
    this.notify.add(msg);
  }
}
