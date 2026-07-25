import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { HeaderComponent } from 'src/app/modules/shared/components/header/header.component';
import { LanguageSwitcherComponent } from 'src/app/modules/shared/components/language-switcher/language-switcher.component';
import { MainLayoutComponent } from 'src/app/modules/shared/components/main-layout/main-layout.component';
import { BasePageComponent } from './components/base-page/base-page.component';
import { WaseelFormsModule } from './components/forms/waseel-forms.module';
import { ListViewComponent } from './components/list-view/list-view.component';
import { WaseelAlertComponent } from './components/waseel-alert/waseel-alert.component';
import { NavMenuComponent } from './components/nav-menu/nav-menu.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { DrawerComponent } from './components/drawer/drawer.component';
import { AlertConfirmDialogComponent } from './dialogs/alert-confirm-dialog/alert-confirm-dialog.component';
import { LoadingOverlayComponent } from './components/loading-overlay/loading-overlay.component';
import { NoContentComponent } from './components/no-content/no-content.component';
import { SecuredElementComponent } from './components/secured-element/secured-element.component';
import { OrdinalNumberPipe } from './pipes/ordinal-number.pipe';
import { SecuredElementDirective } from './directives/secured-element/secured-element.directive';


@NgModule({
  declarations: [
    LanguageSwitcherComponent,
    MainLayoutComponent,
    WaseelAlertComponent,
    BasePageComponent,
    HeaderComponent,
    ListViewComponent,
    NavMenuComponent,
    DialogComponent,
    DrawerComponent,
    AlertConfirmDialogComponent,
    LoadingOverlayComponent,
    NoContentComponent,
    SecuredElementComponent,
    OrdinalNumberPipe,
    SecuredElementDirective
  ],
  imports: [
    CommonModule,
    WaseelFormsModule,
    TranslateModule,
    RouterModule
  ],
  exports: [
    LanguageSwitcherComponent,
    WaseelAlertComponent,
    WaseelFormsModule,
    TranslateModule,
    HeaderComponent,
    BasePageComponent,
    ListViewComponent,
    NavMenuComponent,
    MainLayoutComponent,
    DialogComponent,
    DrawerComponent,
    AlertConfirmDialogComponent,
    LoadingOverlayComponent,
    NoContentComponent,
    SecuredElementComponent,
    OrdinalNumberPipe,
    SecuredElementDirective
  ]
})
export class SharedModule { }
