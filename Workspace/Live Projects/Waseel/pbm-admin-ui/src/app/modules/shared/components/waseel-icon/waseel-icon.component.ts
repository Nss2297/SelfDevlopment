import { Component, Input } from '@angular/core';

@Component({
  selector: 'waseel-icon',
  templateUrl: './waseel-icon.component.html',
  styles: [
  ]
})
export class WaseelIconComponent {

  @Input("icon")
  icon: string | undefined = '';

  @Input("cssClass")
  cssClass: string | undefined = '';
}
