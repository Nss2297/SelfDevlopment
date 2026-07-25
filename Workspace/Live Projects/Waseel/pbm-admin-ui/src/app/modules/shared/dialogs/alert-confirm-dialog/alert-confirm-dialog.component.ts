import { AfterContentChecked, Component, EventEmitter, Input, Output } from '@angular/core';
import { AlertConfirmDialogConfig, DialogService } from '../../services/dialog-service/dialog.service';

@Component({
  selector: 'alert-confirm-dialog',
  templateUrl: './alert-confirm-dialog.component.html',
  styles: [
  ]
})
export class AlertConfirmDialogComponent implements AfterContentChecked {

  @Output('onClose')
  onClose: EventEmitter<boolean> = new EventEmitter();

  @Input('config')
  config?: AlertConfirmDialogConfig | null;

  constructor(private dialogService: DialogService) {
  }

  ngAfterContentChecked(): void {
  }

  confirm() {
    this.onClose.emit(true);
  }

  cancel() {
    this.onClose.emit(this.config?.mode == 'success');
  }

}
