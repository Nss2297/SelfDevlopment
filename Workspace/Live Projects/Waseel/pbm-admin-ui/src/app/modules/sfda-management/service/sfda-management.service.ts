import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { SfdaRequestModel } from '../models/sfda-list-request.model';
import { SfdaListModel } from '../models/sfda-list.model';
import { ListViewModel } from '../../shared/components/list-view/models/list-view.model';
import { BehaviorSubject } from 'rxjs';
import { SfdaDrugListDetailsModel, SfdaDrugListRequestModel } from '../models/sfda-drug-details.model';

@Injectable({
    providedIn: 'root'
})
export class SfdaManagementService extends DefaultHttpClient {

    sfdaList$: BehaviorSubject<ListViewModel<SfdaListModel>> = new BehaviorSubject(new ListViewModel());
    drugsList$: BehaviorSubject<ListViewModel<SfdaDrugListDetailsModel>> = new BehaviorSubject(new ListViewModel<SfdaDrugListDetailsModel>());
    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.prescriptionservice.sfdaManagementService);
    }

    getSfdaList(request: SfdaRequestModel) {
        return this.get(`?${request.toQueryParams()}`, {
            subjectToUpdate: this.sfdaList$
        });

    }

    getSpecificDrugDetailsData(waseelDrugId: string, drugListId: number) {
        return this.get(`/${drugListId}/drugs/${waseelDrugId}`)
    }

    getSfdaDrugListDetails(drugListId: number, request?: SfdaDrugListRequestModel) {
        return this.get(`/${drugListId}?${request?.toQueryParams()}`);
    }

    updateSfdaDrugDetails(waseelDrugId: string, drugListId: number, requestBody: SfdaDrugListDetailsModel) {
        return this.put(`/${drugListId}/drugs/${waseelDrugId}`, requestBody)
    }

    addNewSfdaDrugDetails(drugListId: number, requestBody: SfdaDrugListDetailsModel) {
        return this.post(`/${drugListId}/drugs`, requestBody)
    }



    uploadFile(formData: FormData) {
        // const formData: FormData = new FormData();
        // formData.append('file', file, file.name);
        // formData.append('effectiveDate', effectiveDate);

        // return this.post<any>(`?/upload/${payerId}`+"effectiveDate="+effectiveDate , formData);
        return this.post<any>(`/upload`, formData);

    }

    deleteSFDA(drugListId: string) {
        return this.delete(`/${drugListId}`);
    }

    deleteSFDADetails(drugListId: number) {
        return this.delete(`/${drugListId}`);
    }

    deletedrug(drugListId: number, waseelDrugId: string) {
        return this.delete(`/${drugListId}/drugs/${waseelDrugId}`);
    }

}

