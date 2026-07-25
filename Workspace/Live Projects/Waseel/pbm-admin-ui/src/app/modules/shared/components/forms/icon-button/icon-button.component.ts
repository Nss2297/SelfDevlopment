import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'waseel-icon-button',
  templateUrl: './icon-button.component.html',
  styles: [
  ]
})
export class IconButtonComponent implements OnInit {

  @Input("icon")
  icon?: string;

  @Input("type")
  type?: string;

  @Input("disabled")
  disabled?: boolean;

  @Output("onClick")
  onClick: EventEmitter<MouseEvent> = new EventEmitter();

  @Input("variant")
  variant?: 'filled' | 'outlined' | 'tonal' | 'transparent';

  @Input("color")
  color?: 'default' | 'primary';

  @Input("size")
  size?: 'large' | 'small';

  @Input("tooltip")
  tooltip?: string = '';

  @Input("cssClass")
  cssClass?: string;

  @Input("iconRtlInvert")
  iconRtlInvert?: boolean = false;

  paddings = '';
  colors = '';
  @Input() callbackFunction?: (args?: any) => void;


  constructor() {

  }

  ngOnInit(): void {
    if (!this.type) {
      this.type = 'button';
    }
    if (!this.variant) {
      this.variant = 'filled';
    }
    if (!this.color) {
      this.color = 'primary';
    }
    if (!this.cssClass) {
      this.cssClass = '';
    }
    if (!this.size) {
      this.paddings = ' p-[10px]';
    } else if (this.size === 'large') {
      this.paddings = ' p-[16px]';
    } else if (this.size === 'small') {
      this.paddings = ' p-[8px]';
    }
    if (this.color == 'primary' && this.variant == 'filled' && !this.disabled) {
      this.colors = ' bg-primary text-white hover:bg-primary-700 focus:bg-primary-700 active:bg-primary-700 focus-shadow-primary';
    } else if (this.color == 'primary' && this.variant == 'filled' && this.disabled) {
      this.colors = ' bg-neutral-200 text-white hover:bg-neutral-200 focus:bg-neutral-200 active:bg-neutral-200 cursor-not-allowed';
    } else if (this.color == 'primary' && this.variant == 'tonal' && !this.disabled) {
      this.colors = ' bg-primary-50 text-primary-500 hover:bg-primary-100 focus:bg-primary-100 active:bg-primary-100 focus-shadow-primary';
    } else if (this.color == 'primary' && this.variant == 'tonal' && this.disabled) {
      this.colors = ' bg-primary-50 text-primary-400 hover:bg-primary-50 focus:bg-primary-50 active:bg-primary-50 cursor-not-allowed';
    } else if (this.color == 'primary' && this.variant == 'transparent' && !this.disabled) {
      this.colors = ' bg-transparent text-primary-400 hover:bg-primary-100 focus:bg-primary-100 active:bg-primary-100 focus-shadow-primary';
    } else if (this.color == 'primary' && this.variant == 'transparent' && this.disabled) {
      this.colors = ' bg-transparent text-primary-400 hover:bg-primary-50 focus:bg-primary-50 active:bg-primary-50 cursor-not-allowed';
    } else if (this.color == 'default' && this.variant == 'transparent' && !this.disabled) {
      this.colors = ' bg-transparent text-secondary hover:bg-[rgba(0,0,0,0.08)] focus:bg-[rgba(0,0,0,0.08)] active:bg-[rgba(0,0,0,0.08)] focus-shadow-default';
    } else if (this.color == 'default' && this.variant == 'transparent' && this.disabled) {
      this.colors = ' bg-transparent text-neutral-400 hover:bg-transparent focus:bg-transparent active:bg-transparent cursor-not-allowed';
    }
  }

  ngDoCheck() {
    if (this.color == 'default' && this.variant == 'transparent' && !this.disabled) {
      this.colors = ' bg-transparent text-secondary hover:bg-[rgba(0,0,0,0.08)] focus:bg-[rgba(0,0,0,0.08)] active:bg-[rgba(0,0,0,0.08)] focus-shadow-default';
    } else if (this.color == 'default' && this.variant == 'transparent' && this.disabled) {
      this.colors = ' bg-transparent text-neutral-400 hover:bg-transparent focus:bg-transparent active:bg-transparent cursor-not-allowed';
    }
  }

  onButtonClick(event: MouseEvent) {
    this.onClick.emit(event);
    if (this.callbackFunction) {
      this.callbackFunction();
    }
  }

}
