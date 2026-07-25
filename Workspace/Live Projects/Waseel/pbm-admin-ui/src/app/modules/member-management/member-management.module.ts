import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberManagementListComponent } from './pages/member-management-list/member-management-list.component';
import { MemberManagementDetailsComponent } from './pages/member-management-details/member-management-details/member-management-details.component';
import { RouterModule, Routes } from '@angular/router';
import { ModulesGuard } from 'src/app/services/modules-route-guard/modules-route.guard';
import { SharedModule } from '../shared/shared.module';

export const memberManagementModuleRoutes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'prefix' },
    {
        path: 'list', component: MemberManagementListComponent, canActivate: [ModulesGuard],
        data: {
            allowedAuthorities: ['101;BUSINESS_RULE_ADMINISTRATION', '101;MEMBER_MANAGEMENT'],
            onFailRedirectTo: ['list']
        }
    },
    {
        path: 'details/:id', component: MemberManagementDetailsComponent, canActivate: [ModulesGuard],
        data: {
            allowedAuthorities: ['101;BUSINESS_RULE_ADMINISTRATION', '101;MEMBER_MANAGEMENT'],
            onFailRedirectTo: ['list']
        }
    }
]

@NgModule({
    declarations: [
        MemberManagementListComponent,
        MemberManagementDetailsComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(memberManagementModuleRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class MemberManagementModule { }
