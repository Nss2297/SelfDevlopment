import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { SfdaDetailsComponent } from './pages/sfda-details/sfda-details.component';
import { SfdaDrugDetailsDialogComponent } from './dialogs/sfda-drug-details-dialog/sfda-drug-details-dialog.component';
import { AddSfdaDrugDialogComponent } from './dialogs/add-sfda-drug-dialog/add-sfda-drug-dialog.component';
import { UploadSfdaListDialogComponent } from './dialogs/upload-sfda-list-dialog/upload-sfda-list-dialog.component';
import { SfdaListComponent } from './pages/sfda-list/sfda-list.component';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';

export const sfdaManagementModuleRoutes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'prefix' },
    {
        path: 'list', component: SfdaListComponent,
        canActivate: [ModulesGuard],
        data: {
            allowedAuthorities: ['101;PBM_ADMIN',]
        }
    },
    {
        path: 'details/:id', component: SfdaDetailsComponent,
        canActivate: [ModulesGuard],
    }
]

@NgModule({
    declarations: [
        SfdaListComponent,
        SfdaDetailsComponent,
        SfdaDrugDetailsDialogComponent,
        AddSfdaDrugDialogComponent,
        UploadSfdaListDialogComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(sfdaManagementModuleRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class SfdaManagementModule { }
