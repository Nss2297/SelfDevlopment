import { Component, Input } from '@angular/core';
import { DrugToDrugDetails } from '../../models/drug-drug-details.model';

@Component({
    selector: 'app-view-drug-drug-interaction-dialog',
    templateUrl: './view-drug-drug-interaction-dialog.component.html',
    styleUrls: ['./view-drug-drug-interaction-dialog.component.css']
})
export class ViewDrugDrugInteractionDialogComponent {

    @Input('onClose')
    onClose?: (data: any) => void;

    @Input()
    data?: DrugToDrugDetails;

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }

    constructor() {

    }

    ngOnInit(): void {
    }
}
