import { TestBed } from '@angular/core/testing';

import { PrescriptionProviderService } from './prescription-provider.service';

describe('PrescriptionProviderService', () => {
  let service: PrescriptionProviderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrescriptionProviderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
