import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, EMPTY, combineLatest, map, tap } from 'rxjs';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DssLovService extends DefaultHttpClient {
    private _allListAreInitialized: boolean = false;

    packageTypes$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
    strengthUnit$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
    dosageForm$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
    genderList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    ageList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    ageDetailsList$: BehaviorSubject<{ key: string, fromValue?: number, toValue?: number }[]> = new BehaviorSubject([] as { key: string, fromValue?: number, toValue?: number }[]);

    constructor(
        protected override httpClient: HttpClient
    ) {
        super(httpClient, environment.backend.prescriptionservice.dssLov);




    }
    initializeAllLists(options?: { serviceCode?: string, icdCode?: string, payerId?: string, categoryOfApprovalName?: string }) {
        if (options != null) {
            this._allListAreInitialized = false;
        }
        if (!this._allListAreInitialized) {
            this._allListAreInitialized = true;
            return combineLatest([
                this.getgenderlist(),
                this.getAgelist(),
                this.getAgeDetailsList()
            ]);
        }
        return EMPTY;
    }

    getgenderlist() {

        return this.get<string[]>('/gender').pipe(
            map(result =>
                result.map(gender =>
                    ({ key: gender, value: gender })
                )),
            tap(mappedResult => this.genderList$.next(mappedResult))
        );
    }
    getAgelist(request?: { key?: string, fromValue?: number, toValue?: number }) {
        let params = new HttpParams();
        if (request) {

            if (request.key)
                params = params.append('key', request.key);
        } else {

        }

        return this.get<{ lov: { key: string, fromValue?: number, toValue?: number }[] }>('/age', {
            options: {
                params: params
            }
        }).pipe(
            map(result =>
                result.lov.map(key =>
                    ({ key: key.key, value: key.key })
                )),
            tap(mappedResult => this.ageList$.next(mappedResult))
        );
    }

    getAgeDetailsList() {
        return this.get<{ lov: { key: string, fromValue?: number, toValue?: number }[] }>('/age').pipe(
            map(result => this.ageDetailsList$.next(result.lov))
        );
    }

    getListOfValuesByListKey(key: string) {
        let result: any;
        switch (key) {
            case "packageTypes":

                result = this.httpClient.get<any[]>('/api/dss-lov/' + key).pipe(
                    map((result: any) =>
                        result.lovs.map((packageType: any) =>
                            ({ key: packageType.labelEn, value: packageType.value })
                        )),
                    tap(mappedResult => {
                        console.log(mappedResult.length)
                        this.packageTypes$.next(mappedResult)
                    })
                );
                break;
            case "strengthUnit":
                result = this.httpClient.get<any[]>('/api/dss-lov/' + key).pipe(
                    map((result: any) =>
                        result.lovs.map((packageType: any) =>
                            ({ key: packageType.labelEn, value: packageType.value })
                        )),
                    tap(mappedResult => {
                        console.log(mappedResult.length)
                        this.strengthUnit$.next(mappedResult)
                    })
                );
                break;
            case "dosageForm":
                result = this.httpClient.get<any[]>('/api/dss-lov/' + key).pipe(
                    map((result: any) =>
                        result.lovs.map((packageType: any) =>
                            ({ key: packageType.labelEn, value: packageType.value })
                        )),
                    tap(mappedResult => {
                        console.log(mappedResult.length)
                        this.dosageForm$.next(mappedResult)
                    })
                );
                break;
            default:
                break;
        }
        return result;
    }
}
