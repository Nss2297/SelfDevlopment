import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, EMPTY, Observable, combineLatest, map, tap } from 'rxjs';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LovService extends DefaultHttpClient {

    drugsList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    diagnosisList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([] as { key: string, value: string }[]);
    payersList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    categoriesOfApproval$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    categoriesOfRejection$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    drugsStatuses$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
    modulesList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);

    private _allListAreInitialized: boolean = false;

    constructor(protected override httpClient: HttpClient) {
        super(httpClient, environment.backend.admin.lov);

    }

    initializeAllLists(options?: { serviceCode?: string, icdCode?: string, payerId?: string, categoryOfApprovalName?: string }) {
        if (options != null) {
            this._allListAreInitialized = false;
        }
        if (!this._allListAreInitialized) {
            this._allListAreInitialized = true;
            return combineLatest([
                this.getDrugs(options?.serviceCode ? { serviceCode: options.serviceCode } : undefined),
                this.getDiagnosis(options?.icdCode ? { icdCode: options.icdCode } : undefined),
                this.getPayers(options?.payerId ? { payerId: options.payerId } : undefined),
                this.getCategoriesOfApproval(options?.categoryOfApprovalName ? { name: options.categoryOfApprovalName } : undefined),
                this.getCategoriesOfRejection(),
                this.getDrugsStatuses(),
                this.getModules()
            ]);
        }
        return EMPTY;
    }



    getDrugs(request?: { pageNumber?: number, recordSize?: number, serviceCode?: string, description?: string }) {
        let params = new HttpParams();
        if (request) {
            params = params.append('pageNumber', request.pageNumber ?? 0);
            params = params.append('recordSize', request.recordSize ?? 10);
            if (request.serviceCode)
                params = params.append('serviceCode', request.serviceCode);
            if (request.description)
                params = params.append('description', request.description);
        }
        return this.get<{ content: { serviceCode: string, description: string }[] }>('/drugs', {
            options: {
                params: params
            }
        }).pipe(
            map(result =>
                result.content.map(drug =>
                    ({ key: drug.serviceCode, value: `${drug.serviceCode} | ${drug.description}` })
                )),
            tap(mappedResult => this.drugsList$.next(mappedResult))
        );
    }

    getDrugsForCustomization(request?: { pageNumber?: number, recordSize?: number, value?: string }) {
        let params = new HttpParams();
        if (request) {
            params = params.append('pageNumber', request.pageNumber ?? 0);
            params = params.append('recordSize', request.recordSize ?? 10);
            if (request.value)
                params = params.append('value', request.value);
        }
        return this.httpClient.get<{ sfdaCode: string, sfdaDescription: string }[]>('/api/prescriptions/lov/drugs', { params: params }).pipe(
            map(result =>
                result.map(drug =>
                    ({ key: drug.sfdaCode, value: `${drug.sfdaCode} | ${drug.sfdaDescription}` })
                )),
            tap(mappedResult => {
                console.log(mappedResult.length)
                this.drugsList$.next(mappedResult)
            })
        );
    }

    getDiagnosis(request?: { pageNumber?: number, recordSize?: number, icdCode?: string, description?: string }) {
        let params = new HttpParams();
        if (request) {
            params = params.append('pageNumber', request.pageNumber ?? 0);
            params = params.append('recordSize', request.recordSize ?? 10);
            if (request.icdCode)
                params = params.append('icdCode', request.icdCode);
            if (request.description)
                params = params.append('description', request.description);
        }
        return this.get<{ content: { icdDiagnosisCode: string, description: string }[] }>('/diagnosis', {
            options: {
                params: params
            }
        }).pipe(
            map(result =>
                result.content.map(diagnosis =>
                    ({ key: diagnosis.icdDiagnosisCode, value: `${diagnosis.icdDiagnosisCode} | ${diagnosis.description}` })
                )),
            tap(mappedResult => this.diagnosisList$.next(mappedResult))
        );
    }


    getPayers(request?: { pageNumber?: number, recordSize?: number, payerId?: string }) {
        let params = new HttpParams();
        if (request) {
            params = params.append('pageNumber', request.pageNumber || 0);
            params = params.append('recordSize', request.recordSize || 10);
            if (request.payerId)
                params = params.append('payerId', request.payerId);
        } else {

            params = params.append('pageNumber', '0');
            params = params.append('recordSize', '20');
        }

        return this.get<{ content: { payerId: string, pbmPayerType: string }[] }>('/payers', {
            options: {
                params: params
            }
        }).pipe(
            map(result =>
                result.content.map(payer =>
                    ({ key: payer.payerId, value: `${payer.payerId} | ${payer.pbmPayerType}` })
                )),
            tap(mappedResult => this.payersList$.next(mappedResult))
        );
    }

    getCategoriesOfApproval(request?: { pageNumber?: number, recordSize?: number, name?: string }) {
        let params = new HttpParams();
        if (request) {
            params = params.append('pageNumber', request.pageNumber || 0);
            params = params.append('recordSize', request.recordSize || 10);
            if (request.name)
                params = params.append('name', request.name);
        }

        return this.get<{ content: { id: number, name: string }[] }>('/approval-categories', {
            options: {
                params: params
            }
        }).pipe(
            map(result =>
                result.content.map(category =>
                    ({ key: category.name, value: category.name })
                )),
            tap(mappedResult => this.categoriesOfApproval$.next(mappedResult))
        );
    }

    getCategoriesOfRejection() {
        return this.get<string[]>('/rejection-categories').pipe(
            map(result =>
                result.map(category =>
                    ({ key: category, value: category })
                )),
            tap(mappedResult => this.categoriesOfRejection$.next(mappedResult))
        );
    }

    getDrugsStatuses() {
        return this.get<string[]>('/drugs/statuses').pipe(
            map(result =>
                result.map(status =>
                    ({ key: status, value: status })
                )),
            tap(mappedResult => this.drugsStatuses$.next(mappedResult))
        );
    }

    getModules() {
        return this.get<string[]>('/modules').pipe(
            map(result =>
                result.map(module =>
                    ({ key: module, value: module })
                )),
            tap(mappedResult => this.modulesList$.next(mappedResult))
        );
    }
}
