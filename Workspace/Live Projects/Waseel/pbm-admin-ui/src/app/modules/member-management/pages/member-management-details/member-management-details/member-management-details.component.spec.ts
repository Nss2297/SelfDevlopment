import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberManagementDetailsComponent } from './member-management-details.component';

describe('MemberManagementDetailsComponent', () => {
    let component: MemberManagementDetailsComponent;
    let fixture: ComponentFixture<MemberManagementDetailsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [MemberManagementDetailsComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(MemberManagementDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
