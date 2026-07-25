import { Component, Input } from '@angular/core';
import { formularyProviderService } from '../../Services/formulary-provider-service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';


@Component({
  selector: 'edit-formulary-name-dialog',
  templateUrl: './edit-formulary-name-dialog.component.html',
  styles: [
  ]
})
export class EditFormularyNameDialogComponent {
  @Input('onClose')
  onClose?: (data: any) => void;

  @Input('formularyDetail')
  formularyDetail?:{formularyName:string, formularyId:string};
  formularyNameErrorMsg!:string;
  previousFormularyName!:string;
  
  constructor( 
    private formularyProviderSvc:formularyProviderService,
    private translate: TranslateService,
    private dialogService: DialogService,
    
    ){  }

    ngOnInit(){
      this.previousFormularyName = this.formularyDetail?.formularyName!;
    }
  
    updateFormularyName(){
      this.formularyNameErrorMsg = "";
      if(this.formularyDetail?.formularyName){
        if(this.previousFormularyName != this.formularyDetail?.formularyName){
          this.formularyNameErrorMsg = "";
          this.formularyProviderSvc.updateFormularyName(this.formularyDetail?.formularyId!,this.formularyDetail?.formularyName!).subscribe((data)=>{
          this.closeDialog();
          let formularyNameMessage = "";
          this.translate.get('prescription.formularyNameEditMsg').subscribe(text => formularyNameMessage = text);
          this.dialogService.showSuccessDialog(formularyNameMessage, (data:any) => {
          })
        },(error) => {
          if(error.response.error.errorDescription){
            this.dialogService.showErrorDialog(error.response.error.errorDescription, (data: any) => { 
              
            })
          }else{
            this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
          }
        })
        }
        else{
          this.translate.get('prescription.sameFormularyNameValidation').subscribe(text => this.formularyNameErrorMsg = text);
        }
      }else{
        this.translate.get('prescription.validFormularyName').subscribe(text => this.formularyNameErrorMsg = text);
      }
    }
  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
}