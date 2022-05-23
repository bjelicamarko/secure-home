import { AbstractControl } from '@angular/forms';

export function MinLengthPasswordValidator(control: AbstractControl) {
    if(!control.value) return null;

    const isValid = /^.{8,}$/.test(control.value);
  
    if(!isValid)
      return { invalidMinLengthPassword: true };
      
    return null;
}