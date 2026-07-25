import { Component, Input } from '@angular/core';
import { SfdaDrugListDetailsModel } from '../../models/sfda-drug-details.model';

@Component({
    selector: 'sfda-drug-details-dialog',
    templateUrl: './sfda-drug-details-dialog.component.html',
    styles: [
    ]
})
export class SfdaDrugDetailsDialogComponent {
    @Input('onClose')
    onClose?: (data: any) => void;

    @Input()
    data?: SfdaDrugListDetailsModel;

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
}
