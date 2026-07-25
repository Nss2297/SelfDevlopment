import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service'; 
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-exclusion-list-dialog',
  templateUrl: './edit-exclusion-list-dialog.component.html',
  styles: [
  ]
})
export class EditExclusionListDialogComponent {
  @Input('onClose')
  onClose?: (data: any) => void;

  

  @Input('exclusionDetail')

  exclusionDetail!:{exclusionName:string, exclusionId:string};
  exclusionNameErrorMsg!:string;
  previousExclusionName!:string;

  constructor( 
    private translate: TranslateService,
    private dialogService: DialogService,
    private  exclusionServiceTsService:ExclusionServiceTsService,
    private route:ActivatedRoute
    ){  }

    ngOnInit(){
      // this.route.params.subscribe(parameters=>{
      //   this.exclusionDetail.exclusionId = parameters["id"];
      //   console.log(this.exclusionDetail.exclusionId)
      // })
      // this.exclusionDetail.drugExclusionName = "T3 DESLORATADINE/PSEUD OEPHEDRINE";

      this.previousExclusionName = this.exclusionDetail?.exclusionName!;
    }

    updateDrugExclusionName(){
      this.exclusionNameErrorMsg = "";
      if(this.exclusionDetail.exclusionName){
        if(this.previousExclusionName != this.exclusionDetail.exclusionName){
          this.exclusionNameErrorMsg = "";
          this.exclusionServiceTsService.updateDrugExclusionListName(this.exclusionDetail.exclusionId,this.exclusionDetail.exclusionName).subscribe((data)=>{
          this.closeDialog();
          let exclusionNameMessage = "";
          this.translate.get('prescription.exclusionNameEditMsg').subscribe(text => exclusionNameMessage = text);
          this.dialogService.showSuccessDialog(exclusionNameMessage, (data:any) => {
          })
        },(error) => {
          if(error.response.error.errors){
            this.dialogService.showErrorDialog(error.response.error.errors, (data: any) => { 
              
            })
          }else{
            this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errors, (data: any) => { })
          }
        })
        }
        else{
          this.translate.get('prescription.sameExclusionNameValidation').subscribe(text => this.exclusionNameErrorMsg = text);
        }
      }else{
        this.translate.get('prescription.validExclusionName').subscribe(text => this.exclusionNameErrorMsg = text);
      }
    }

    closeDialog() {
      if (this.onClose) {
        this.onClose(null);
      }
    }
}
