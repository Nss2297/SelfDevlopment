import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { PrescriptionCustomizationRequest } from '../models/prescription-customization-request.model';
// import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { customizationResponsemodel } from '../models/customiztion-request-model';
import { BehaviorSubject, map, tap } from 'rxjs';
import { ListViewModel } from '../../shared/components/list-view/models/list-view.model';
import { CustomizationRequestModel } from '../models/customization-request.model';

@Injectable({
  providedIn: 'root'
})
export class CustomizationService extends DefaultHttpClient {

  customizationRequest$: BehaviorSubject<ListViewModel<CustomizationRequestModel>> = new BehaviorSubject(new ListViewModel());

  CustomizationsDatalist$: BehaviorSubject<ListViewModel<CustomizationRequestModel>> = new BehaviorSubject(new ListViewModel())
  constructor(protected override httpClient: HttpClient, private datePipe: DatePipe) {
    super(httpClient, environment.backend.prescriptionservice.customizationService);
  }
  addNewCustmizationData(data: any) {
    console.log(data)
    // return this.httpClient.post('http://localhost:4200/api/customizations/requests/',data)
    // return this.post('/customizations/requests',data)
    // return this.post(`/${ePrescriptionReferenceNumber}/customizations/requests`, data)
    return this.post('/customizations/requests', data)
  }

  getPayerCustomizationRequest(request: PrescriptionCustomizationRequest) {
    return this.get<ListViewModel<CustomizationRequestModel>>(`/customizations/requests?${request.toQueryParams()}`, {
      subjectToUpdate: this.customizationRequest$
    });
  }

  deletePayerCustomizationRequest(customizationRequestId?: string) {
    // /{customizationRequestId}
    return this.delete(`/customizations/requests/${customizationRequestId}`);
  }

  updateCustomizationRequest(customizationRequestId: string, request: { status: 'Accepted' | 'Rejected', rejectionReason?: string }) {
    return this.put(`/customizations/requests/${customizationRequestId}`, request);
  }

  // cancelPrescriptionData(ePrescriptionReferenceNumber?: string) {
  //   return this.delete(`/${ePrescriptionReferenceNumber}`);
  // }

  // addNewCustmizationData(requestBody: { 
  //   ePrescriptionReferenceNo?:string;
  //    drugCode?:string,
  //   drugName?:string,
  //   icdCode?:string,
  //   icdDescription?:string,

  //   unitType?:string,
  //   moduleName?:string,
  //   fromAgeInDays?:string,
  //   toAgeInDays?:string,
  //   maxValuePerDay?:string,
  //  productPackageSize?:string,
  //   rejectionReason?:string,}) {
  //   return this.post<customizationResponsemodel>('/customizations/requests', requestBody);
  // }
  // addNewCustmizationData(requestBody: {
  //   ePrescriptionReferenceNo?:string;
  //   moduleName?:string,
  //   fromAgeInDays?:string,
  //   toAgeInDays?:string,
  //   maxValuePerDay?:string,
  //  productPackageSize?:string,
  //   rejectionReason?:string,
  //   diagnosisCodes?: any[], drugList?: any[], 
  // }) {
  //   requestBody.diagnosisCodes = requestBody.diagnosisCodes?.map(code => ({ diagnosisCode: code.icdCode, icdDescription: code.icdDescription }));
  //   requestBody.drugList = requestBody.drugList?.map(code => ({
  //     drugCode: code.drugCode,

  //     unitType: code.unitType
  //   }));
  //   return this.post<customizationResponsemodel>('/customizations/requests', requestBody);
  // }


}
