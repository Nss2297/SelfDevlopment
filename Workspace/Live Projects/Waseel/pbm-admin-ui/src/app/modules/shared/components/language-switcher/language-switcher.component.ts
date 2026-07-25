import { transition } from '@angular/animations';
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-language-switcher',
  templateUrl: './language-switcher.component.html',
  styleUrls: []
})
export class LanguageSwitcherComponent {

  @Input("mode")
  mode = "default";

  constructor(public translate: TranslateService) {

  }

  switchLanguage() {
    if (this.translate.currentLang == 'ar') {
      this.translate.use('en');
      localStorage.setItem('lang', 'en');
      document.querySelector("html")?.setAttribute('class', 'lang-en');
      document.querySelector("html")?.setAttribute('dir', 'ltr');
      document.querySelector("html")?.setAttribute('lang', 'en');
    } else {
      this.translate.use('ar');
      localStorage.setItem('lang', 'ar');
      document.querySelector("html")?.setAttribute('class', 'lang-ar');
      document.querySelector("html")?.setAttribute('dir', 'rtl');
      document.querySelector("html")?.setAttribute('lang', 'ar');
    }
  }
}
