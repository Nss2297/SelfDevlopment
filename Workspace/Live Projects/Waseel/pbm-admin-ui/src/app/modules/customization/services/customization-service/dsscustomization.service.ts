import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { ListViewModel } from "src/app/modules/shared/components/list-view/models/list-view.model";
import { DefaultHttpClient } from "src/app/util/default-http-client";
import { environment } from "src/environments/environment";
import { DrugGenderDetails } from "../../models/drug-gender-details.model";
import { DrugGenderRequest } from "../../models/drug-gender-request.model";
import { DrugAgeRequest } from "../../models/drug-age-request.model";
import { DrugAgeDetails } from "../../models/drug-age-details.model";
import { DrugToDrugDetails } from "../../models/drug-drug-details.model";
import { DrugToDrugInteractionRequest } from "../../models/drug-drug-request.model";
import { DuplicationRequest } from "../../models/duplication-request.model";
import { DuplicationDetails } from "../../models/duplication-details.model";
@Injectable({
    providedIn: 'root'
})
export class DssCustomizationService extends DefaultHttpClient {
    drugToAgelist$: BehaviorSubject<ListViewModel<DrugAgeDetails>> = new BehaviorSubject(new ListViewModel());
    drugToGender$: BehaviorSubject<ListViewModel<DrugGenderDetails>> = new BehaviorSubject(new ListViewModel());
    drugToDrugInteractionList$: BehaviorSubject<ListViewModel<DrugToDrugDetails>> = new BehaviorSubject(new ListViewModel());
    duplicationList$: BehaviorSubject<ListViewModel<DuplicationDetails>> = new BehaviorSubject(new ListViewModel());
    private hostUrl = environment.backend.hostname;
    private url = this.hostUrl + environment.backend.admin.dssCustomization;

    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.admin.dssCustomization);
    }

    getDrugToGenderCustomizations(request: DrugGenderRequest) {
        return this.get(`/gender?${request.toQueryParams()}`, {
            subjectToUpdate: this.drugToGender$
        });
    }


    deleteDrugToGenderData(itemId: String) {
        return this.httpClient.delete(this.url + `/gender/${itemId}`, { observe: 'response' });
    }

    getDrugToGenderCustomizationsById(id: string) {
        return this.get<DrugGenderDetails>(`/gender/${id}`);
    }

    addDrugToGenderData(requestBody: { serviceCode: string, gender: string, payerId: string, moduleName: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.post<{ id: string }>('/gender', requestBody);
    }

    editDrugToGenderData(itemId: string, requestBody: { serviceCode: string, gender: string, payerId: string, moduleName: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.put<{ id: string }>(`/gender/${itemId}`, requestBody);
    }

    uploadDrugGenderCustomizationExcelDatafile(file: File, isOverride: string = 'false') {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);


        return this.post<any>(`/gender?isOverride=${isOverride}`, formData);
    }

    getDrugToAgeCustomizations(request: DrugAgeRequest) {
        return this.get(`/age?${request.toQueryParams()}`, {
            subjectToUpdate: this.drugToAgelist$
        });
    }

    deleteDrugToAgeData(itemId: String) {
        return this.httpClient.delete(this.url + `/age/${itemId}`, { observe: 'response' });
    }

    getDrugToAgeCustomizationsById(id: string) {
        return this.get<DrugAgeDetails>(`/age/${id}`);
    }

    uploadDrugAgeCustomizationExcelDatafile(file: File, isOverride: string = 'false') {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);
        return this.post<any>(`/age?isOverride=${isOverride}`, formData);
    }
    addDrugToAgeData(requestBody: { serviceCode: string, age: string, payerId: string, moduleName: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.post<{ id: string }>('/age', requestBody);
    }

    editDrugToAgeData(itemId: string, requestBody: { serviceCode: string, age: string, payerId: string, moduleName: string, serviceStatus: string, additionalRejectionReason: string }) {
        return this.put<{ id: string }>(`/age/${itemId}`, requestBody);
    }


    getDrugToDrugInteractionList(request: DrugToDrugInteractionRequest) {
        return this.get(`/drug?${request.toQueryParams()}`, {
            subjectToUpdate: this.drugToDrugInteractionList$
        });
    }

    deleteDrugToDrugInteractionList(id: number) {
        return this.delete(`/drug/${id}`);
    }

    getDrugToDrugInteractionDetails(id: number) {
        return this.get<DrugToDrugDetails>(`/drug/${id}`);
    }

    addNewDrugToDrugInteractionData(requestBody: DrugToDrugDetails) {
        return this.post<DrugToDrugDetails>(`/drug`, requestBody);
    }
    updateDrugToDrugInteractionData(id: number, requestBody: DrugToDrugDetails) {
        return this.put<DrugToDrugDetails>(`/drug/${id}`, requestBody);
    }


    getDuplicationTherapyList(request: DuplicationRequest) {
        return this.get(`/duplicateTherapy?${request.toQueryParams()}`, {
            subjectToUpdate: this.duplicationList$
        });}

        
    
        deleteDuplicationList(id:string) {
            return this.delete(`/duplicateTherapy/${id}`);
        }


       getduplicationdetails(id:string) {
            return this.get<DuplicationDetails>(`/duplicateTherapy/${id}`);
        }


        addNewDuplicationTherapyData(requestBody: DuplicationDetails) {
            return this.post<DuplicationDetails>(`/duplicateTherapy`, requestBody);
        }
        updateDuplicationTherapyData(id: number, requestBody: DuplicationDetails) {
            return this.put<DuplicationDetails>(`/duplicateTherapy/${id}`, requestBody);
        }




        uploadDuplicatioExcelDatafile(file: File, isOverride: string = 'false') {
            const formData: FormData = new FormData();
            formData.append('file', file, file.name);
            return this.post<any>(`/duplicateTherapy?isOverride=${isOverride}`, formData);
        }


    uploadDrugToDrugExcelDatafile(file: File, isOverride: string = 'false') {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);
        return this.post<any>(`/drug?isOverride=${isOverride}`, formData);
    }
}