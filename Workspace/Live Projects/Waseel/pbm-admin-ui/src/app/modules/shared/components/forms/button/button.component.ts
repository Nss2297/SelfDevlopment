import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'waseel-button',
    templateUrl: './button.component.html',
    styleUrls: []
})
export class ButtonComponent implements OnInit {

    @Input("disabled")
    disabled: boolean | null = false;

    @Input("type")
    type?: string = 'button';

    @Input("cssClass")
    cssClass?: string = '';

    @Input("variant")
    variant?: 'filled' | 'outlined' | 'tonal' | 'text' = 'filled';

    @Input("color")
    color?: 'default' | 'primary' | 'danger' = 'primary';

    @Input("size")
    size?: 'large' | 'small';

    @Input("icon")
    icon?: 'left' | 'right';

    @Input("iconName")
    iconName?: string;

    @Input("iconRtlInvert")
    iconRtlInvert?: boolean = false;

    @Output("onClick")
    onClick: EventEmitter<MouseEvent> = new EventEmitter();

    paddings = '';

    iconSize = 'h-24 w-24';
    leftIconSpacings = 'ltr:mr-6 rtl:ml-6 ltr:-ml-4 rtl:-mr-4';
    rightIconSpacings = 'ltr:ml-6 rtl:mr-6 ltr:-mr-4 rtl:-ml-4';

    ngOnInit() {
        if (!this.size) {
            this.paddings = ' px-16 py-[11px] button';
        } else if (this.size === 'large') {
            this.paddings = ' px-24 py-[17px] button';
        } else if (this.size === 'small') {
            this.paddings = ' px-12 py-[8px] button-small';
        }
        if (this.size === 'small') {
            this.iconSize = 'h-20 w-20';
            this.leftIconSpacings = 'ltr:mr-4 rtl:ml-4 ltr:-ml-4 rtl:-mr-4';
            this.rightIconSpacings = 'ltr:ml-4 rtl:mr-4 ltr:-mr-4 rtl:-ml-4';
        }
    }

    onButtonClick(event: MouseEvent) {
        this.onClick.emit(event);
    }
}