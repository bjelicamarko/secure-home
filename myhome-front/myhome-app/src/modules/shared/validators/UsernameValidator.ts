import { AbstractControl } from '@angular/forms';

export function UsernameValidator(control: AbstractControl) {
  if(!control.value) return null;

  const isValid = /^[a-zA-Z0-9]*$/.test(control.value);

  if(!isValid)
    return { invalidUsername: true };

  return null;
}