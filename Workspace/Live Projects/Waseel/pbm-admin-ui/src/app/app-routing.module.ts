import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authenticationModuleRoutes } from './modules/authentication/authentication.module';
import { MainRouteGuard } from './services/main-route-guard/main-route.guard';
import { ModulesGuard } from './services/modules-route-guard/modules-route.guard';
import { PatientPrescriptionComponent } from './modules/prescription/pages/patient-prescription/patient-prescription.component';


const routes: Routes = [
    { path: '', redirectTo: 'prescription', pathMatch: 'full' },
    { path: 'auth', children: authenticationModuleRoutes, canActivateChild: [MainRouteGuard] },
    {
        path: 'customization',
        loadChildren: () => import('./modules/customization/customization.module').then(m => m.CustomizationModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: { allowedAuthorities: ['101;PBM_ADMIN', 'CUSTOMIZATION_REQUEST', 'DRUG_TO_DIAGNOSIS_CUSTOMIZATION',] }
    },
    {
        path: 'prescription',
        loadChildren: () => import('./modules/prescription/prescription.module').then(m => m.PrescriptionModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: {
            allowedAuthorities: [
                'PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION',
                'FOLLOW_UP_PRESCRIPTION', 'PRESCRIPTION_CANCELLATION',
                'PRESCRIPTION_INQUIRY', 'DETAIL_INQUIRY',
                'SUMMARY_INQUIRY', 'PRESCRIPTION_DISPENSE',
                'BUSINESS_CUSTOMIZATION', 'EDIT_PRESCRIPTION_DECISION', 'MEDICAL_CUSTOMIZATION', 'VIEW_PRESCRIPTION',
                'BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'
            ],
            onFailRedirectTo: ['customization']
        }
    },
    {
        path: 'formulary',
        loadChildren: () => import('./modules/formulary-management/formulary-management.module').then(m => m.FormularyManagementModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: {
            allowedAuthorities: ['BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT']
        }
    },
    {
        path: 'sfda',
        loadChildren: () => import('./modules/sfda-management/sfda-management.module').then(m => m.SfdaManagementModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data:{
            allowedAuthorities: [ '101;PBM_ADMIN',]
        }
    },
    {
        path: 'drug-exclusion-management',
        loadChildren: () => import('./modules/drug-exclusion-management/drug-exclusion-management.module').then(m => m.DrugExclusionManagementModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: {
            allowedAuthorities: ['CUSTOMIZATION_REQUEST', 'VIEW_PRESCRIPTION', '101;EXCLUSION_MANAGEMENT', 'HIGH_COST_EXCLUSION', 'NETWORK_EXCLUSION', 'PROVIDER_EXCLUSION', 'SPECIALITY_EXCLUSION']
        }
    },
    {
        path: 'member-management',
        loadChildren: () => import('./modules/member-management/member-management.module').then(m => m.MemberManagementModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: {
            allowedAuthorities: ['101;BUSINESS_RULE_ADMINISTRATION', '101;MEMBER_MANAGEMENT']
        }
    },
    {
        path: 'patient-prescription', component: PatientPrescriptionComponent
    },
    {
        path: 'idf',
        loadChildren: () => import('./modules/idf/idf.module').then(m => m.IdfModule),
        canLoad: [MainRouteGuard, ModulesGuard],
        data: { allowedAccountId: '101', allowedAuthorities: ['101;PBM_ADMIN', '101;IDF_MANAGEMENT'] }
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { enableTracing: false })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
