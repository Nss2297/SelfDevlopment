import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DuplicationTherapyCustomizationPageComponent } from './duplication-therapy-customization-page.component';

describe('DuplicationTherapyCustomizationPageComponent', () => {
  let component: DuplicationTherapyCustomizationPageComponent;
  let fixture: ComponentFixture<DuplicationTherapyCustomizationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DuplicationTherapyCustomizationPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DuplicationTherapyCustomizationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
