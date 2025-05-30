import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoggerService } from '@my/core';
import { HeaderComponent, NotificationModalComponent } from './main';
import { FooterComponent } from './main/footer/footer.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    NotificationModalComponent,
    FooterComponent,
    HeaderComponent,
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
