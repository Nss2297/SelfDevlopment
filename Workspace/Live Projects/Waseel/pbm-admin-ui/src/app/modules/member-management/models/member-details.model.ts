import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";


export class MemberDetails implements ListItem {

    @Header('prescription.memberName', 1)
    name?: string;
    @Header('prescription.nationalId', 2)
    idNumber?: string
    @Header('prescription.gender', 3)
    gender?: string;
    @Header('DOB', 4)
    dateOfBirth?: string;
    @Header('prescription.nationality', 5)
    nationality?: string;

    //memberId?: string;
    get id() {
        return this.idNumber ?? '';
    }

}

export class MemberPolicyDetails implements ListItem {
    @Header('POLICY-NUM', 1)
    policyNumber?: string;
    @Header('POLICY-HOLDER-NAME', 2)
    policyHolderName?: string
    @Header('MEMBER-ID', 3)
    memberId?: string;
    @Header('CLASS-CODE', 4)
    classCode?: string;
    @Header('CLASS-NAME', 5)
    className?: string;

    pageNumber?: number;
    recordSize?: number;

    get id() {
        return this.memberId ?? '';
    }
}
export class MemberInfoDetailsModel {
    memberName?: string;
    idNumber?: number;
    gender?: string;
    dateOfBirth?: string;
    maritalStatus?: string;
    nationality?: string;
    mobileNumber?: string;
    email?: string;
    policyType?: string;
    issueDate?: string;
    startDate?: string;
    endDate?: string;
    memberChronicDiseaseResponseModel?: ChronicDiseases[];
}

export class ChronicDiseases {
    chronicDiseaseId?: string;
    chronicDiseaseName?: string;
    payerId?: string;
}









