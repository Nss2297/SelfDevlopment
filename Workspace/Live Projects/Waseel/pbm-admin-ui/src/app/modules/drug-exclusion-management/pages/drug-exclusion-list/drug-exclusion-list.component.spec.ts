import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugExclusionListComponent } from './drug-exclusion-list.component';

describe('DrugExclusionListComponent', () => {
  let component: DrugExclusionListComponent;
  let fixture: ComponentFixture<DrugExclusionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugExclusionListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugExclusionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
