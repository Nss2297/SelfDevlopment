import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrescriptionsComponent } from './pages/prescriptions/prescriptions.component';
import { InquirePrescriptionComponent } from './pages/inquire-prescription/inquire-prescription.component';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AddEditPrescriptionComponent } from './pages/add-edit-prescription/add-edit-prescription.component';
import { ViewPrescriptionDetailsComponent } from './pages/view-prescription-details/view-prescription-details.component';
import { AddEditPrescriptionDrugDialogComponent } from './dialogs/add-edit-prescription-drug-dialog/add-edit-prescription-drug-dialog.component';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';
import { DispenseDialogComponent } from './dialogs/dispense-dialog/dispense-dialog.component';

import { InquiryListingComponent } from './pages/inquiry-listing/inquiry-listing.component';
import { PayerMemberStepComponent } from './pages/add-edit-prescription/components/payer-member-step/payer-member-step.component';
import { DiagnosisStepComponent } from './pages/add-edit-prescription/components/diagnosis-step/diagnosis-step.component';
import { DrugsStepComponent } from './pages/add-edit-prescription/components/drugs-step/drugs-step.component';
import { ConfirmationStepComponent } from './pages/add-edit-prescription/components/confirmation-step/confirmation-step.component';
import { PayerListComponent } from './pages/payer-list/payer-list.component';
import { ModifyDecisionComponent } from './pages/modify-decision/modify-decision.component';
import { ViewPayerDetailsComponent } from './pages/view-payer-details/view-payer-details.component';
import { CustomizationRequestsComponent } from './pages/customization-requests/customization-requests.component';
import { AddEditModifyDecisionCommentDialogComponent } from './dialogs/add-edit-modify-decision-comment-dialog/add-edit-modify-decision-comment-dialog.component';
import { PrintUcafComponent } from './pages/print-ucaf/print-ucaf.component';
import { DrugDispenseComponent } from './pages/drug-dispense/drug-dispense.component';
import { DispenseSummaryConfirmDialogComponent } from './dialogs/dispense-summary-confirm-dialog/dispense-summary-confirm-dialog.component';
import { ProviderOverrideCommentDialogComponent } from './dialogs/provider-override-comment-dialog/provider-override-comment-dialog.component';


export const prescriptionModuleRoutes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'prefix' },
  {
    path: 'list', component: PrescriptionsComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['PBM_PRESCRIPTION',"OVERRIDE_MEDICATION", 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION', 'FOLLOW_UP_PRESCRIPTION', 'PRESCRIPTION_CANCELLATION'
        , 'VIEW_PRESCRIPTION'],
      onFailRedirectTo: ['prescription', 'inquire']
    }
  },
  {
    path: 'inquire', component: InquirePrescriptionComponent, canActivate: [ModulesGuard], data: {
      // allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY', 'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE'],
      // onFailRedirectTo: ['prescription', 'list']
    }
  },
  // {
  //   path: 'payer', component: PayerListComponent, canActivate: [ModulesGuard], data: {
  //     // allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY', 'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE'],
  //     // onFailRedirectTo: ['prescription', 'list']
  //   }
  // },
  {
    path: 'modifyDecision/:ePrescriptionReferenceNumber', component: ModifyDecisionComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['EDIT_PRESCRIPTION_DECISION'],
    }
  },

  {
    path: 'customization-requests', component: CustomizationRequestsComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['BUSINESS_CUSTOMIZATION', 'CUSTOMIZATION_REQUEST', 'EDIT_PRESCRIPTION_DECISION', 'MEDICAL_CUSTOMIZATION', 'VIEW_PRESCRIPTION', 'PBM_ADMIN', 'BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'],
      // onFailRedirectTo: ['prescription', 'list']
    }
  },
  {
    path: 'print-ucaf', component: PrintUcafComponent, canActivate: [ModulesGuard]
  },
  {
    path: 'details/:ePrescriptionReferenceNumber', component: ViewPrescriptionDetailsComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['PRESCRIPTION_SUBMISSION',"OVERRIDE_MEDICATION", 'NEW_PRESCRIPTION', 'FOLLOW_UP_PRESCRIPTION', 'PRESCRIPTION_CANCELLATION'
        , 'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY', 'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE', 'BUSINESS_CUSTOMIZATION', 'CUSTOMIZATION_REQUEST'
        , 'EDIT_PRESCRIPTION_DECISION', 'MEDICAL_CUSTOMIZATION', 'VIEW_PRESCRIPTION', 'PBM_ADMIN', 'BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'],
      onFailRedirectTo: ['prescription', 'list']
    }
  },
  {
    path: ':ePrescriptionReferenceNumber/dispense', component: DrugDispenseComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: [ 'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY', 'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE'],
      onFailRedirectTo: ['prescription', 'list']
    }
  },
  {
    path: 'add', component: AddEditPrescriptionComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION', 'FOLLOW_UP_PRESCRIPTION'],
      onFailRedirectTo: ['prescription', 'list']
    }
  },
  {
    path: ':ePrescriptionReferenceNumber/followUp', component: AddEditPrescriptionComponent, canActivate: [ModulesGuard], data: {
      allowedAuthorities: ['PBM_PRESCRIPTION',"OVERRIDE_MEDICATION", 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION', 'FOLLOW_UP_PRESCRIPTION'],
      onFailRedirectTo: ['prescription', 'list']
    }
  },
  {
    path: 'dispense', component: DrugDispenseComponent
  },

  {
    path: 'inquiryList', component: InquiryListingComponent, canActivate: [ModulesGuard], data: {
      // allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY', 'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE'],
      onFailRedirectTo: ['inquiryList']
    }
  },
]

@NgModule({
  declarations: [
    PrescriptionsComponent,
    InquirePrescriptionComponent,
    AddEditPrescriptionComponent,
    ViewPrescriptionDetailsComponent,
    AddEditPrescriptionDrugDialogComponent,
    DispenseDialogComponent,
    InquiryListingComponent,
    PayerMemberStepComponent,
    DiagnosisStepComponent,
    DrugsStepComponent,
    ConfirmationStepComponent,
    PayerListComponent,
    ModifyDecisionComponent,
    ViewPayerDetailsComponent,
    CustomizationRequestsComponent,
    AddEditModifyDecisionCommentDialogComponent,
    PrintUcafComponent,
    DrugDispenseComponent,
    DispenseSummaryConfirmDialogComponent,
    ProviderOverrideCommentDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(prescriptionModuleRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class PrescriptionModule { }
