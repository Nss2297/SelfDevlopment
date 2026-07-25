import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';

@Component({
  selector: 'waseel-drawer',
  templateUrl: './drawer.component.html'
})
export class DrawerComponent {

  @Input()
  title: string = "";

  @Input()
  isOpen: boolean = false;

  @Output()
  isOpenChange: EventEmitter<boolean> = new EventEmitter();

  @HostListener('document:keydown.escape', ['$event']) onKeydownHandler(event: KeyboardEvent) {
    this.isOpen = false;
    this.isOpenChange.emit(this.isOpen);
  }



  toggleDrawer() {
    this.isOpen = !this.isOpen;
    this.isOpenChange.emit(this.isOpen);
  }


}
