import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularyListComponent } from './formulary-list.component';

describe('FormularyListComponent', () => {
  let component: FormularyListComponent;
  let fixture: ComponentFixture<FormularyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularyListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
