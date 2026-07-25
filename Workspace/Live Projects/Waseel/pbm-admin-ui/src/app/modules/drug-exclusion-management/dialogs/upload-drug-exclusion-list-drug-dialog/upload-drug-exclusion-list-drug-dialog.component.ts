import { Component, Input } from '@angular/core';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { HttpException } from 'src/app/util/default-http-client';
@Component({
  selector: 'app-upload-drug-exclusion-list-drug-dialog',
  templateUrl: './upload-drug-exclusion-list-drug-dialog.component.html',
  styles: [
  ]
})
export class UploadDrugExclusionListDrugDialogComponent {
  
  @Input('addedDrugsList') addedDrugsList: any;

  errorCode: string = '';
  errorList:any =[];
  duplicateList:any = [];
  splitString:any =[];
  responseList:any =[];

  errorArray:boolean=false;
 array : any= [];
 currentUser:any;
  constructor(
   public   exclusionTsService:ExclusionServiceTsService,
   private authService: AuthService,
   public translate:TranslateService,
   public dialogService:DialogService
    )
   {
    this.authService.user$.subscribe( data =>{
      this.currentUser = data;
    })
   }


  fileName: string | null = null;
  fileSize:Number | null = null;
  fileSizeInMb:String | null = null;
  fileSelected: boolean = false;
  selectedFile: File | undefined;
  listIsLoading: boolean = false;
  isOverride: boolean = false;
  @Input('onClose')
  onClose: any;
  overrideIfExists = false;
  ngOnInit(): void {
    
  }
  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }

  

  onFileSelected(event: any) {
    
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile)
    const file: File = event.target.files[0];
    if (file) {
      this.fileSelected = true;
      this.fileName = file.name; 
      this.fileSize = file.size/(1024 * 1024);
      this.fileSizeInMb = this.fileSize.toFixed(2)
      
    }
    else {
      this.fileSelected = false;
    }

  }

 

uploadFile() {
  let uploadtitle = "";
  let uploadSubtitle = "";
  this.responseList = [];
  let fileSizeExceedMsg = '';
  this.translate.get('customization.fileSizeExceedMsg').subscribe(text => fileSizeExceedMsg = text);
  this.translate.get('customization.uploadTitle').subscribe(text => uploadtitle = text);
  this.translate.get('customization.uploadSubTitle').subscribe(text => uploadSubtitle = text);
  this.dialogService.showConfirmDialog(uploadtitle, uploadSubtitle, (data: any) => {
    if (data) {
      this.authService.showSystemLoader();
      if (this.selectedFile) {
        const maxSizeInBytes = 5 * 1024 * 1024;
        // if (this.selectedFile.size > maxSizeInBytes) {
           //this.authService.hideSystemLoader();
    
        //   this.dialogService.showErrorDialog(fileSizeExceedMsg, (data:any) => {
        //   });
         
         //} else {
          const isOverrideParam = this.isOverride ? 'true' : 'false';
    
          this.exclusionTsService.uploadFile(this.selectedFile).subscribe({
            next: (data) => {
              this.authService.hideSystemLoader();
              const uploadResponseModel = JSON.parse(JSON.stringify(data));
              this.responseList = uploadResponseModel;
              this.errorList = this.responseList.errors;
              this.duplicateList = uploadResponseModel.duplicateRecords;

              if(this.responseList.exclusionListDrugDetailsRequestModel){
                if(!this.duplicateList){
                  this.duplicateList = [];
                }
                this.responseList.exclusionListDrugDetailsRequestModel.forEach((drug:any) => {
  
                  let isDuplicateDrug = this.addedDrugsList.some((element: any) => {
                    return element.drugCode == drug.drugCode && element.drugName == drug.drugName;
                  });
  
                  if (isDuplicateDrug) {
                    let duplicateMessage = `Drug with code: ${drug.drugCode} is already exist in the Drugs Table. `
                    this.duplicateList.push(duplicateMessage);
                  }
                });
              }
    
              let uploadedMsg = '';
              let errorListMsg = '';
    
              if ((this.errorList && this.errorList.length > 0) || (this.duplicateList && this.duplicateList.length > 0)) {
                this.errorArray = true;
                this.isOverride = false
                this.fileSelected = false;
              } 
              else {
                this.errorArray = false;
                this.isOverride = false;
                this.fileSelected = false;
                this.translate.get('customization.uploadSuccess').subscribe(text => uploadedMsg = text);
                this.dialogService.showSuccessDialog(uploadedMsg, (event: any) => {
                  if (event) {
                    this.onClose(this.responseList);
                  }
                });
              }
            },
            error: (exception) => {
              this.authService.showSystemLoader();
              this.listIsLoading = true;
              this.isOverride = false;
              this.fileSelected = false;
              let uploadedErrorMsg = '';
              if (exception instanceof HttpException) {
              //  this.authService.showSystemLoader();
                console.log("exception error", exception);
                if(exception.response.error.errors){
                this.dialogService.showErrorDialog(exception.response.error.errors, () => { })
              }else if(exception.response.error.errorDescriptions){
                this.dialogService.showErrorDialog(exception.response.error.errorDescriptions, () => { })
              }
              }
              this.listIsLoading = false;
              this.authService.hideSystemLoader();
            }
          });
        //}
      } else {
        let uploadedErrorMsg = '';
        this.isOverride = false;
        this.translate.get('customization.uploadError').subscribe(text => uploadedErrorMsg = text);
        this.dialogService.showErrorDialog(uploadedErrorMsg, (event: any) => {
          if (event) {
            console.log("error message");
          }
        });
      }
    } else {
      let uploadedErrorMsg = '';
      this.isOverride = false;
      this.translate.get('customization.uploadError').subscribe(text => uploadedErrorMsg = text);
      this.dialogService.showErrorDialog(uploadedErrorMsg, (event: any) => {
        if (event) {
          console.log("error message");
        }
      });
    }
  })
}

downloadFile(){
    
    const sampleExcelUrl = 'assets/Drug List Sample.xlsx';
    // const payersampleExcelUrl = 'assets/Drug to Diagnosis Customization Payer  Sample (2) (1).xlsx';
    let actionPerformed = false;
    
    
    // if (this.currentUser.authorities.some((data: any) => data.authority.includes("PBM_ADMIN"))) {
      const b = document.createElement('a');
      b.href = sampleExcelUrl;
      b.download = 'Drug List Sample';
      document.body.appendChild(b);
      b.click();
      document.body.removeChild(b);
      actionPerformed = true;
    // }

    //  else {
    //   const a = document.createElement('a');
    //   a.href = payersampleExcelUrl;
    //   a.download = 'Drug to Diagnosis Customization Payer Sample.xlsx';
    //   document.body.appendChild(a);
    //   a.click();
    //   document.body.removeChild(a);
      
    // }
   
    
  }
}
