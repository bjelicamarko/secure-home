import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: number | Date | moment.Moment, dateFormat: string): any {
    return moment(value).format(dateFormat);
  }

}
