import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DrugDiagnosisDetails } from '../../models/drug-diagnosis-details.model';

@Component({
  selector: 'view-drug-diagnosis-customization-dialog',
  templateUrl: './view-drug-diagnosis-customization-dialog.component.html',
  styles: [
  ]
})
export class ViewDrugDiagnosisCustomizationDialogComponent implements OnInit {

  constructor(translate: TranslateService) {

  }

  serviceStatusClasses: string = "text-success dark:text-success-300"

  ngOnInit(): void {
    // if (this.data != null) {
    //   if ('approved' == (this.data.serviceStatus?.toLowerCase() || '')) {
    //     this.data.serviceCode = 'customization.approved';
    //   } else if ('rejected' == (this.data.serviceStatus?.toLowerCase() || '')) {
    //     this.data.serviceCode = 'customization.rejected';
    //   }
    //   if(this.data.serviceStatus?.toLowerCase().includes('rejected')){
    //     this.serviceStatusClasses = 'text-error dark:text-error-400';
    //   }
    // }
    
  }

  @Input('onClose')
  onClose?: (data: any) => void;

  @Input()
  data?: DrugDiagnosisDetails;

  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
}
