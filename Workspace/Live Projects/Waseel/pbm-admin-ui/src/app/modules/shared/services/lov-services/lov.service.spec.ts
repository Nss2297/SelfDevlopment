import { TestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LovService } from './lov.service';

describe('DataService', () => {
  let service: LovService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LovService]
    });
    service = TestBed.inject(LovService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should send a GET request with query parameters', () => {
    const request = {
      pageNumber: 1,
      recordSize: 10,
      serviceCode: 'ABC',
      description: 'Test'
    };
    const expectedUrl = '/drugs?pageNumber=1&recordSize=10&serviceCode=ABC&description=Test';
    const expectedResponse = {
      content: [
        {
          serviceCode: 'ABC',
          description: 'Test drug'
        },
        {
          serviceCode: 'DEF',
          description: 'Another drug'
        }
      ]
    };

    service.getDrugs(request).subscribe(result => {
      expect(result).toEqual([
        { key: 'ABC', value: 'ABC | Test drug' },
        { key: 'DEF', value: 'DEF | Another drug' }
      ]);
    });

    const req = httpTestingController.expectOne(expectedUrl);
    expect(req.request.method).toEqual('GET');
    req.flush(expectedResponse);
  });
});
