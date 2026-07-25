

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { BehaviorSubject, map, tap } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { PrescriptionDetails } from '../models/prescription-details.model';
import { PrescriptionRequest } from '../models/prescription-request.model';
import { CustomizationRequestModel } from '../models/customization-request.model';
import { PrescriptionCustomizationRequest } from '../models/prescription-customization-request.model';
import { environment } from 'src/environments/environment';

//  { HttpClient } from '@angular/common/http';
// iimportmport { Injectable } from '@angular/core';
// import { DefaultHttpClient } from 'src/app/util/default-http-client';
// import { environment } from 'src/environments/environment';
// import { PrescriptionRequest } from '../models/prescription-request.model';
// import { BehaviorSubject } from 'rxjs';
// import { PrescriptionDetails } from '../models/prescription-details.model';
// import { ListViewModel } from '../../shared/components/list-view/models/list-view.model';
// >>>>>>> 457123949e50c0d698bbec6db7f84641158c03bb


@Injectable({
  providedIn: 'root'
})
// export class PrescriptionProviderService extends DefaultHttpClient{
//   prescription$: BehaviorSubject<ListViewModel<PrescriptionDetails>> = new BehaviorSubject(new ListViewModel());
//   PrescriptionList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);

//   private hostUrl = environment.backend.hostname;
//   value:any


//   constructor(protected override httpClient: HttpClient) { 
//     super(httpClient,environment.backend.prescriptionservice.prescriptionProvider);
//   }


//   getPrescriptions() {
//     let params = new HttpParams();
//     // if (request) {
//     //   params = params.append('pageNumber', request.pageNumber || 0);
//     //   params = params.append('recordSize', request.recordSize || 5);
//       // if (request.memberId)
//       // params = params.append('memberId', request.memberId);
//   // }

//       return this.get< {memberId : string, memberName: string, status: String, policyNumber: string, dateAndTime:string, insurance:string, referenceNo: string }[]>('/prescriptions', {
//         // options: {
//         //   params: params
//         // }
//       }).pipe(]
//         map(result =>
//           result.map(prescriptions =>
//             ({ key:  prescriptions.memberId, value: `${prescriptions.referenceNo} | ${prescriptions.insurance}| ${prescriptions.dateAndTime}| ${prescriptions.memberId}| ${prescriptions.insurance}| ${prescriptions.memberName}| ${prescriptions.status}` })
//           )),
//         tap(mappedResult => this.PrescriptionList$.next(mappedResult))
//       );

// }

export class PrescriptionProviderService extends DefaultHttpClient {

  prescription$: BehaviorSubject<ListViewModel<PrescriptionDetails>> = new BehaviorSubject(new ListViewModel());
  customizationRequest$: BehaviorSubject<ListViewModel<CustomizationRequestModel>> = new BehaviorSubject(new ListViewModel());

  constructor(protected override httpClient: HttpClient) {
    super(httpClient, environment.backend.prescriptionservice.prescriptionProvider);
  }

  getPrescriptions(request: PrescriptionRequest) {
    return this.get(`?${request.toQueryParams()}`, {
      subjectToUpdate: this.prescription$,
      // useMock:true

    });
  }

  getPayerPrescriptions( payerId:string, request: PrescriptionRequest) {
    return this.get(`/payers/${payerId}?${request.toQueryParams()}`, {
      subjectToUpdate: this.prescription$,

    });
  }
  getPayerCustomizationRequest( payerId:string, request:PrescriptionCustomizationRequest ) {
    return this.get(`/payers/${payerId}?${request.toQueryParams()}`, {
      subjectToUpdate: this.customizationRequest$,
      useMock:true

    });
  }
  saveData(providerId:string, refereneNumber:string, data:any){
    console.log("FINAL SAVE DATA ",data);
    console.log("REFERNCE NO ", refereneNumber)
    console.log("REFERNCE ID ", providerId)

    return this.put('/'+refereneNumber+'/drug-status', data);
  }
}
