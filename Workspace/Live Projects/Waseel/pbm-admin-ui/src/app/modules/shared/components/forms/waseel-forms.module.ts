import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IconButtonComponent } from 'src/app/modules/shared/components/forms/icon-button/icon-button.component';
import { WaseelIconComponent } from '../waseel-icon/waseel-icon.component';
import { ButtonComponent } from './button/button.component';
import { TextBoxComponent } from './text-box/text-box.component';
import { SelectComponent } from './select/select.component';
import { SelectWithSearchComponent } from './select-with-search/select-with-search.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { TranslateModule } from '@ngx-translate/core';



@NgModule({
  declarations: [
    ButtonComponent,
    TextBoxComponent,
    IconButtonComponent,
    WaseelIconComponent,
    SelectComponent,
    SelectWithSearchComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    MatMomentDateModule,
    MatDatepickerModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    ButtonComponent,
    TextBoxComponent,
    IconButtonComponent,
    WaseelIconComponent,
    SelectComponent,
    SelectWithSearchComponent,
    MatMomentDateModule,
    MatDatepickerModule
  ]
})
export class WaseelFormsModule { }