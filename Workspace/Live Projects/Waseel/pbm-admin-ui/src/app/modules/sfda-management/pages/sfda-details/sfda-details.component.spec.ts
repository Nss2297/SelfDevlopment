import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SfdaDetailsComponent } from './sfda-details.component';

describe('SfdaDetailsComponent', () => {
  let component: SfdaDetailsComponent;
  let fixture: ComponentFixture<SfdaDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SfdaDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SfdaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
