import { AbstractControl } from '@angular/forms';

export function MaxLengthValidator(control: AbstractControl) {
    if(!control.value) return null;

    const isValid = /^.{0,20}$/.test(control.value);
  
    if(!isValid)
      return { invalidMaxLength: true };
      
    return null;
}