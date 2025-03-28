import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoggerService } from '@my/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
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
