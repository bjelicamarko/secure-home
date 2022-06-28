import { Injectable } from '@angular/core';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class SharedDatePickerService {

  constructor() { }

  checkDate(date: string) : string {
    var dateString = moment(date).format('DD.MM.YYYY.');
    return dateString.toString();
  }
}
