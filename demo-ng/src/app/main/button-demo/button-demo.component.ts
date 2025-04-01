import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-button-demo',
  imports: [ButtonModule],
  templateUrl: './button-demo.component.html',
  standalone: true,
  styleUrl: './button-demo.component.css',
})
export class ButtonDemoComponent {}
