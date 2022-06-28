import { AbstractControl } from '@angular/forms';

export function D15LengthValidator(control: AbstractControl) {
  if (!control.value) return null;

  const isValid = /^.{15,15}$/.test(control.value);

  if (!isValid)
    return { invalidLength: true };

  return null;
}