import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { map, BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomizationlovService extends DefaultHttpClient{

  moduleList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
  private _allListAreInitialized: boolean = false;
  constructor(protected override httpClient: HttpClient) { 
    super(httpClient, environment.backend.prescriptionservice.customizationLov);
  }

  initializeAllLists() {
    if (!this._allListAreInitialized) {
      this.getModuleNameTypes().subscribe();
      this._allListAreInitialized = true;
    }
  }

  getModuleNameTypes() {
    return this.get<string[]>('/customization-modules').pipe(
      map(result =>
        result.map(moduleName =>
          ({ key: moduleName, value: moduleName })
        )),
      tap(mappedResult => this.moduleList$.next(mappedResult))

    );

  }

}
