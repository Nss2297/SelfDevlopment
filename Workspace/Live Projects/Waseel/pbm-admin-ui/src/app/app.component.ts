import { Component, Inject, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from './modules/shared/services/dialog-service/dialog.service';
import { AuthService } from './modules/authentication/services/auth-service/auth.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: []
})
export class AppComponent {


    constructor(
        public translate: TranslateService,
        @Inject(LOCALE_ID) private locale: string,
        private titleService: Title,
        public dialogService: DialogService,
        public authService: AuthService
    ) {
        const languages = ['ar', 'en'];
        translate.addLangs(languages);
        translate.setDefaultLang('en');
        document.querySelector("html")?.setAttribute('dir', 'ltr');
        document.querySelector("html")?.setAttribute('lang', 'en');
        const userLang = localStorage.getItem('lang');
        if (userLang && languages.includes(userLang)) {
            translate.use(userLang);
        } else {
            translate.use(locale.includes('en') ? 'en' : 'ar');
        }
        translate.get("appName").subscribe(title => titleService.setTitle(title));
        translate.onLangChange.subscribe(languageChangeEvent => {
            if (this.translate.currentLang == 'ar') {
                document.querySelector("html")?.setAttribute('dir', 'rtl');
                document.querySelector("html")?.setAttribute('lang', 'ar');
            } else {
                document.querySelector("html")?.setAttribute('dir', 'ltr');
                document.querySelector("html")?.setAttribute('lang', 'en');
            }
        });
        if (this.translate.currentLang == 'ar') {
            document.querySelector("html")?.setAttribute('class', 'lang-ar');
            document.querySelector("html")?.setAttribute('dir', 'rtl');
            document.querySelector("html")?.setAttribute('lang', 'ar');
        } else {
            document.querySelector("html")?.setAttribute('class', 'lang-en');
            document.querySelector("html")?.setAttribute('dir', 'ltr');
            document.querySelector("html")?.setAttribute('lang', 'en');
        }
    }

    ngOnInit() {
    }

    runOnConfirmDialogCloseCallBack(isConfirm: boolean) {
        if (this.dialogService.alertConfirmDialogCallback) {
            this.dialogService.alertConfirmDialogCallback(isConfirm);
        }
        this.dialogService.hideAlertConfirmDialog();
    }
}
