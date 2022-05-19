import { AbstractControl } from '@angular/forms';

export function MinLengthValidator(control: AbstractControl) {
  if(!control.value) return null;

  const isValid = /^.{3,}$/.test(control.value);

  if(!isValid)
    return { invalidMinLength: true };
    
  return null;
}