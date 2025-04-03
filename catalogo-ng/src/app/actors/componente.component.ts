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
import { ActorsViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-actors',
  imports: [
    forwardRef(() => ActorsAddComponent),
    forwardRef(() => ActorsEditComponent),
    forwardRef(() => ActorsViewComponent),
    forwardRef(() => ActorsListComponent),
    RouterLink,
  ],
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrl: './componente.component.css',
})
export class ActorsComponent implements OnInit, OnDestroy {
  constructor(protected vm: ActorsViewModelService) {}
  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-add',
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
export class ActorsAddComponent implements OnInit {
  protected selectedOption: string = '';

  constructor(protected vm: ActorsViewModelService) {}
  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-edit',
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
export class ActorsEditComponent implements OnInit, OnDestroy {
  protected selectedOption: string = '';
  private obs$?: Subscription;
  constructor(
    protected vm: ActorsViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-list',
  imports: [RouterLink, PaginatorModule],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class ActorsListComponent implements OnInit, OnDestroy {
  public rowsPerPage = signal<Array<number>>([5, 10, 20]);

  constructor(protected vm: ActorsViewModelService) {}

  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [DatePipe],
})
export class ActorsViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: ActorsViewModelService, protected router: Router) {}
  public get VM(): ActorsViewModelService {
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
  ActorsComponent,
  ActorsListComponent,
  ActorsAddComponent,
  ActorsEditComponent,
  ActorsViewComponent,
];
