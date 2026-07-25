import { Component, Input } from '@angular/core';
import { DrugAgeDetails } from '../../models/drug-age-details.model';

@Component({
  selector: 'app-view-drug-age-customization-dialog',
  templateUrl: './view-drug-age-customization-dialog.component.html',
  styleUrls: ['./view-drug-age-customization-dialog.component.css']
})
export class ViewDrugAgeCustomizationDialogComponent {
    @Input('onClose')
    onClose?: (data: any) => void;
    @Input()
    data?:DrugAgeDetails;
    closeDialog() {
      if (this.onClose) {
        this.onClose(null);
      }
    }
    ngOnInit(): void {
      console.log(this.data)
    }
}
