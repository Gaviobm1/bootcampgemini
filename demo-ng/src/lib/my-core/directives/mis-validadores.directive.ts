import { Directive } from '@angular/core';
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms';
import { isIdentityCard } from 'validator';

export function nifValidator(
  control: AbstractControl
  // eslint-disable-next-line @typescript-eslint/consistent-indexed-object-style, @typescript-eslint/no-explicit-any
): { [key: string]: any } | null {
  if (!control.value) {
    return null;
  }
  return isIdentityCard(control.value.toString(), 'ES')
    ? null
    : { nif: 'No es un NIF válido' };
}

@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[nif][formControlName],[nif][formControl],[nif][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: NifValidator, multi: true },
  ],
})
export class NifValidator implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return nifValidator(control);
  }
}
