import { TestBed } from '@angular/core/testing';

import { AddPrescriptionStoreService } from './add-prescription-store.service';

describe('AddPrescriptionStoreService', () => {
  let service: AddPrescriptionStoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddPrescriptionStoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
