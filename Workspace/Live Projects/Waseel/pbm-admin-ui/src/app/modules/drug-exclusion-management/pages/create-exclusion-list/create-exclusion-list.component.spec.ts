import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateExclusionListComponent } from './create-exclusion-list.component';

describe('CreateExclusionListComponent', () => {
  let component: CreateExclusionListComponent;
  let fixture: ComponentFixture<CreateExclusionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateExclusionListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateExclusionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
