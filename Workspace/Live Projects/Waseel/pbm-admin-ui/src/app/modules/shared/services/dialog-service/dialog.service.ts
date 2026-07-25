import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject, Subject } from 'rxjs';

export interface AlertConfirmDialogConfig {
  mode?: 'confirm' | 'warning' | 'success' | 'info' | 'delete' | 'error',
  titleMsg?: string,
  subtitleMsg?: string,
  yesActionText?: string,
  noActionText?: string;
  hideNoButton?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  alertConfirmDialogOpen$: BehaviorSubject<boolean> = new BehaviorSubject(false);
  alertConfirmDialogCallback?: (data: any) => void;
  alertConfirmDialogConfig?: AlertConfirmDialogConfig;
  alertConfirmDialogPreventBackdropClick$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  constructor(
    private translate: TranslateService
  ) {
    this.alertConfirmDialogConfig = {
      mode: 'success',
      titleMsg: "titleMsg",
      subtitleMsg: "msg",
      yesActionText: "yesActionText",
      hideNoButton: true
    };
  }

  showDeleteConfirmDialog(callback: (data: any) => void) {
    let titleMsg, subtitleMsg, yesActionText, noActionText;
    this.translate.get('DELETE-TITLE-MSG').subscribe(text => titleMsg = text);
    this.translate.get('DELETE-SUBTITLE-MSG').subscribe(text => subtitleMsg = text);
    this.translate.get('DELETE').subscribe(text => yesActionText = text);
    this.translate.get('CANCEL').subscribe(text => noActionText = text);
    this.alertConfirmDialogConfig = {
      mode: 'delete',
      titleMsg,
      subtitleMsg,
      yesActionText,
      noActionText
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
    this.alertConfirmDialogPreventBackdropClick$.next(true);
  }

  hideAlertConfirmDialog() {
    this.alertConfirmDialogOpen$.next(false);
    this.alertConfirmDialogCallback = undefined;
    this.alertConfirmDialogPreventBackdropClick$.next(false);
    this.alertConfirmDialogConfig = undefined;
  }

  showWarningDialog(msg: string, callback: (data: any) => void) {
    let titleMsg, yesActionText;
    this.translate.get('WARNING').subscribe(text => titleMsg = text);
    this.translate.get('OK').subscribe(text => yesActionText = text);
    this.alertConfirmDialogConfig = {
      mode: 'warning',
      titleMsg,
      subtitleMsg: msg,
      yesActionText,
      hideNoButton: true
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
  }

  showInfoDialog(msg: string, callback: (data: any) => void) {
    let titleMsg, yesActionText;
    this.translate.get('INFORMATION').subscribe(text => titleMsg = text);
    this.translate.get('OK').subscribe(text => yesActionText = text);
    this.alertConfirmDialogConfig = {
      mode: 'info',
      titleMsg,
      subtitleMsg: msg,
      yesActionText,
      hideNoButton: true
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
  }

  showSuccessDialog(msg: string, callback: (data: any) => void) {
    let titleMsg, yesActionText;
    this.translate.get('SUCCESS').subscribe(text => titleMsg = text);
    this.translate.get('OK').subscribe(text => yesActionText = text);
    this.alertConfirmDialogConfig = {
      mode: 'success',
      titleMsg,
      subtitleMsg: msg,
      yesActionText,
      hideNoButton: true
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
  }

  showErrorDialog(msg: string, callback: (data: any) => void) {
    let titleMsg, yesActionText;
    this.translate.get('ERROR').subscribe(text => titleMsg = text);
    this.translate.get('OK').subscribe(text => yesActionText = text);
    this.alertConfirmDialogConfig= {
      mode: 'error',
      titleMsg,
      subtitleMsg: msg,
      yesActionText,
      hideNoButton: true
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
  }

  showConfirmDialog(title: string, subtitle: string, callback: (data: any) => void) {
    this.alertConfirmDialogConfig = {
      mode: 'confirm',
      titleMsg: title,
      subtitleMsg: subtitle
    };
    this.alertConfirmDialogCallback = callback;
    this.alertConfirmDialogOpen$.next(true);
  }
}
