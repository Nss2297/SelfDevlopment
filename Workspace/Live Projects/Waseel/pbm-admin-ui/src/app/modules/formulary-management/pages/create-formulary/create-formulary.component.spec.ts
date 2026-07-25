import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFormularyComponent } from './create-formulary.component';

describe('CreateFormularyComponent', () => {
  let component: CreateFormularyComponent;
  let fixture: ComponentFixture<CreateFormularyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateFormularyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateFormularyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
