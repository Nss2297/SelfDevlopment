import { Component } from '@angular/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { ChronicDiseases, MemberInfoDetailsModel, MemberPolicyDetails } from '../../../models/member-details.model';
import { MemberDetailsService } from '../../../services/member-details.service';
import { BehaviorSubject, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { MemberHistory, memberHistroyRequest } from '../../../models/member-history.model';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
    selector: 'app-member-management-details',
    templateUrl: './member-management-details.component.html',
    styleUrls: ['./member-management-details.component.css']
})
export class MemberManagementDetailsComponent {

    memberInfoData: MemberInfoDetailsModel = new MemberInfoDetailsModel();
    policyDetailsData: ListViewModel<MemberPolicyDetails> = new ListViewModel();
    policyDetailsData$: BehaviorSubject<ListViewModel<MemberPolicyDetails>> = new BehaviorSubject(new ListViewModel<MemberPolicyDetails>());
    policyDetailsListPrototype = MemberPolicyDetails.prototype;
    dataControl: MemberPolicyDetails = new MemberPolicyDetails();
    listIsLoading: boolean = false;
    noContentSubtitle: boolean = true;
    currentTab = 0;
    idNumber!: string;
    subscriptions: Subscription[] = [];
    memberHistorydata: ListViewModel<MemberHistory> = new ListViewModel();
    memberHistoryListPrototype = MemberHistory.prototype;
    memberHistorydataControl: memberHistroyRequest = new memberHistroyRequest();
    chronicDiseaseData: ChronicDiseases[] = [];

    constructor(
        private router: Router,
        private memberManagementSVC: MemberDetailsService,
        private activatedRoute: ActivatedRoute,
        private authService: AuthService,) {
    }

    ngOnInit(): void {

        this.dataControl.recordSize = 10;
        this.subscriptions.push(this.activatedRoute.params.subscribe(params => {
            this.idNumber = params['id'];
        }));

        this.subscriptions.push(this.memberManagementSVC.membersHistory$.subscribe(data => {
            this.memberHistorydata = data;
        }));

        

        if (!this.memberInfoData) {
            this.fetchData();

        }

    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.memberManagementSVC.getMemberDetails(this.idNumber).subscribe((data: any) => {

            this.memberInfoData.memberName = data.memberName;
            this.memberInfoData.idNumber = data.idNumber;
            this.memberInfoData.gender = data.gender;
            this.memberInfoData.dateOfBirth = data.dateOfBirth;
            this.memberInfoData.maritalStatus = data.maritalStatus;
            this.memberInfoData.nationality = data.nationality;
            this.memberInfoData.mobileNumber = data.mobileNumber;
            this.memberInfoData.email = data.email;

            if (data.memberChronicDiseaseResponseModel && data.memberChronicDiseaseResponseModel.length > 0) {
                this.chronicDiseaseData = [];
                data.memberChronicDiseaseResponseModel.forEach((chronicDisease: any) => {
                    if (this.chronicDiseaseData.findIndex(x => x.chronicDiseaseId === chronicDisease.chronicDiseaseId) == -1) {
                        let chronicDiseases = new ChronicDiseases();
                        chronicDiseases.chronicDiseaseId = chronicDisease.chronicDiseaseId;
                        chronicDiseases.chronicDiseaseName = chronicDisease.chronicDiseaseName;
                        chronicDiseases.payerId = chronicDisease.payerId;
                        if (chronicDisease) {
                            this.chronicDiseaseData.push(chronicDiseases)
                        }
                    }

                });
            }

            if (data.memberPolicyDetails && data.memberPolicyDetails.length > 0) {
                data.memberPolicyDetails.forEach((memberPolicyData: any) => {
                    if (this.policyDetailsData.content.findIndex(x => x.policyNumber == memberPolicyData.policyNumber) == -1) {
                        let policyData = new MemberPolicyDetails();
                        policyData.policyNumber = memberPolicyData.policyNumber;
                        policyData.policyHolderName = memberPolicyData.policyHolderName;
                        policyData.memberId = memberPolicyData.memberId;
                        policyData.classCode = memberPolicyData.policyClasses[0].classCode;
                        policyData.className = memberPolicyData.policyClasses[0].className;
                        this.policyDetailsData.content.push(policyData);
                    }
                });
                this.policyDetailsData.totalPages = 1;
                this.policyDetailsData$.next(this.policyDetailsData);
            }

            this.authService.hideSystemLoader();
            this.listIsLoading = false;
        })

        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.memberManagementSVC.getMemberHistory(this.idNumber, this.memberHistorydataControl.pageNumber, this.memberHistorydataControl.recordSize).subscribe(data => {
            this.authService.hideSystemLoader();
            this.listIsLoading = false;
        });


    }

    getExtraClassesForListView() {
        return this.memberHistorydata.content.map((item, index) => {

            if (item.ePrescriptionStatus?.toUpperCase().startsWith("APPROVED") || item.ePrescriptionStatus?.toUpperCase().startsWith("DISPENSED")) {
                if (!item.lastUpdateDateTime) {
                    return { [index + ':2']: 'text-success dark:text-success-300 body-2-medium' }
                } else {
                    return { [index + ':3']: 'text-success dark:text-success-300 body-2-medium' }
                }
            } else if (item.ePrescriptionStatus?.toUpperCase().startsWith("REJECTED") || item.ePrescriptionStatus?.toUpperCase().startsWith("INVALID") || item.ePrescriptionStatus?.toUpperCase().startsWith("FAILED")
                || item.ePrescriptionStatus?.toUpperCase().startsWith("CANCELLED")) {
                if (!item.lastUpdateDateTime) {
                    return { [index + ':2']: 'text-error dark:text-error-400 body-2-medium' }
                } else {
                    return { [index + ':3']: 'text-error dark:text-error-400 body-2-medium' }
                }
            } else if (item.ePrescriptionStatus?.toUpperCase().startsWith("PENDING") || item.ePrescriptionStatus?.toUpperCase().startsWith("PARTIAL_APPROVED")
                || item.ePrescriptionStatus?.toUpperCase().startsWith("PARTIAL_DISPENSED")) {
                if (!item.lastUpdateDateTime) {
                    return { [index + ':2']: 'text-warning dark:text-error-400 body-2-medium' }
                } else {
                    return { [index + ':3']: 'text-warning dark:text-error-400 body-2-medium' }
                }
            } else {
                return { [index + ':2']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }

    onBackClick = () => {
        this.router.navigateByUrl('/member-management/list');
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(input => input.unsubscribe());
    }
}
