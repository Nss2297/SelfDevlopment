import { Component } from '@angular/core';

@Component({
  selector: 'app-idf-management-page',
  templateUrl: './idf-management-page.component.html',
  styles: [
  ]
})
export class IdfManagementPageComponent {

  addIDFDrugDiagnosisDialogOpen = false;
  isFilterDrawerOpen: boolean = false;

  openAddIDFDrugDiagnosisDialog() {
    this.addIDFDrugDiagnosisDialogOpen = true;
  }

  closeAddIDFDrugDiagnosisDialog = (data: any) => {
    this.addIDFDrugDiagnosisDialogOpen = false;
  }

}
