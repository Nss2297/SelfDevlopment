import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomizationRequestsComponent } from './customization-requests.component';

describe('CustomizationRequestsComponent', () => {
  let component: CustomizationRequestsComponent;
  let fixture: ComponentFixture<CustomizationRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomizationRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomizationRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
