import { AbstractControl } from '@angular/forms';

export function MinLengthValidator2(control: AbstractControl) {
  if (!control.value) return null;

  const isValid = /^.{2,2}$/.test(control.value);

  if (!isValid)
    return { invalidLength: true };

  return null;
}