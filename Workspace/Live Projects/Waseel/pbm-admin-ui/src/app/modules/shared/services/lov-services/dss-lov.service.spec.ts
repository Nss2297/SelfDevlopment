import { TestBed } from '@angular/core/testing';

import { DssLovService } from './dss-lov.service';

describe('DssLovService', () => {
  let service: DssLovService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DssLovService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
