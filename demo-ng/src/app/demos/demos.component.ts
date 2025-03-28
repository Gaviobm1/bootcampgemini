import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService } from '@my/common-services';
import { Unsubscribable } from 'rxjs';
import { NotificationType } from '@my/common-services';

@Component({
  selector: 'app-demos',
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent implements OnInit, OnDestroy {
  private suscriptor: Unsubscribable | undefined;

  constructor(public vm: NotificationService) {}

  ngOnInit(): void {
    this.suscriptor = this.vm.Notificacion.subscribe((n) => {
      if (n.Type !== NotificationType.error) {
        return;
      }
      window.alert(`Suscripción: ${n.Message}`);
      this.vm.remove(this.vm.Listado.length - 1);
    });
  }

  ngOnDestroy(): void {
    if (this.suscriptor) {
      this.suscriptor.unsubscribe();
    }
  }
}
