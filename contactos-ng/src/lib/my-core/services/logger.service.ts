import { Injectable, Inject, InjectionToken, Optional } from '@angular/core';

export const ERROR_LEVEL = new InjectionToken<string>('ERROR_LEVEL');

@Injectable({
  providedIn: 'root',
})
export class LoggerService {
  private count: number = 0;
  private level: number = Number.MAX_VALUE;

  constructor(@Inject(ERROR_LEVEL) @Optional() level?: number) {
    if (level || level == 0) {
      this.level = level;
    }
  }

  public error(msg: string): void {
    if (this.level > 0) {
      console.error(msg);
      this.increment();
    }
  }

  public warn(msg: string): void {
    if (this.level > 1) {
      console.warn(msg);
      this.increment();
    }
  }

  public info(msg: string): void {
    if (this.level > 2) {
      if (console.info) {
        console.info(msg);
        this.increment();
      } else {
        console.log(msg);
        this.increment();
      }
    }
  }

  public log(msg: string): void {
    if (this.level > 3) {
      console.log(msg);
      this.increment();
    }
  }

  private increment(): void {
    this.count++;
  }

  get counter() {
    return this.count.toString();
  }
}
