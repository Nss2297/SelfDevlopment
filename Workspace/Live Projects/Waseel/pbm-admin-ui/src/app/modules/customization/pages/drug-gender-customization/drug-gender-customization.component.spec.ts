import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugGenderCustomizationComponent } from './drug-gender-customization.component';

describe('DrugGenderCustomizationComponent', () => {
  let component: DrugGenderCustomizationComponent;
  let fixture: ComponentFixture<DrugGenderCustomizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugGenderCustomizationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugGenderCustomizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
