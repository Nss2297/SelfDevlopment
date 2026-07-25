import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AddEditDrugDiagnosisCustomizationDialogComponent } from './dialogs/add-edit-drug-diagnosis-customization-dialog/add-edit-drug-diagnosis-customization-dialog.component';
import { ViewDrugDiagnosisCustomizationDialogComponent } from './dialogs/view-drug-diagnosis-customization-dialog/view-drug-diagnosis-customization-dialog.component';
import { DrugDiagnosisCustomizationPageComponent } from './pages/drug-diagnosis-customization-page/drug-diagnosis-customization-page.component';
import { UploadDrugDiagnosisCustomizationDialogComponent } from './dialogs/upload-drug-diagnosis-customization-dialog/upload-drug-diagnosis-customization-dialog.component';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';
import { DrugGenderCustomizationComponent } from './pages/drug-gender-customization/drug-gender-customization.component';
import { AddEditDrugGenderCustomizationDialogComponent } from './dialogs/add-edit-drug-gender-customization-dialog/add-edit-drug-gender-customization-dialog.component';
import { UploadDrugGenderCustomizationDialogComponent } from './dialogs/upload-drug-gender-customization-dialog/upload-drug-gender-customization-dialog.component';
import { ViewDrugGenderCustomizationDialogComponent } from './dialogs/view-drug-gender-customization-dialog/view-drug-gender-customization-dialog.component';
import { DrugAgeCustomizationPageComponent } from './pages/drug-age-customization-page/drug-age-customization-page.component';
import { AddEditDrugAgeCustomizationComponent } from './dialogs/add-edit-drug-age-customization/add-edit-drug-age-customization.component';
import { UploadDrugAgeCustomizationDialogComponent } from './dialogs/upload-drug-age-customization-dialog/upload-drug-age-customization-dialog.component';
import { ViewDrugAgeCustomizationDialogComponent } from './dialogs/view-drug-age-customization-dialog/view-drug-age-customization-dialog.component';
import { DrugDrugInteractionPageComponent } from './pages/drug-drug-interaction-page/drug-drug-interaction-page.component';
import { ViewDrugDrugInteractionDialogComponent } from './dialogs/view-drug-drug-interaction-dialog/view-drug-drug-interaction-dialog.component';
import { UploadDrugDrugInteractionDialogComponent } from './dialogs/upload-drug-drug-interaction-dialog/upload-drug-drug-interaction-dialog.component';
import { AddEditDrugDrugInteractionDialogComponent } from './dialogs/add-edit-drug-drug-interaction-dialog/add-edit-drug-drug-interaction-dialog.component';
import { DuplicationTherapyCustomizationPageComponent } from './pages/duplication-therapy-customization-page/duplication-therapy-customization-page.component';
import { ViewDuplicationTherapyDialogComponent } from './dialogs/view-duplication-therapy-dialog/view-duplication-therapy-dialog.component';
import { UploadDuplicationTherapyDialogComponent } from './dialogs/upload-duplication-therapy-dialog/upload-duplication-therapy-dialog.component';
import { AddEditDuplicationTherapyDialogComponent } from './dialogs/add-edit-duplication-therapy-dialog/add-edit-duplication-therapy-dialog.component';

export const customizationModuleRoutes: Routes = [
    { path: '', redirectTo: 'drugs/diagnosis', pathMatch: 'prefix' },
    // { path: 'drugs/diagnosis', component: DrugDiagnosisCustomizationPageComponent },
    {
        path: 'drugs/diagnosis', component: DrugDiagnosisCustomizationPageComponent, canActivate: [ModulesGuard], data: {
            allowedAuthorities: ['PBM_ADMIN', 'CUSTOMIZATION_REQUEST', 'DRUG_TO_DIAGNOSIS_CUSTOMIZATION',],
            onFailRedirectTo: ['drugs/diagnosis']
        }
    }, {
        path: 'drugs/gender', component: DrugGenderCustomizationComponent, canActivate: [ModulesGuard], data: {
            allowedAuthorities: ['PBM_ADMIN', 'DRUG_TO_GENDER_CUSTOMIZATION',],
            onFailRedirectTo: ['drugs/diagnosis']
        }
    },
    {
        path: 'drugs/age', component: DrugAgeCustomizationPageComponent, canActivate: [ModulesGuard], data: {
            allowedAuthorities: ['PBM_ADMIN', 'DRUG_TO_AGE_CUSTOMIZATION'],
            onFailRedirectTo: ['drugs/diagnosis']
        }
    },
    {
        path: 'drugs/drug', component: DrugDrugInteractionPageComponent, canActivate: [ModulesGuard], data: {
            allowedAuthorities: ['PBM_ADMIN', 'DRUG_TO_DRUG_CUSTOMIZATION'],
            onFailRedirectTo: ['drugs/diagnosis']
        }
    },
    {
        path: 'drugs/duplication', component: DuplicationTherapyCustomizationPageComponent, canActivate: [ModulesGuard], data: {
            allowedAuthorities: ['PBM_ADMIN','DUPLICATE_THERAPY_CUSTOMIZATION','DUPLICATE_THERAPY_CUSTOMIZATION_UPLOAD'],
            onFailRedirectTo: ['drugs/diagnosis']
        }
    },
]

@NgModule({
    declarations: [
        DrugDiagnosisCustomizationPageComponent,
        AddEditDrugDiagnosisCustomizationDialogComponent,
        ViewDrugDiagnosisCustomizationDialogComponent,
        UploadDrugDiagnosisCustomizationDialogComponent,
        DrugGenderCustomizationComponent,
        AddEditDrugGenderCustomizationDialogComponent,
        UploadDrugGenderCustomizationDialogComponent,
        ViewDrugGenderCustomizationDialogComponent,
        DrugAgeCustomizationPageComponent,
        AddEditDrugAgeCustomizationComponent,
        UploadDrugAgeCustomizationDialogComponent,
        ViewDrugAgeCustomizationDialogComponent,
        DrugDrugInteractionPageComponent,
        ViewDrugDrugInteractionDialogComponent,
        UploadDrugDrugInteractionDialogComponent,
        AddEditDrugDrugInteractionDialogComponent,
        DuplicationTherapyCustomizationPageComponent,
        ViewDuplicationTherapyDialogComponent,
        UploadDuplicationTherapyDialogComponent,
        AddEditDuplicationTherapyDialogComponent
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
export class CustomizationModule { }
