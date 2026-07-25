import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPayerDetailsComponent } from './view-payer-details.component';

describe('ViewPayerDetailsComponent', () => {
  let component: ViewPayerDetailsComponent;
  let fixture: ComponentFixture<ViewPayerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewPayerDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPayerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
