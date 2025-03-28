import { Component } from '@angular/core';
import { NotificationService } from '@my/common-services';

@Component({
  selector: 'app-demos',
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent {
  constructor(public vm: NotificationService) {}
}
