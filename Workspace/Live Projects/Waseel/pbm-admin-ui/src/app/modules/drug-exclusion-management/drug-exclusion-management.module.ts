import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateExclusionListComponent } from './pages/create-exclusion-list/create-exclusion-list.component';
import { DrugExclusionListComponent } from './pages/drug-exclusion-list/drug-exclusion-list.component';
import { ExclusionListDetailsComponent } from './pages/exclusion-list-details/exclusion-list-details.component';
import { AddExclusionListBasedOnDialogComponent } from './dialogs/add-exclusion-list-based-on-dialog/add-exclusion-list-based-on-dialog.component';
import { EditExclusionListDialogComponent } from './dialogs/edit-exclusion-list-dialog/edit-exclusion-list-dialog.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { AddDrugExclusionListDrugDialogComponent } from './dialogs/add-drug-exclusion-list-drug-dialog/add-drug-exclusion-list-drug-dialog.component';
import { UploadDrugExclusionListDrugDialogComponent } from './dialogs/upload-drug-exclusion-list-drug-dialog/upload-drug-exclusion-list-drug-dialog.component';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';

export const drugExclusionManagementModuleRoutes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'prefix' },
  {
    path: 'list', component: DrugExclusionListComponent, canActivate: [ModulesGuard],
    data: {
      allowedAuthorities: ['CUSTOMIZATION_REQUEST','101;EXCLUSION_MANAGEMENT','HIGH_COST_EXCLUSION','NETWORK_EXCLUSION','PROVIDER_EXCLUSION','SPECIALITY_EXCLUSION'],
      onFailRedirectTo: ['list']
  }
  },
  {
    path: 'create', component: CreateExclusionListComponent
  },
  {
    path: 'details/:id', component: ExclusionListDetailsComponent, canActivate: [ModulesGuard],
    data: {
        allowedAuthorities: ['CUSTOMIZATION_REQUEST'],
        onFailRedirectTo: ['list']
    }
  }
];

@NgModule({
  declarations: [
    DrugExclusionListComponent,
    CreateExclusionListComponent,
    ExclusionListDetailsComponent,
    AddExclusionListBasedOnDialogComponent,
    EditExclusionListDialogComponent,
    AddDrugExclusionListDrugDialogComponent,
    UploadDrugExclusionListDrugDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(drugExclusionManagementModuleRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class DrugExclusionManagementModule { }
