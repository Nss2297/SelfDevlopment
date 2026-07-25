import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IdfManagementPageComponent } from './pages/idf-management-page/idf-management-page.component';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AddEditIdfDrugDiagnosisDialogComponent } from './dialog/add-edit-idf-drug-diagnosis-dialog/add-edit-idf-drug-diagnosis-dialog.component';

export const customizationModuleRoutes: Routes = [
  { path: '', redirectTo: 'idf-management', pathMatch: 'prefix' },
  { path: 'idf-management', component: IdfManagementPageComponent }
]

@NgModule({
  declarations: [
    IdfManagementPageComponent,
    AddEditIdfDrugDiagnosisDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(customizationModuleRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class IdfModule { }
