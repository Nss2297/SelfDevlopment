import { Component, HostListener, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'waseel-dialog',
  templateUrl: './dialog.component.html',
  styles: [
  ]
})
export class DialogComponent implements OnInit {

  @Input('size')
  size?: 'sm' | 'md' | 'lg' | 'xl';

  @Input('internalScroll')
  internalScroll?: boolean = false;

  @Input('clipContent')
  clipContent?: boolean = true;

  @Output('onClose')
  onClose: EventEmitter<any> = new EventEmitter();

  @Input('preventBackdropClick')
  preventBackdropClick = false;

  width = 'max-w-[600px]';
  closing = false;
  animationDuration = 300;

  @HostListener('document:keydown.escape', ['$event']) onKeydownHandler(event: KeyboardEvent) {
    if (!this.preventBackdropClick) {
      this.closeDialog();
    }
  }

  ngOnInit(): void {
    if (!this.size) {
      this.size = 'md';
    }
    if (this.size === 'sm') {
      this.width = 'max-w-[480px]';
    } else if (this.size === 'lg') {
      this.width = 'max-w-[800px]';
    } else if (this.size === 'xl') {
      this.width = 'max-w-[1140px]';
    }
  }

  closeDialog() {
    this.closing = true;
    setTimeout(() => {
      this.onClose.emit();
    }, this.animationDuration);
  }

}
