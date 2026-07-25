import { HttpClient, HttpContext, HttpErrorResponse, HttpHeaders, HttpParams } from "@angular/common/http";
import { catchError, iif, map, mergeMap, of, Subject, throwError, } from "rxjs";
import { environment } from "src/environments/environment";

export class DefaultHttpClient {

    baseUrl: string;
    servicePath: string;

    constructor(protected httpClient: HttpClient, servicePath: string) {
        this.servicePath = servicePath;
        this.baseUrl = environment.backend.hostname + servicePath;
    }

    delete<T>(url: string | undefined) {
        return this.httpClient.delete<T>(this.baseUrl + url);
    }

    post<T>(url: string, body: any, requestSettings?: RequestSettings<T>) {
        return this.httpClient.post<T>((requestSettings?.useMock ? '/mocks' + this.servicePath : this.baseUrl) + url, body, requestSettings?.options)
            .pipe(
                map(body => this._handleResponse(body, requestSettings)),
                catchError(error => {
                    if (error instanceof HttpErrorResponse) {
                        if (error.status == 400) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'BAD_REQUEST', error: error });
                        } else if (error.status == 401) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'INVALID_CREDENTIALS', error: error });
                        } else if (error.status == 403) {
                            return of({ messageCode: requestSettings?.onForbiddenErrorCode || 'INSUFFICIENT_PRIVILEGES', error: error });
                        } else if ((error.status / 100).toFixed() == '4') {
                            return of({ messageCode: `CLIENT_ERROR_${error.status}`, error: error });
                        }
                    }
                    return of({ messageCode: 'UNKNOWN_ERROR', error: error });
                })
            ).pipe(
                mergeMap(value =>
                    iif(() =>
                        (typeof (value) == 'object' && Object.keys(value as object).includes('messageCode')),
                        throwError(() => new HttpException(value as {
                            messageCode: string;
                            error: any;
                        })), of(value as T)
                    )
                )
            );
    }

    put<T>(url: string, body: any, requestSettings?: RequestSettings<T>) {
        return this.httpClient.put<T>((requestSettings?.useMock ? '/mocks' + this.servicePath : this.baseUrl) + url, body, requestSettings?.options)
            .pipe(
                map(body => this._handleResponse(body, requestSettings)),
                catchError(error => {
                    if (error instanceof HttpErrorResponse) {
                        if (error.status == 400) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'BAD_REQUEST', error: error });
                        } else if (error.status == 401) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'INVALID_CREDENTIALS', error: error });
                        } else if (error.status == 403) {
                            return of({ messageCode: requestSettings?.onForbiddenErrorCode || 'INSUFFICIENT_PRIVILEGES', error: error });
                        } else if ((error.status / 100).toFixed() == '4') {
                            return of({ messageCode: `CLIENT_ERROR_${error.status}`, error: error });
                        }
                    }
                    return of({ messageCode: 'UNKNOWN_ERROR', error: error });
                })
            ).pipe(
                mergeMap(value =>
                    iif(() =>
                        (typeof (value) == 'object' && Object.keys(value as object).includes('messageCode')),
                        throwError(() => new HttpException(value as {
                            messageCode: string;
                            error: any;
                        })), of(value as T)
                    )
                )
            );
    }

    get<T>(url: string, requestSettings?: RequestSettings<T>) {
        return this.httpClient.get<T>((requestSettings?.useMock ? '/mocks' + this.servicePath : this.baseUrl) + url, requestSettings?.options)
            .pipe(
                map(body => this._handleResponse(body, requestSettings)),
                catchError(error => {
                    if (error instanceof HttpErrorResponse) {
                        if (error.status == 400) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'BAD_REQUEST', error: error });
                        } else if (error.status == 401) {
                            return of({ messageCode: requestSettings?.onBadRequestErrorCode || 'INVALID_CREDENTIALS', error: error });
                        } else if (error.status == 403) {
                            return of({ messageCode: requestSettings?.onForbiddenErrorCode || 'INSUFFICIENT_PRIVILEGES', error: error });
                        } else if ((error.status / 100).toFixed() == '4') {
                            return of({ messageCode: `CLIENT_ERROR_${error.status}`, error: error });
                        }
                    }
                    return of({ messageCode: 'UNKNOWN_ERROR', error: error });
                })
            ).pipe(
                mergeMap(value =>
                    iif(() =>
                        (typeof (value) == 'object' && Object.keys(value as object).includes('messageCode')),
                        throwError(() => new HttpException(value as {
                            messageCode: string;
                            error: any;
                        })), of(value as T)
                    )
                )
            );
    }

    _handleResponse<T>(body: T, requestSettings?: RequestSettings<T>): T {
        if (body) {
            if (requestSettings?.subjectToUpdate) {
                requestSettings.subjectToUpdate.next(body);
            }
            return body;
        }
        return ({}) as T;
    }

}

export type RequestSettings<T> = {
    subjectToUpdate?: Subject<T>,
    useMock?: boolean,
    onBadRequestErrorCode?: string,
    onForbiddenErrorCode?: string,
    onUnauthorizedErrorCode?: string,
    options?: {
        headers?: HttpHeaders | {
            [header: string]: string | string[];
        };
        context?: HttpContext;
        observe?: 'body';
        params?: HttpParams | {
            [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>;
        };
        reportProgress?: boolean;
        responseType?: 'json';
        withCredentials?: boolean;
    }
}

export class HttpException {
    messageCode: string;
    response: HttpErrorResponse;

    constructor(value: { messageCode: string; error: any; }) {
        this.messageCode = value.messageCode;
        this.response = value.error
    }
}