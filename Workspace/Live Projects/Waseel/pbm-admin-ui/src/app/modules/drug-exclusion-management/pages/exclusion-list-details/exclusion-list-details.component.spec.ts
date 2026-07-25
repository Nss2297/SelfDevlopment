import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExclusionListDetailsComponent } from './exclusion-list-details.component';

describe('ExclusionListDetailsComponent', () => {
  let component: ExclusionListDetailsComponent;
  let fixture: ComponentFixture<ExclusionListDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExclusionListDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExclusionListDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
