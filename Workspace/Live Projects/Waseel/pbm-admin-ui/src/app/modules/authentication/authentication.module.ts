import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApmModule } from '@elastic/apm-rum-angular';
import { TranslateModule } from '@ngx-translate/core';
import { WaseelFormsModule } from 'src/app/modules/shared/components/forms/waseel-forms.module';
import { SharedModule } from '../shared/shared.module';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import { PatientSignInComponent } from './pages/patient-sign-in/patient-sign-in.component';


export const authenticationModuleRoutes: Routes = [
  { path: 'signIn', component: SignInComponent },
  { path: 'patientSignIn', component: PatientSignInComponent}
]

@NgModule({
  declarations: [
    SignInComponent,
    PatientSignInComponent,
  ],
  imports: [
    CommonModule,
    WaseelFormsModule,
    TranslateModule,
    SharedModule,
    RouterModule.forChild(authenticationModuleRoutes),
    ApmModule
  ],
  exports: [
    RouterModule,
    SharedModule
  ],
  providers: []
})
export class AuthenticationModule { }

