import { TestBed } from '@angular/core/testing';

import { MainRequestsInterceptor } from './main-requests.interceptor';

describe('MainRequestsInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      MainRequestsInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: MainRequestsInterceptor = TestBed.inject(MainRequestsInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
