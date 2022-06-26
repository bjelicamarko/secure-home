import { AbstractControl } from '@angular/forms';

export function EmailValidator(control: AbstractControl) {
  if (!control.value) return null;

  const isValid = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/.test(control.value);

  if (!isValid)
    return { invalidEmail: true };

  return null;
}