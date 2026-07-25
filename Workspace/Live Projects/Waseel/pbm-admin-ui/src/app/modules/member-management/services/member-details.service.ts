import { Injectable } from '@angular/core';
import { ListViewModel } from '../../shared/components/list-view/models/list-view.model';
import { BehaviorSubject, Subject, map } from 'rxjs';
import { MemberDetailsRequest } from '../models/member-details-request.model';
import { MemberDetails, MemberPolicyDetails } from '../models/member-details.model';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { MemberHistory } from '../models/member-history.model';

@Injectable({
    providedIn: 'root'
})
export class MemberDetailsService extends DefaultHttpClient {

    memberDetailsList$: BehaviorSubject<ListViewModel<MemberDetails>> = new BehaviorSubject(new ListViewModel());
    membersHistory$: BehaviorSubject<ListViewModel<MemberHistory>> = new BehaviorSubject(new ListViewModel());

    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.prescriptionservice.memberManagementService);
    }

    getMemberList(request: MemberDetailsRequest) {
        return this.get(`?${request.toQueryParams()}`, {
            subjectToUpdate: this.memberDetailsList$

        });
    }

    getMemberDetails(idNumber: string) {
        return this.httpClient.get("/api/lov/members/" + idNumber);
    }


    getMemberHistory(idNumber: string, pageNumber?: number, recordSize?: number) {
        return this.get(`/${idNumber}?` + "pageNumber=" + pageNumber + "&recordSize=" + recordSize, {
            subjectToUpdate: this.membersHistory$
        });
    }



}
