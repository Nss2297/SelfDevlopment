import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { SfdaManagementService } from '../../service/sfda-management.service';
import { HttpException } from 'src/app/util/default-http-client';
import * as moment from 'moment';

@Component({
    selector: 'upload-sfda-list-dialog',
    templateUrl: './upload-sfda-list-dialog.component.html',
    styles: [
    ]
})
export class UploadSfdaListDialogComponent {
    @Input('onClose')
    onClose: any;
    //   onClose?: (data: any) => void;
    fileSizeExceedErrorMsg: string | null = null;
    effectiveDateError: string | null = null;
    fileName: string | null = null;
    fileSize: Number | null = null;
    fileSizeInMb: String | null = null;
    fileSelected: boolean = false;
    selectedFile: File | undefined;
    effectiveDate!: string;
    responseList: any = [];
    errorList: any = [];
    duplicateList: any = [];
    errorArray: boolean = false;
    listIsLoading: boolean = false;
    addedDrugsList: any;
    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
    currentUser: any;
    constructor(
        private sfdaManagementSVC: SfdaManagementService,
        private authService: AuthService,
        public translate: TranslateService,
        public dialogService: DialogService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }
    resetEffectiveDateError() {
        this.effectiveDateError = null;
      }
    onFileSelected(event: any) {

        this.selectedFile = event.target.files[0];
        console.log(this.selectedFile)
        const file: File = event.target.files[0];
        if (file) {
            const maxSizeInBytes = 5 * 1024 * 1024;

            if (file.size > maxSizeInBytes) {
                // Set an error message and return
                this.fileSelected = false;
                this.fileSizeExceedErrorMsg = 'File size exceeds the limit (5 MB)';
                return;
            }
            this.fileSelected = true;
            this.fileName = file.name;
            this.fileSize = file.size / (1024 * 1024);
            this.fileSizeInMb = this.fileSize.toFixed(2)
            // this.effectiveDateError = null;
            this.fileSizeExceedErrorMsg = null;
            this.resetEffectiveDateError();
            
        }
        else {
            this.fileSelected = false;
            this.fileSizeExceedErrorMsg = null;
        }

    }
    downloadFile() {
        
        const sampleExcelUrl = '/assets/SFDASampleFile.xlsx';

        let actionPerformed = false;
        const b = document.createElement('a');
        b.href = sampleExcelUrl;
        b.download = 'SFDASampleFile.xlsx';
        document.body.appendChild(b);
        b.click();
        document.body.removeChild(b);
        actionPerformed = true;
    }

    uploadFile() {
        let effectiveDateerrormsg = "";
        let uploadtitle = "";
        let uploadSubtitle = "";
        this.responseList = [];
        let fileSizeExceedMsg = '';
        this.translate.get('customization.fileSizeExceedMsg').subscribe(text => fileSizeExceedMsg = text);
        this.translate.get('customization.uploadTitle').subscribe(text => uploadtitle = text);
        this.translate.get('customization.uploadSubTitle').subscribe(text => uploadSubtitle = text);
        if (!this.effectiveDate) {
            this.translate.get('prescription.effectiveDateerrormsg').subscribe(text => {
                this.effectiveDateError = "Please select effectiveDate";
                // this.dialogService.showErrorDialog(effectiveDateerrormsg, (event: any) => {});
            });
            return; 
        }
        this.resetEffectiveDateError();
        this.dialogService.showConfirmDialog(uploadtitle, uploadSubtitle, (data: any) => {
            if (data) {
                this.authService.showSystemLoader();
                if (this.selectedFile) {
                    const maxSizeInBytes = 5 * 1024 * 1024;

                    const formData: FormData = new FormData();
                    formData.append('sfdaFile', this.selectedFile, this.selectedFile.name);


                    const formattedDate = moment(this.effectiveDate).format('DD-MM-YYYY');
                    formData.append('effectiveDate', formattedDate);
                   
                    this.sfdaManagementSVC.uploadFile(formData).subscribe({
                        next: (data) => {
                            this.authService.hideSystemLoader();
                            const uploadResponseModel = JSON.parse(JSON.stringify(data));
                            this.responseList = uploadResponseModel;
                            this.errorList = this.responseList.errors;
                            this.duplicateList = uploadResponseModel.duplicateRecords;

                            if (this.responseList.exclusionListDrugDetailsRequestModel) {
                                if (!this.duplicateList) {
                                    this.duplicateList = [];
                                }
                                this.responseList.exclusionListDrugDetailsRequestModel.forEach((drug: any) => {

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
                                //   this.isOverride = false
                                this.fileSelected = false;
                            }
                            else {
                                this.errorArray = false;
                                //   this.isOverride = false;
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
                            // this.isOverride = false;
                            this.fileSelected = false;
                            let uploadedErrorMsg = '';
                            if (exception instanceof HttpException) {
                                //  this.authService.showSystemLoader();
                                console.log("exception error", exception);
                                if (exception.response.error.errors) {
                                    this.dialogService.showErrorDialog(exception.response.error.errors, () => { })
                                } else if (exception.response.error.errorDescriptions) {
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
                    //   this.isOverride = false;
                    this.translate.get('customization.uploadError').subscribe(text => uploadedErrorMsg = text);
                    this.dialogService.showErrorDialog(uploadedErrorMsg, (event: any) => {
                        if (event) {
                            console.log("error message");
                        }
                    });
                }
            } else {
                let uploadedErrorMsg = '';
                // this.isOverride = false;
                this.translate.get('customization.uploadError').subscribe(text => uploadedErrorMsg = text);
                this.dialogService.showErrorDialog(uploadedErrorMsg, (event: any) => {
                    if (event) {
                        console.log("error message");
                    }
                });
            }
        })
    }

}
