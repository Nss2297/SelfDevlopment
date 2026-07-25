import { Component, Input } from '@angular/core';
import { DuplicationDetails } from '../../models/duplication-details.model';

@Component({
  selector: 'app-view-duplication-therapy-dialog',
  templateUrl: './view-duplication-therapy-dialog.component.html',
  styleUrls: ['./view-duplication-therapy-dialog.component.css']
})
export class ViewDuplicationTherapyDialogComponent {
    @Input('onClose')
    onClose?: (data: any) => void;

    @Input()
    data?:DuplicationDetails;

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
}
