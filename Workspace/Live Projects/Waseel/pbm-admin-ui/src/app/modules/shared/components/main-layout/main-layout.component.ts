import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from '../../services/dialog-service/dialog.service';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html'
})
export class MainLayoutComponent implements OnInit {

  @Input('showSideSection')
  showSideSection: boolean = true;

  @Input('smallSideSection')
  smallSideSection: boolean = true;

  @Input('showLanguageSwitcher')
  showLanguageSwitcher: boolean = false;

  @Input('foldSideSection')
  foldSideSection: boolean = false;


  dir: 'ltr' | 'rtl' = 'rtl';

  constructor(
    public translate: TranslateService,
  ) {
    this.translate.onLangChange.subscribe(languageChangeEvent => {
      this.dir = languageChangeEvent.lang == 'ar' ? 'rtl' : 'ltr';
    });
  }

  ngOnInit(): void {
    this.dir = this.translate.currentLang == 'ar' ? 'rtl' : 'ltr';
  }
}
