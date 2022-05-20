import { AbstractControl } from '@angular/forms';

export function BasicValidator(control: AbstractControl) {
  if(!control.value) return null;

  const isValid = /^[a-zA-Z]*$/.test(control.value);

  if(!isValid)
    return { invalidString: true };

  return null;
}