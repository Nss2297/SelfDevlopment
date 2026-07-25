import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, take, tap } from 'rxjs';
import { DrugsDetails } from 'src/app/modules/prescription/models/prescription-drug.model';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { ListViewModel } from '../../components/list-view/models/list-view.model';

@Injectable({
  providedIn: 'root'
})
export class PrescriptionlovService extends DefaultHttpClient {
// providersList$:BehaviorSubject<{providerId:string,providerName:string,code:string}[]>= new BehaviorSubject([] as {providerId:string,providerName:string,code:string}[]);
  providersList$: BehaviorSubject<{ providerId: string, providerName: string, code: string }[]> = new BehaviorSubject<{ providerId: string, providerName: string, code: string }[]>([]);
  physiciansList$: BehaviorSubject<{ registrationNumber: string, name: string, category: string,physicianSpeciality:string }[]> = new BehaviorSubject([] as { registrationNumber: string, name: string, category: string,physicianSpeciality:string  }[]);
  drugsList$: BehaviorSubject<ListViewModel<DrugListModel>> = new BehaviorSubject(new ListViewModel<DrugListModel>());
  getFrequencyTypes$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  getUnitTypes$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  memberList$: BehaviorSubject<{ memberName: string, age: string, gender: string, dob: string, idNumber: string }[]>
    = new BehaviorSubject([] as { memberName: string, age: string, gender: string, dob: string, idNumber: string }[]);
  memberInfo$: BehaviorSubject<{ memberName: string, age: string, gender: string, dob: string, idNumber: string }>
    = new BehaviorSubject({} as { memberName: string, age: string, gender: string, dob: string, idNumber: string });
  statusList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);


  saveDrugAdd$: BehaviorSubject<ListViewModel<DrugsDetails>> = new BehaviorSubject(new ListViewModel());

  editDrug$: BehaviorSubject<ListViewModel<DrugsDetails>> = new BehaviorSubject(new ListViewModel());

  private _allListAreInitialized: boolean = false;
  public myData: string | undefined;
  sksArray: any = [];
  constructor(protected override httpClient: HttpClient) {
    super(httpClient, environment.backend.prescriptionservice.lov);
  }


  private data: any;

  getData() {
    return this.data;
  }

  setData(newData: any) {
    this.data = newData;
  }


  initializeAllLists() {
    if (!this._allListAreInitialized) {
      this.getUnitTypes().subscribe();
      this.getFrequencyTypes().subscribe();
      this.getRequestStatusTypes().subscribe();
      this.getDrugs().subscribe();
      this._allListAreInitialized = true;
    }
  }
  getProviders(request?: { value: string }) {
    let params = new HttpParams();
    if (request) {
      if (request.value) {
        params = params.append('value', request.value);
        console.log(params)
      }
    }
    return this.get<{ providerId: string, providerName: string, code: string }[]>(
      '/providers', {
      options: {
        params: params
      },
      subjectToUpdate: this.providersList$
    });
  }

  getPhysicians(request?: { physician: string }) {

    let params = new HttpParams();
    if (request) {
      if (request.physician) {
        params = params.append('physician', request.physician);
      }
    }
    return this.get<{ registrationNumber: string, name: string, category: string,physicianSpeciality:string, }[]>(
      '/physicians', {
      options: {
        params: params
      },
      subjectToUpdate: this.physiciansList$
    });
  }

  getDrugs(request?: { drugValue: string, idNumber: string, payerId?:string, searchBy?:string; }) {
    let params = new HttpParams();
    if (request) {
      if (request.searchBy == "scientificName") {
        params = params.append('searchBy', request.searchBy);
        params = params.append('value', request.drugValue);
        
      }else {
        if(request.drugValue){
          params = params.append('value', request.drugValue);
        }
        if(request.searchBy){
          params = params.append('searchBy', request.searchBy);
        }
        if (request.idNumber) {
          params = params.append('idNumber', request.idNumber);
        }
        if (request.payerId) {
          params = params.append('payerId', request.payerId);
        }
      }
      
    }else{
      
    }
    return this.get(
      '/drugs', {
      options: {
        params: params
      },
      subjectToUpdate: this.drugsList$
    });
  }


  getUnitTypes() {
    return this.get<string[]>('/unit-types').pipe(
      map(result =>
        result.map(unitTypes =>
          ({ key: unitTypes, value: unitTypes })
        )),
      tap(mappedResult => this.getUnitTypes$.next(mappedResult))
    );
  }

  getFrequencyTypes() {
    return this.get<string[]>('/frequency-types').pipe(
      map(result =>
        result.map(frequencyTypes =>
          ({ key: frequencyTypes, value: frequencyTypes })
        )),
      tap(mappedResult => this.getFrequencyTypes$.next(mappedResult))
    );
  }
  getMembers(request?: { payerId: string, idNumber: string }) {
    let params = new HttpParams();
    if (request) {
      if (request.payerId) {
        params = params.append('payerId', request.payerId);
        // params = params.append('idNumber', request.idNumber);
      }
      if (request.idNumber)
        params = params.append('value', request.idNumber);
    }
    return this.get<{ memberName: string, age: string, gender: string, dob: string, idNumber: string }[]>(
      '/member-info', {
      options: {
        params: params
      },
      subjectToUpdate: this.memberList$
    });
  }

  getMemberInfo(idNumber: string){
    return this.get<any>(
      '/member-demographic-data/' + idNumber, {
      subjectToUpdate: this.memberInfo$
    });
  }
  getRequestStatusTypes() {
    return this.get<string[]>('/status-types').pipe(
      map(result =>
        result.map(status =>
          ({ key: status, value: status })
        )),
      tap(mappedResult => this.statusList$.next(mappedResult))

    );

  }


  addSaveDrug(requestBody: any) {
    this.saveDrugAdd$.pipe(take(1)).subscribe(data => {
      data.content.push(requestBody);
      this.saveDrugAdd$.next(data);

    });
  }

  editSaveDrug(requestBody: any) {
    this.editDrug$.pipe(take(1)).subscribe(data => {
      data.content.push(requestBody);
      this.editDrug$.next(data);

    });
  }

  

}

export class DrugListModel{
  unitPrice!: string; 
  sfdaCode!: string; 
  sfdaDescription!: string; 
  scientificName!: string; 
  scientificCode!: string; 
  drugFormulary!: boolean; 
  dosageForm!:string; 
  strengthUnit!:string; 
  strength!:string; 
  roaSuggested!:string;
  activeDrugListId!:number;
  get id() {
    return this.sfdaCode ?? '';
}
}