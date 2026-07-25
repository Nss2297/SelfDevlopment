import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ordinalNumber'
})
export class OrdinalNumberPipe implements PipeTransform {

  transform(value: string): string {
    if (value != null) {
      const intValue = Number.parseInt(value);
      if (intValue >= 11 && intValue <= 13) {
        return `${intValue}th`;
      }
      switch (intValue % 10) {
        case 1: return `${intValue}st`;
        case 2: return `${intValue}nd`;
        case 3: return `${intValue}rd`;
        default: return `${intValue}th`;
      }
    }
    return value;
  }

}
