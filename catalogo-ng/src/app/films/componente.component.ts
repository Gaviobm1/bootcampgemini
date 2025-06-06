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
import { FilmsViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-films',
  imports: [
    forwardRef(() => FilmsAddComponent),
    forwardRef(() => FilmsEditComponent),
    forwardRef(() => FilmsViewComponent),
    forwardRef(() => FilmsListComponent),
    RouterLink,
  ],
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrl: './componente.component.css',
})
export class FilmsComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
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
  selector: 'app-films-add',
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
export class FilmsAddComponent implements OnInit {
  protected selectedOption: string = '';

  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  removeActor(name: string) {
    this.vm.Elemento.actors.filter((actor: string) => actor !== name);
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
  selector: 'app-films-edit',
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
export class FilmsEditComponent implements OnInit, OnDestroy {
  protected selectedOption: string = '';
  private obs$?: Subscription;
  constructor(
    protected vm: FilmsViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  removeActor(name: string) {
    this.vm.Elemento.actors = this.vm.Elemento.actors.filter(
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
  selector: 'app-films-list',
  imports: [RouterLink, PaginatorModule],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class FilmsListComponent implements OnInit, OnDestroy {
  public rowsPerPage = signal<Array<number>>([5, 10, 20]);

  constructor(protected vm: FilmsViewModelService) {}

  public get VM(): FilmsViewModelService {
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
  selector: 'app-films-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [DatePipe],
})
export class FilmsViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: FilmsViewModelService, protected router: Router) {}
  public get VM(): FilmsViewModelService {
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
  FilmsComponent,
  FilmsListComponent,
  FilmsAddComponent,
  FilmsEditComponent,
  FilmsViewComponent,
];
