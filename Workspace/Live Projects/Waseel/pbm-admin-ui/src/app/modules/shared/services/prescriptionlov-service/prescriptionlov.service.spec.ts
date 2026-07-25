import { TestBed } from '@angular/core/testing';

import { PrescriptionlovService } from './prescriptionlov.service';

describe('PrescriptionlovService', () => {
  let service: PrescriptionlovService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrescriptionlovService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
