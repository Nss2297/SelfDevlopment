import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { MemberDetails } from '../../models/member-details.model';
import { MemberDetailsRequest } from '../../models/member-details-request.model';
import { MemberDetailsService } from '../../services/member-details.service';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

@Component({
    selector: 'app-member-management-list',
    templateUrl: './member-management-list.component.html',
    styleUrls: ['./member-management-list.component.css']
})
export class MemberManagementListComponent {

    data: ListViewModel<MemberDetails> = new ListViewModel();
    listPrototype = MemberDetails.prototype;
    dataControl: MemberDetailsRequest = new MemberDetailsRequest();
    listIsLoading: boolean = false;
    isFilterDrawerOpen: boolean = false;
    errorCode: string = '';
    subscription: Subscription[] = [];
    noContentSubtitle: boolean = true;
    trimmedMemberName!: string;

    genderList = [
        {
            key: "Male",
            value: "Male"
        },
        {
            key: "Female",
            value: "Female"
        }
    ];

    filterForm: FormGroup = new FormGroup({
        name: new FormControl('', { validators: Validators.required }),
        idNumber: new FormControl('', { validators: Validators.required }),
        gender: new FormControl('', { validators: Validators.required }),
        nationality: new FormControl('', { validators: Validators.required })
    });

    constructor(
        private memberManagementSVC: MemberDetailsService,
        private authService: AuthService,
        private router: Router,
        private datePipe: DatePipe
    ) { }

    ngOnInit(): void {
        this.subscription.push(this.memberManagementSVC.memberDetailsList$.subscribe(data => {
            if (!data) {
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
            } else {
                this.data = data;
                this.data.content.forEach(memberDetails => {
                    memberDetails.name = memberDetails.name ?? "-";
                    memberDetails.nationality = memberDetails.nationality ?? "-";
                    if (memberDetails.dateOfBirth?.includes("T")) {
                        memberDetails.dateOfBirth = moment.utc(memberDetails.dateOfBirth).format("DD/MM/YYYY");
                    }
                })
            }
            this.listIsLoading = false;
        }))
    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.memberManagementSVC.getMemberList(this.dataControl).subscribe();
        this.authService.hideSystemLoader();
    }

    // showItemPerPageDropdown(totalRecords: number): boolean {
    //     return totalRecords >= 10;
    // }

    applyFilter() {
        let name = this.filterForm.controls['name'].value;
        this.trimmedMemberName = name?.replace(/^\s+|\s+$/g, '');
        this.dataControl.name = this.trimmedMemberName;
        this.filterForm.controls['name'].setValue(this.trimmedMemberName);

        let idNumber = this.filterForm.controls['idNumber'].value;
        let trimmedMemberId = idNumber?.replace(/^\s+|\s+$/g, '');
        idNumber = trimmedMemberId;
        this.dataControl.idNumber = trimmedMemberId;
        this.filterForm.controls['idNumber'].setValue(trimmedMemberId);

        let nationality = this.filterForm.controls['nationality'].value;
        let trimmedNationality = nationality?.replace(/^\s+|\s+$/g, '');
        this.dataControl.nationality = trimmedNationality;
        this.filterForm.controls['nationality'].setValue(trimmedNationality);

        this.dataControl.gender = this.filterForm.controls['gender'].value;

        this.dataControl.pageNumber = 0;
        this.fetchData();
        this.isFilterDrawerOpen = false;
    }

    resetFilter() {

        this.dataControl.name = undefined;
        this.dataControl.idNumber = undefined;
        this.dataControl.gender = undefined;
        this.dataControl.nationality = undefined;
        this.filterForm.setValue({
            'name': null,
            'idNumber': null,
            'gender': '',
            'nationality': null
        });
    }

    checkNumberType(value: any) {
        if (!Number(value)) {
            this.filterForm.get('idNumber')?.reset();
            this.filterForm.get('idNumber')?.markAsDirty();
            return;
        }
    }

    navigateToMemberDetails(idNumber: string) {
        this.router.navigate(["member-management/details", idNumber]);
    }
    ngOnDestroy() {
        this.memberManagementSVC.memberDetailsList$.next(new ListViewModel<MemberDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
