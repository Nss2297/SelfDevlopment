import { Component, Input } from '@angular/core';
import { DrugGenderDetails } from '../../models/drug-gender-details.model';

@Component({
  selector: 'app-view-drug-gender-customization-dialog',
  templateUrl: './view-drug-gender-customization-dialog.component.html',
  styles: [
  ]
})
export class ViewDrugGenderCustomizationDialogComponent  {
  @Input('onClose')
  onClose?: (data: any) => void;
  @Input()
  data?:DrugGenderDetails;
  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
  ngOnInit(): void {
    console.log(this.data)
  }
}
