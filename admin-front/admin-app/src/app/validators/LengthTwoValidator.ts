import { AbstractControl } from '@angular/forms';

export function LengthTwoValidator(control: AbstractControl) {
    if(!control.value) return null;

    const isValid = /^.{2,2}$/.test(control.value);
  
    if(!isValid)
      return { invalidTwoLength: true };
      
    return null;
}