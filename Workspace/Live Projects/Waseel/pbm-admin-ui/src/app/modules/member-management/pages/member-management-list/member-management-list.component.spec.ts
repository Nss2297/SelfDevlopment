import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberManagementListComponent } from './member-management-list.component';

describe('MemberManagementListComponent', () => {
  let component: MemberManagementListComponent;
  let fixture: ComponentFixture<MemberManagementListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberManagementListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemberManagementListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
