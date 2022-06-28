import { AbstractControl } from '@angular/forms';

export function BasicValidatorWithSpace(control: AbstractControl) {
  if(!control.value) return null;

  const isValid = /^[a-zA-Z\s]*$/.test(control.value);

  if(!isValid)
    return { invalidSpaceString: true };

  return null;
}