import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoggerService } from '@my/core';
import { NotificationComponent } from './main/notification/notification.component';
import { DemosComponent } from './demos/demos.component';
import { NotificationModalComponent } from './main';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    NotificationComponent,
    DemosComponent,
    NotificationModalComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'world';

  constructor(out: LoggerService) {
    out.info('Es info');
    out.warn('Es warning');
    out.error('Es error');
    out.log('Es log');
    out.log(out.counter);
  }
}
