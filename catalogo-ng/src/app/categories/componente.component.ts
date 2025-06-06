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
import { CategoriesViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-categories',
  imports: [
    forwardRef(() => CategoriesAddComponent),
    forwardRef(() => CategoriesEditComponent),
    forwardRef(() => CategoriesViewComponent),
    forwardRef(() => CategoriesListComponent),
    RouterLink,
  ],
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrl: './componente.component.css',
})
export class CategoriesComponent implements OnInit, OnDestroy {
  constructor(protected vm: CategoriesViewModelService) {}
  public get VM(): CategoriesViewModelService {
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
  selector: 'app-categories-add',
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
export class CategoriesAddComponent implements OnInit {
  protected selectedOption: string = '';

  constructor(protected vm: CategoriesViewModelService) {}
  public get VM(): CategoriesViewModelService {
    return this.vm;
  }

  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-categories-edit',
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
export class CategoriesEditComponent implements OnInit, OnDestroy {
  protected selectedOption: string = '';
  private obs$?: Subscription;
  constructor(
    protected vm: CategoriesViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): CategoriesViewModelService {
    return this.vm;
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
  selector: 'app-categories-list',
  imports: [RouterLink, PaginatorModule],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class CategoriesListComponent implements OnInit, OnDestroy {
  public rowsPerPage = signal<Array<number>>([5, 10, 20]);

  constructor(protected vm: CategoriesViewModelService) {}

  public get VM(): CategoriesViewModelService {
    return this.vm;
  }

  public onPageChange(event: any) {
    this.vm.pagedList(event.page, event.rows);
  }

  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-categories-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [DatePipe],
})
export class CategoriesViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: CategoriesViewModelService,
    protected router: Router
  ) {}
  public get VM(): CategoriesViewModelService {
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
  CategoriesComponent,
  CategoriesListComponent,
  CategoriesAddComponent,
  CategoriesEditComponent,
  CategoriesViewComponent,
];
