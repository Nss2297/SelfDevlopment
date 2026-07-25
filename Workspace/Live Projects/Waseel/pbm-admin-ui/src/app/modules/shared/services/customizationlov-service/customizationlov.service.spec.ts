import { TestBed } from '@angular/core/testing';

import { CustomizationlovService } from './customizationlov.service';

describe('CustomizationlovService', () => {
  let service: CustomizationlovService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomizationlovService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
