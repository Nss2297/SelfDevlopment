import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from "src/environments/environment";

import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { ListViewModel } from '../../shared/components/list-view/models/list-view.model';
import { drugExclusionListDetailsModel } from '../drug-exclusion-models/drugExclusionListDetails.model';
import { drugExclusionRequest } from '../drug-exclusion-models/drugExclusion-request.model';
import { DrugExclusionDetailsFilter } from '../drug-exclusion-models/drugExclusionDetails-filter.model';
import { BasedOnAllExclusion, ExclusionDrugDetailsModel, exclusionDetailsModel } from '../drug-exclusion-models/exclusion-details.model';
@Injectable({
  providedIn: 'root'
})
export class ExclusionServiceTsService extends DefaultHttpClient {
  drugExclusionList$:BehaviorSubject<ListViewModel< drugExclusionListDetailsModel>> = new BehaviorSubject(new ListViewModel());
  networkExclusionList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  providerExclusionList$:BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  specialityExclusionList$:BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  drugDetailsList$: BehaviorSubject<ListViewModel<ExclusionDrugDetailsModel>> = new BehaviorSubject(new ListViewModel());
  basedOnAllExclusion$:BehaviorSubject<ListViewModel<exclusionDetailsModel>> = new BehaviorSubject(new ListViewModel());


  constructor(protected override httpClient: HttpClient) {
    super(httpClient, environment.backend.prescriptionservice.drugExclusionService);
  }


  getDrugExclusions(request:drugExclusionRequest) {
    return this.get(`?${request.toQueryParams()}`, {
      subjectToUpdate: this.drugExclusionList$,
      
    });
    }
    deletedrugExclusion(exclusionId?: string) {
      return this.delete(`/${exclusionId}`);
    }

  getNetworkExclusion(request?: { pageNumber?: number, recordSize?: number, value:string}) {
    let params = new HttpParams();
    if (request) {
      if(request.value)
      params = params.append('pageNumber', request.pageNumber! | 0);
      params = params.append('recordSize', request.recordSize! | 10);
      params = params.append('value', request.value);
      
    }
    return this.httpClient.get<{ content: { networkId: string, networkName: string }[] }>('/api/lov/drug-exclusions/networks',{params:params}).pipe(
      map(result =>
        result.content.map(networkExclusion =>
          ({ key: networkExclusion.networkId, value: `${networkExclusion.networkName}` })
        )),
      tap(mappedResult => this.networkExclusionList$.next(mappedResult))
    );
  }

  getProviderExclusion(request?: { value:string}) {
    let params = new HttpParams();
    if (request) {
     if (request.value)
        params = params.append('value', request.value);
    }
    return this.httpClient.get<{ content: { providerId: string, providerName: string }[] }>('/api/lov/providers?',{params:params}).pipe(
      map(result =>
        result.content.map( providerExclusion =>
          ({ key: providerExclusion.providerId, value: `${ providerExclusion.providerName}` })
        )),
      tap(mappedResult => this.providerExclusionList$.next(mappedResult))
    );
  }

  getSpecialityExclusion(request?: { value:string}) {
    let params = new HttpParams();
    if (request) {
     if (request.value)
        params = params.append('value', request.value);
    }
    return this.httpClient.get<{ content: { specialityId: string, specialityName: string }[] }>('/api/lov/specialities?',{params:params}).pipe(
      map(result =>
        result.content.map( specialityExclusion =>
          ({ key: specialityExclusion.specialityId, value: `${ specialityExclusion.specialityName}` })
        )),
      tap(mappedResult => this. specialityExclusionList$.next(mappedResult))
    );
  }


  getExclusionDrugList(exclusionId:string,request:DrugExclusionDetailsFilter){
    return this.get(`/${exclusionId}/drugs?${request.toQueryParams()}`,{
      subjectToUpdate: this.drugDetailsList$
    });
  }
  addNetworkExclusion(exclusionId:string,data:any){
    let payload = {networkId:data.networkId};
    return this.put(`/${exclusionId}/networks`,payload);
  }

  addProviderExclusion(exclusionId:string,data:any){
    let payload = {providerId:data.providerId,providerName:data.providerName};
    return this.put(`/${exclusionId}/providers`,payload);
  }

  addSpecialityExclusion(exclusionId:string,data:any){
    let payload = {specialityId:data.specialityId};
    return this.put(`/${exclusionId}/specialities`,payload);
  }
  
  addDrug(exclusionId:string, data:any) {
    const endpoint = `/${exclusionId}/drugs`; 
    return this.post(endpoint, data);
  }
  createDrugExclusion(drugExclusionPayload: any){
    return this.post('', drugExclusionPayload);
  }

  addHighCostMedicine(exclusionId:string){
    return this.put(`/${exclusionId}/high-cost`,{});
  }

  deleteDrug(drugExclusionDetailsId?: string) {
    return this.delete(`/drugs/${drugExclusionDetailsId}`);
  }

  updateDrugExclusionListName(exclusionId:string, exclusionName:string){
    return this.put(`/${exclusionId}/name/${exclusionName}`,{});
  }

  bindDrugExclusionListName(exclusionId:string){
    return this.get(`/${exclusionId}/name`);
  }

  getAllExclusions(exclusionId:string, request: BasedOnAllExclusion){
    return this.get(`/${exclusionId}?${request.toQueryParams()}`,{
      subjectToUpdate: this.basedOnAllExclusion$
    })
    
  }

  deleteBasedOnDetailData(payload:{exclusionType: string, exclusionAsscId: string}){
     return this.delete(`/${payload.exclusionType}/${payload.exclusionAsscId}`)
  }

  deleteExclusion(exclusionId:string) {
 return this.delete(`/${exclusionId}`);
  }


// exclusion upload api 
  uploadFile(file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

  
    return this.post<any>(``,formData);
  }


}