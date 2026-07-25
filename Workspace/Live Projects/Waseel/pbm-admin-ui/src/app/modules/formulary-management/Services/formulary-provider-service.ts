import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DefaultHttpClient } from "src/app/util/default-http-client";
import { environment } from "src/environments/environment";
import { FormularyDetailsModel, FormularyDrugDetailsModel, FormularyPolicyDetailsModel } from "../models/formulary-details.model";
import { BehaviorSubject } from "rxjs";
import { ListViewModel } from "../../shared/components/list-view/models/list-view.model";
import { formularyRequest } from "../models/formulary-request-model";
import { FormularyListDetailsModel } from "../models/formularylist-details-model";
import { FormularyDetailsFilter } from "../models/formulary-details-filter.model";

@Injectable({
    providedIn: 'root'
})

export class formularyProviderService extends DefaultHttpClient {
    formularyList$: BehaviorSubject<ListViewModel<FormularyListDetailsModel>> = new BehaviorSubject(new ListViewModel());
    metaDataDetails$: BehaviorSubject<FormularyDetailsModel> = new BehaviorSubject(new FormularyDetailsModel());
    drugList$: BehaviorSubject<ListViewModel<FormularyDrugDetailsModel>> = new BehaviorSubject(new ListViewModel());
    policyList$: BehaviorSubject<ListViewModel<FormularyPolicyDetailsModel>> = new BehaviorSubject(new ListViewModel());
    drugsList$: BehaviorSubject<{ unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, drugFormulary: boolean, dosageForm: string, strengthUnit: string }[]> = new BehaviorSubject([] as { unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, drugFormulary: boolean, dosageForm: string, strengthUnit: string }[]);
    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.prescriptionservice.drugFormularyService);
    }

    getFormularyList(request: formularyRequest) {
        return this.get(`?${request.toQueryParams()}`, {
            subjectToUpdate: this.formularyList$,

        });
    }

    deleteFormulary(formularyId?: string) {
        return this.delete(`/${formularyId}`);
    }

    getFormularyMetadataDetails(formularyId: string) {
        return this.get(`/${formularyId}`, {
            subjectToUpdate: this.metaDataDetails$
        });

    }

    getFormularyPolicyList(formularyId: string, request: FormularyDetailsFilter) {
        return this.get(`/${formularyId}/policy-details?${request.toQueryParams()}`, {
            subjectToUpdate: this.policyList$
        });
    }


    getFormularyDrugList(formularyId: string, request: FormularyDetailsFilter) {
        return this.get(`/${formularyId}/drug-details?${request.toQueryParams()}`, {
            subjectToUpdate: this.drugList$
        });
    }

    createFormularyDrugList(formularyId: string,) {
        return this.get(`/${formularyId}`, {
            subjectToUpdate: this.drugList$
        });
    }

    updateFormularyName(formularyId: string, formularyName: string) {
        return this.put(`/${formularyId}`, { "formularyId": formularyId, "formularyName": formularyName });
    }

    getPolicyDetailsByPolicyNo(policyNo: string) {
        return this.httpClient.get("/api/lov/policy-details/" + policyNo);
    }

    updatePolicyDetail(formularyId: string, data: any) {
        const endpoint = `/${formularyId}/policy-details`;
        return this.post(endpoint, data);
    }

    getPolicyDetailsByMemberId(idNumber: string) {
        return this.httpClient.get("/api/lov/members/" + idNumber);
    }

    verifyPolicyDetails(formularyId: string) {
        return this.get(`/${formularyId}/verify-policy`);
    }

    deleteDrug(formularyId?: string) {
        return this.delete(`/drugs/${formularyId}`);
    }

    addDrug(formularyId: string, data: any) {
        const endpoint = `/${formularyId}/drugs`;
        return this.post(endpoint, data);
    }

    createFormulary(formularyPayload: any) {
        return this.post('', formularyPayload);
    }
    // addDrug(data: any) {
    //   console.log(data)

    //   return this.post('/formularyId/drugs', data)
    // }
}