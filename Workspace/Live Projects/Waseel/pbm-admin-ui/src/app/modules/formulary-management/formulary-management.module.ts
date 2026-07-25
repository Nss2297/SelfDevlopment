import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';
import { AddBasedOnDialogComponent } from './dialogs/add-based-on-dialog/add-based-on-dialog.component';
import { AddFormularyDrugDialogComponent } from './dialogs/add-formulary-drug-dialog/add-formulary-drug-dialog.component';
import { EditFormularyNameDialogComponent } from './dialogs/edit-formulary-name-dialog/edit-formulary-name-dialog.component';
import { FormularyDetailsComponent } from './pages/formulary-details/formulary-details.component';
import { CreateFormularyComponent } from './pages/create-formulary/create-formulary.component';
import { FormularyListComponent } from './pages/formulary-list/formulary-list.component';

export const formularyModuleRoutes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'prefix' },
    {
        path: 'list', component: FormularyListComponent, canActivate: [ModulesGuard],
        data: {
            allowedAuthorities: ['BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'],
            onFailRedirectTo: ['list']
        }
    },
    {
        path: 'create', component: CreateFormularyComponent,
        //  canActivate: [ModulesGuard]
    },
    {
        path: 'details/:id', component: FormularyDetailsComponent, canActivate: [ModulesGuard],
        data: {
            allowedAuthorities: ['BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'],
            onFailRedirectTo: ['list']
        }
    }
]


@NgModule({
    declarations: [
        FormularyListComponent,
        CreateFormularyComponent,
        AddBasedOnDialogComponent,
        AddFormularyDrugDialogComponent,
        FormularyDetailsComponent,
        EditFormularyNameDialogComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(formularyModuleRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class FormularyManagementModule { }
