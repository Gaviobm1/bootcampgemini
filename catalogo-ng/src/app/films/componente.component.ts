import {
  Component,
  forwardRef,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FilmsViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { DatePipe } from '@angular/common';

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
    this.vm.pagedList(1, 5);
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
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class FilmsAddComponent implements OnInit {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-films-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class FilmsEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  constructor(
    protected vm: FilmsViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): FilmsViewModelService {
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
  selector: 'app-films-list',
  imports: [RouterLink],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class FilmsListComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.pagedList(1, 5);
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
