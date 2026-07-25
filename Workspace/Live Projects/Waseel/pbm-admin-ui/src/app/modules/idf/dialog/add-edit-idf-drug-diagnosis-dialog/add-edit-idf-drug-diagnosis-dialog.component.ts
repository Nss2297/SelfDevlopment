import { Component, Input } from '@angular/core';

@Component({
  selector: 'add-edit-idf-drug-diagnosis-dialog',
  templateUrl: './add-edit-idf-drug-diagnosis-dialog.component.html',
  styles: [
  ]
})
export class AddEditIdfDrugDiagnosisDialogComponent {
  @Input('dialogMode')
  dialogMode: 'add' | 'edit' = 'add';

  @Input('onClose')
  onClose?: (data: any) => void;

  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
}
