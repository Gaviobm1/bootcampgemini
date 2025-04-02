import {
  Component,
  forwardRef,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { ContactosViewModelService } from './servicios.service';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { Subscription } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-contactos',
  imports: [
    forwardRef(() => ContactosAddComponent),
    forwardRef(() => ContactosEditComponent),
    forwardRef(() => ContactosViewComponent),
    forwardRef(() => ContactosListComponent),
    RouterLink,
  ],
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrl: './componente.component.css',
})
export class ContactosComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) {}
  public get VM(): ContactosViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-contactos-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class ContactosAddComponent implements OnInit {
  constructor(protected vm: ContactosViewModelService) {}
  public get VM(): ContactosViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-contactos-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class ContactosEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  constructor(
    protected vm: ContactosViewModelService,
    protected route: ActivatedRoute,
    protected router: Router
  ) {}
  public get VM(): ContactosViewModelService {
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
  selector: 'app-contactos-list',
  imports: [RouterLink],
  templateUrl: './tmpl-list.component.html',
  styleUrl: './componente.component.css',
})
export class ContactosListComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) {}
  public get VM(): ContactosViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-contactos-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  imports: [DatePipe],
})
export class ContactosViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: ContactosViewModelService,
    protected router: Router
  ) {}
  public get VM(): ContactosViewModelService {
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

export const CONTACTOS_COMPONENTES = [
  ContactosComponent,
  ContactosListComponent,
  ContactosAddComponent,
  ContactosEditComponent,
  ContactosViewComponent,
];
