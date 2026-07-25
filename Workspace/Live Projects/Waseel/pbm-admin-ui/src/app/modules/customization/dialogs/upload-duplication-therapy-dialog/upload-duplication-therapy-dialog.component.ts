
import { Component, Input } from '@angular/core';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { HttpException } from 'src/app/util/default-http-client';

@Component({
    selector: 'app-upload-duplication-therapy-dialog',
    templateUrl: './upload-duplication-therapy-dialog.component.html',
    styleUrls: ['./upload-duplication-therapy-dialog.component.css']
})
export class UploadDuplicationTherapyDialogComponent {

    @Input('onClose')
    onClose?: (data: any) => void;

    currentUser: any;
    fileSelected: boolean = false;
    fileName: string | null = null;
    fileSize: Number | null = null;
    fileSizeInMb: String | null = null;
    selectedFile: File | undefined;
    isOverride: boolean = false;
    errorArray: boolean = false;
    listIsLoading: boolean = false;
    addedDrugsList: any;
    overrideIfExists = false;

    errorList: any = [];
    duplicateList: any = [];
    splitString: any = [];
    responseList: any = [];
    fileSizeExceedErrorMsg: string | null = null;
    successMessage: any;

    constructor(public dssCustomizationSVC: DssCustomizationService,
        private authService: AuthService,
        public translate: TranslateService,
        public dialogService: DialogService) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit() {

    }

    downloadSampleFile() {
        const sampleExcelUrl = 'assets/admin/Drug To Duplicate Therapy Sample File.xlsx';
        const payersampleExcelUrl = 'assets/payer/Drug To Duplicate Therapy Sample File (2) (2).xlsx';

        if (this.currentUser.authorities.some((data: any) => data.authority.includes("PBM_ADMIN"))) {
            window.location.href = sampleExcelUrl;
        } else {
            window.location.href = payersampleExcelUrl;
        }
    }

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

    uploadDuplicationTherapyCustomizationData() {
        let uploadtitle = "";
        let uploadSubtitle = "";
        this.responseList = [];
        this.translate.get('customization.uploadTitle').subscribe(text => uploadtitle = text);
        this.translate.get('customization.uploadSubTitle').subscribe(text => uploadSubtitle = text);
        this.dialogService.showConfirmDialog(uploadtitle, uploadSubtitle, (data: any) => {

            if (data) {

                this.authService.showSystemLoader();


                if (this.selectedFile) {
                    const maxSizeInBytes = 5 * 1024 * 1024;
                    const isOverrideParam = this.isOverride ? 'true' : 'false';

                    this.dssCustomizationSVC.uploadDuplicatioExcelDatafile(this.selectedFile, isOverrideParam).subscribe({
                        next: (data) => {
                            if (data.errorList) {
                                this.authService.showSystemLoader();
                                this.listIsLoading = true;
                                this.isOverride = false;
                                this.fileSelected = false;
                                this.errorArray = true;
                                this.errorList = data.errorList || [];
                                this.duplicateList = data.duplicateRecords || [];

                            }
                            this.authService.hideSystemLoader();
                            const uploadResponseModel = JSON.parse(JSON.stringify(data));
                            this.responseList = uploadResponseModel;

                            this.errorList = uploadResponseModel.errorList;
                            this.duplicateList = uploadResponseModel.duplicateRecords;


                            let uploadedMsg = '';

                            if ((this.errorList.length || this.duplicateList.length) > 0) {
                                this.isOverride = false
                                this.fileSelected = false;
                                this.successMessage = uploadResponseModel.message;
                            }

                            else {
                                this.errorArray = false;
                                this.isOverride = false;
                                this.fileSelected = false;
                                this.translate.get('customization.duplicationUploadSuccessMsg').subscribe(text => uploadedMsg = text);
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
                            if (exception instanceof HttpException) {
                                this.dialogService.showErrorDialog(exception.response.error.errors, () => { })
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
                    }
                });
            }
        })
    }

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
}

