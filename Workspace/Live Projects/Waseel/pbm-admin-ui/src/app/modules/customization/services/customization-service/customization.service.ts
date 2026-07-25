import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { DrugDiagnosisDetails } from '../../models/drug-diagnosis-details.model';
import { DrugToDiagnosisRequest } from '../../models/drug-diagnosis-request.model';

@Injectable({
    providedIn: 'root'
})
export class CustomizationService extends DefaultHttpClient {

    drugToDiagnosis$: BehaviorSubject<ListViewModel<DrugDiagnosisDetails>> = new BehaviorSubject(new ListViewModel());
    private hostUrl = environment.backend.hostname;
    private url = this.hostUrl + environment.backend.admin.customization;

    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.admin.customization);
    }

    getDrugToDiagnosisCustomizations(request: DrugToDiagnosisRequest) {
        return this.get(`?${request.toQueryParams()}`, {
            subjectToUpdate: this.drugToDiagnosis$
        });
    }

    getDrugToDiagnosisCustomizationsById(id: string) {
        return this.get<DrugDiagnosisDetails>(`/${id}`);
    }

    deleteDrugToDiagnosisData(itemId: String) {
        return this.httpClient.delete(this.url + `/${itemId}`, { observe: 'response' });
    }

    addDrugToDiagnosisData(requestBody: { serviceCode: string, icdCode: string, payerId: string, moduleName: string, categoryOfApproval: string, rejectionCategory: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.post<{ id: string }>('', requestBody);
    }

    editDrugToDiagnosisData(itemId: string, requestBody: { serviceCode: string, icdCode: string, payerId: string, moduleName: string, categoryOfApproval: string, rejectionCategory: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.put<{ id: string }>(`/${itemId}`, requestBody);
    }
    // uploadDrugToDiagnosisData(){
    //   return this.post<{ id: string}>( );
    // }
    uploadFile(file: File, isOverride: string = 'false') {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);


        return this.post<any>(`?isOverride=${isOverride}`, formData);
    }



}
// getPrescriptions(request: PrescriptionRequest) {
//   return this.get(`?${request.toQueryParams()}?pharmacyUser=true`, {
//     subjectToUpdate: this.prescription$
//   });
// }

