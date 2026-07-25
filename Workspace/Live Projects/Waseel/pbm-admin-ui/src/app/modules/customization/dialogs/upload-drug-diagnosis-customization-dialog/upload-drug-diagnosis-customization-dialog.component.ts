import { Component, Input, OnInit } from '@angular/core';
import { CustomizationService } from '../../services/customization-service/customization.service';
import { HttpException } from 'src/app/util/default-http-client';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';


@Component({
    selector: 'upload-drug-diagnosis-customization-dialog',
    templateUrl: './upload-drug-diagnosis-customization-dialog.component.html',
    styles: [
    ]
})
export class UploadDrugDiagnosisCustomizationDialogComponent implements OnInit {
    errorCode: string = '';

    // errorList:String | null = null
    errorList: any = [];
    duplicateList: any = [];
    splitString: any = [];
    responseList: any = [];
    fileSizeExceedErrorMsg: string | null = null;
    errorArray: boolean = false;
    array: any = [];
    currentUser: any;
    successMessage: any;
    constructor(
        public customizationService: CustomizationService,
        private authService: AuthService,
        public translate: TranslateService,
        public dialogService: DialogService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }


    fileName: string | null = null;
    fileSize: Number | null = null;
    fileSizeInMb: String | null = null;
    fileSelected: boolean = false;
    selectedFile: File | undefined;
    listIsLoading: boolean = false;
    isOverride: boolean = false;
    @Input('onClose')
    onClose?: (data: any) => void;
    overrideIfExists = false;
    ngOnInit(): void {

    }
    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }



    // onFileSelected(event: any) {

    //     this.selectedFile = event.target.files[0];
    //     console.log(this.selectedFile)
    //     const file: File = event.target.files[0];
    //     if (file) {
    //         this.fileSelected = true;
    //         this.fileName = file.name;
    //         this.fileSize = file.size / (1024 * 1024);
    //         this.fileSizeInMb = this.fileSize.toFixed(2)

    //     }
    //     else {
    //         this.fileSelected = false;
    //     }

    // }
    onFileSelected(event: any) {

        this.selectedFile = event.target.files[0];
        console.log(this.selectedFile)
        const file: File = event.target.files[0];
        if (file) {
            const maxSizeInBytes = 5 * 1024 * 1024;

            if (file.size > maxSizeInBytes) {

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


        }
        else {
            this.fileSelected = false;
            this.fileSizeExceedErrorMsg = null;
        }

    }



    uploadFile() {
        let uploadtitle = "";
        let uploadSubtitle = "";
        this.responseList = [];
        this.translate.get('customization.uploadTitle').subscribe(text => uploadtitle = text);
        this.translate.get('customization.uploadSubTitle').subscribe(text => uploadSubtitle = text);
        this.dialogService.showConfirmDialog(uploadtitle, uploadSubtitle, (data: any) => {
            // this.authService.showSystemLoader();

            if (data) {

                this.authService.showSystemLoader();


                if (this.selectedFile) {
                    const maxSizeInBytes = 5 * 1024 * 1024;
                    console.log("selected file size", this.selectedFile.size);
                    console.log("maxsize", maxSizeInBytes);
                    const isOverrideParam = this.isOverride ? 'true' : 'false';

                    this.customizationService.uploadFile(this.selectedFile, isOverrideParam).subscribe({
                        next: (data) => {
                            this.authService.hideSystemLoader();
                            const uploadResponseModel = JSON.parse(JSON.stringify(data));
                            this.responseList = uploadResponseModel
                            console.log("responseList", this.responseList)
                            console.log("responseList length", this.responseList.length)

                            this.errorList = uploadResponseModel.errorList;
                            this.duplicateList = uploadResponseModel.duplicateRecords
                            console.log("errorlist length", this.errorList.length)
                            console.log("duplicate length", this.duplicateList.length)


                            let uploadedMsg = '';
                            let errorListMsg = '';

                            if ((this.errorList.length || this.duplicateList.length) > 0) {
                                console.log("when errorList is not  null")
                                this.errorArray = true;
                                this.isOverride = false
                                this.fileSelected = false;
                                this.successMessage = uploadResponseModel.message;
                            }

                            else {
                                console.log("when errorList is null")
                                this.errorArray = false;
                                this.isOverride = false;
                                this.fileSelected = false;
                                this.translate.get('customization.uploadSuccess').subscribe(text => uploadedMsg = text);
                                this.dialogService.showSuccessDialog(uploadedMsg, (event: any) => {
                                    if (event) {
                                        this.closeDialog();
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
                                console.log("exception error", exception);
                                this.dialogService.showErrorDialog(exception.response.error.errorDescriptions, () => { })
                            }
                            this.listIsLoading = false;
                            this.authService.hideSystemLoader();
                        }
                    });
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
            }
            else {
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



    downloadFile() {

        const sampleExcelUrl = 'assets/admin/Drug to Diagnosis Customization Sample (2) (1).xlsx';
        const payersampleExcelUrl = 'assets/payer/Drug to Diagnosis Customization  Sample.xlsx';
        let actionPerformed = false;


        if (this.currentUser.authorities.some((data: any) => data.authority.includes("PBM_ADMIN"))) {
            const b = document.createElement('a');
            b.href = sampleExcelUrl;
            b.download = 'Drug to Diagnosis Customization Sample (2) (1).xlsx';
            document.body.appendChild(b);
            b.click();
            document.body.removeChild(b);
            actionPerformed = true;
        }

        else {
            const a = document.createElement('a');
            a.href = payersampleExcelUrl;
            a.download = 'Drug to Diagnosis Customization  Sample.xlsx';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);

        }


    }


}
