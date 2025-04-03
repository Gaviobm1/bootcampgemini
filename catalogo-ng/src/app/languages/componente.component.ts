import {
  Component,
  forwardRef,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  signal,
  SimpleChanges,
} from '@angular/core';
import { LanguagesViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-languages',
  imports: [
    forwardRef(() => LanguagesAddComponent),
    forwardRef(() => LanguagesEditComponent),
    forwardRef(() => LanguagesViewComponent),
    forwardRef(() => LanguagesListComponent),
    RouterLink,
  ],
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrl: './componente.component.css',
})
export class LanguagesComponent implements OnInit, OnDestroy {
  constructor(protected vm: LanguagesViewModelService) {}
  public get VM(): LanguagesViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.pagedList(this.vm.CurrentPage, this.vm.PageSize);
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-languages-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [
    FormsModule,
    TypeValidator,
    ErrorMessagePipe,
    ButtonModule,
    CommonModule,
  ],
})
export class LanguagesAddComponent implements OnInit {
  protected selectedOption: string = '';

  constructor(protected vm: LanguagesViewModelService) {}
  public get VM(): LanguagesViewModelService {
    return this.vm;
  }

  removeActor(name: string) {
    this.vm.Elemento.languages.filter((actor: string) => actor !== name);
    console.log(this.vm.Elemento);
  }

  removeCategory(name: string) {
    this.vm.Elemento.categorias = this.vm.Elemento.categorias.filter(
      (category: string) => category !== name
    );
    console.log(this.vm.Elemento);
  }

  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-languages-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [
    FormsModule,
    TypeValidator,
    ErrorMessagePipe,
    ButtonModule,
    CommonModule,
  ],
})
export class LanguagesEditComponent implements OnInit, OnDestroy {
  protected selectedOption: string = '';
  private obs$?: Subscription;
  constructor(
    protected vm: LanguagesViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): LanguagesViewModelService {
    return this.vm;
  }

  removeActor(name: string) {
    this.vm.Elemento.languages = this.vm.Elemento.languages.filter(
      (actor: string) => actor !== name
    );
  }

  removeCategory(name: string) {
    this.vm.Elemento.categorias = this.vm.Elemento.categorias.filter(
      (category: string) => category !== name
    );
  }

  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe((params: ParamMap) => {
      const id = parseInt(params?.get('id') ?? '');
      if (id) {
        this.vm.edit(id);
      } else {
        this.router.navigate(['/404.html']);
      }
    });
  }
  ngOnDestroy(): void {
    this.obs$!.unsubscribe();
  }
}

@Component({
  selector: 'app-languages-list',
  imports: [RouterLink, PaginatorModule],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class LanguagesListComponent implements OnInit, OnDestroy {
  public rowsPerPage = signal<Array<number>>([5, 10, 20]);

  constructor(protected vm: LanguagesViewModelService) {}

  public get VM(): LanguagesViewModelService {
    return this.vm;
  }

  public onPageChange(event: any) {
    this.vm.pagedList(event.page, event.rows);
  }

  ngOnInit(): void {
    this.vm.pagedList(this.vm.CurrentPage, this.vm.PageSize);
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-languages-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [DatePipe],
})
export class LanguagesViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: LanguagesViewModelService,
    protected router: Router
  ) {}
  public get VM(): LanguagesViewModelService {
    return this.vm;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.id) {
      this.vm.view(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}

export const FILMS_COMPONENTES = [
  LanguagesComponent,
  LanguagesListComponent,
  LanguagesAddComponent,
  LanguagesEditComponent,
  LanguagesViewComponent,
];
