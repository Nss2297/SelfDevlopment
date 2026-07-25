import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { BrandDrug, DispensableDrug, DispenseDetail } from '../../models/dispense-detail.model';
import { PrescriptionService } from '../../service/prescription.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
    selector: 'app-drug-dispense',
    templateUrl: './drug-dispense.component.html',
    styles: []
})

export class DrugDispenseComponent implements OnInit, OnDestroy {

    dispenseSummaryDialogOpen = false;
    ePrescriptionReferenceNumber!: string;
    dispenseDetails!: DispenseDetail;
    subscriptions: Subscription[] = [];
    selectedDrugs: DispensableDrug[] = [];
    selectedDrugsForDispense: DispensableDrug[] = [];
    totals: { grandTotal: number, noOfDrugs: number, quantity: number, patientShare: number, net: number, patientShareVatAmount: number, patientShareVatCurrency: string } =
        { grandTotal: 0, noOfDrugs: 0, quantity: 0, patientShare: 0, net: 0, patientShareVatAmount: 0, patientShareVatCurrency: 'SAR' };


    constructor(
        private router: Router,
        private activeRouter: ActivatedRoute,
        private prescriptionService: PrescriptionService,
        private dialogService: DialogService,
        private translate: TranslateService,
        private authService: AuthService
    ) { }

    ngOnInit() {
        this.authService.showSystemLoader();
        const routerSubscription = this.activeRouter.params.subscribe(params => {
            this.ePrescriptionReferenceNumber = params['ePrescriptionReferenceNumber'];
            this.fetchData();
        });
        const detailsSubscription = this.prescriptionService.dispenseDetail$
            .subscribe(details => {
                this.dispenseDetails = details;
                this.selectedDrugs = [];
                this.selectedDrugsForDispense = [];
                if (details.prescriptionDrugs && details.prescriptionDrugs.length > 0) {
                    let replaceableMaxPatientShare = 0;
                    let irReplaceableMaxPatientShare = 0;
                    details.prescriptionDrugs?.forEach(drug => {
                        if (drug.suggestedDrugs.find(y => y.benefitCase == "REPLACEABLE_BRAND")) {
                            replaceableMaxPatientShare = drug.suggestedDrugs.find(y => y.benefitCase == "REPLACEABLE_BRAND")?.maxPatientShare!;
                        } else if (drug.suggestedDrugs.find(y => y.benefitCase == "IRREPLACEABLE_BRAND")) {
                            irReplaceableMaxPatientShare = drug.suggestedDrugs.find(y => y.benefitCase == "IRREPLACEABLE_BRAND")?.maxPatientShare!;
                        }
                    });
                    this.dispenseDetails.memberPolicyConsumption["REPLACEABLE_BRAND"].maxPatientShare = replaceableMaxPatientShare;
                    this.dispenseDetails.memberPolicyConsumption["IRREPLACEABLE_BRAND"].maxPatientShare = irReplaceableMaxPatientShare;

                    details.prescriptionDrugs?.forEach(drug => {
                        this.selectBrandForFirstLoad(drug, drug.suggestedDrugs[0]);
                        setTimeout(() => {
                            this.authService.hideSystemLoader();
                        }, 1000)

                    });

                    this.calculateTotals();
                }
            });
        this.subscriptions.push(routerSubscription);
        this.subscriptions.push(detailsSubscription);
    }

    openDispenseSummaryDialogbox() {
        let readyToDispenseDrugs = '';
        this.translate.get('prescription.readyToDispenseDrugs').subscribe(text => readyToDispenseDrugs = text);
        if (this.selectedDrugs.length > 0) {
            this.dispenseSummaryDialogOpen = true;
        }
        else {
            this.dialogService.showWarningDialog(readyToDispenseDrugs, (data: any) => {

            })
        }

    }

    fetchData() {
        this.prescriptionService.fetchDispenseDetailsV2(this.ePrescriptionReferenceNumber).subscribe();
    }

    closeDispenseSummaryDialog = (data: any) => {
        this.dispenseSummaryDialogOpen = false;
    }

    onBackClick = () => {
        this.authService.showSystemLoader()
        this.router.navigateByUrl(`/prescription/details/${this.ePrescriptionReferenceNumber}`);
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
        this.prescriptionService.dispensableDrugs$.next(new ListViewModel());
        this.prescriptionService.dispenseDetail$.next(new DispenseDetail());
    }

    selectAllDrugs() {
        if (this.selectedDrugs.length == this.dispenseDetails.prescriptionDrugs?.length) {
            this.selectedDrugs = [];
            this.selectedDrugsForDispense = [];
        } else {
            this.selectedDrugs = (this.dispenseDetails.prescriptionDrugs ?? []).map(drug => drug ?? '');
            this.selectedDrugsForDispense = (this.dispenseDetails.prescriptionDrugs ?? []).map(drug => drug ?? '');
        }
        this.calculateTotals();
    }
    selectDrug(selecteddrug: any) {
        if (this.selectedDrugs.includes(selecteddrug)) {
            const index = this.selectedDrugs.findIndex(drug => drug == selecteddrug);
            if (index != -1) {
                this.selectedDrugs.splice(index, 1);
                this.selectedDrugsForDispense.splice(index, 1);
            }
        } else {
            this.selectedDrugs.push(selecteddrug);
            this.selectedDrugsForDispense.push(selecteddrug);
        }
        this.calculateTotals()
    }

    selectBrand(drug: DispensableDrug, suggestedDrugs: BrandDrug) {
        let payerRequestTitle = '';
        let payerRequestSubtitle = '';
        this.translate.get('prescription.payerRequestTitle',).subscribe((text: string) => payerRequestTitle = text);
        this.translate.get('prescription.payerRequestSubtitle', { value: suggestedDrugs.sfdaDescription }).subscribe((text: string) => payerRequestSubtitle = text);
        if ((!suggestedDrugs.isApprovalRequired) || (suggestedDrugs.drugFormulary)) {
            drug.unitPrice = suggestedDrugs.unitPrice;
            drug.totalPrice = suggestedDrugs.totalPrice;
            drug.benefitCase = suggestedDrugs.benefitCase || '';
            drug.sfdaCode = suggestedDrugs.sfdaCode;
            drug.sfdaDescription = suggestedDrugs.sfdaDescription;
            drug.dosageForm = suggestedDrugs.dosageForm;
            drug.strengthUnit = suggestedDrugs.strengthUnit;
            drug.strength = suggestedDrugs.strength;
            drug.roaSuggested = suggestedDrugs.roaSuggested;
            drug.isApproved = suggestedDrugs.isApproved;
            drug.patientShareVatAmount = suggestedDrugs.patientShareVatAmount;
            drug.patientShareVatCurrency = suggestedDrugs.patientShareVatCurrency;
            drug.isApprovalRequired = suggestedDrugs.isApprovalRequired;
            if (this.selectedDrugs.find(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand)) {
                this.calculateTotals()
            }
        } else {
            this.dialogService.showConfirmDialog(payerRequestTitle, payerRequestSubtitle, (data: any) => {
                if (data) {
                    drug.unitPrice = suggestedDrugs.unitPrice;
                    drug.totalPrice = suggestedDrugs.totalPrice;
                    drug.benefitCase = suggestedDrugs.benefitCase || '';
                    drug.sfdaCode = suggestedDrugs.sfdaCode;
                    drug.sfdaDescription = suggestedDrugs.sfdaDescription;
                    drug.dosageForm = suggestedDrugs.dosageForm;
                    drug.strengthUnit = suggestedDrugs.strengthUnit;
                    drug.strength = suggestedDrugs.strength;
                    drug.roaSuggested = suggestedDrugs.roaSuggested;
                    drug.isApproved = suggestedDrugs.isApproved;
                    drug.patientShareVatAmount = suggestedDrugs.patientShareVatAmount;
                    drug.patientShareVatCurrency = suggestedDrugs.patientShareVatCurrency;
                    drug.isApprovalRequired = suggestedDrugs.isApprovalRequired;
                    if (this.selectedDrugs.find(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand)) {
                        this.calculateTotals()
                    }
                }
                const index = this.selectedDrugs.findIndex(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand);
                if (index == -1) {
                    this.selectedDrugs.push(drug);
                    this.selectedDrugsForDispense.push(drug);
                }
                if (this.selectedDrugs.find(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand)) {
                    this.calculateTotals()
                }
            })
        }
    }

    selectBrandForFirstLoad(drug: DispensableDrug, suggestedDrugs: BrandDrug) {
        drug.unitPrice = suggestedDrugs.unitPrice;
        drug.totalPrice = suggestedDrugs.totalPrice;
        drug.benefitCase = suggestedDrugs.benefitCase || '';
        drug.sfdaCode = suggestedDrugs.sfdaCode;
        drug.sfdaDescription = suggestedDrugs.sfdaDescription;
        drug.dosageForm = suggestedDrugs.dosageForm;
        drug.strengthUnit = suggestedDrugs.strengthUnit;
        drug.strength = suggestedDrugs.strength;
        drug.roaSuggested = suggestedDrugs.roaSuggested;
        drug.isApproved = suggestedDrugs.isApproved;
        drug.isApprovalRequired = suggestedDrugs.isApprovalRequired;
        drug.patientShare = suggestedDrugs.patientShare ? suggestedDrugs.patientShare : 0;
        const index = this.selectedDrugs.findIndex(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand);
        if (index == -1) {
            this.selectedDrugs.push(drug);
            this.selectedDrugsForDispense.push(drug);
        }

        // if (this.selectedDrugs.find(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand)) {
        //     this.calculateTotals()
        // }
    }

    calculateTotals() {
        this.totals.grandTotal = 0;
        this.totals.noOfDrugs = 0;
        this.totals.quantity = 0;
        this.totals.patientShare = 0;
        this.totals.net = 0;

        console.log('Totals:', this.totals);
        this.dispenseDetails.prescriptionDrugs?.map(drug => drug.benefitCase ?? '').filter(onlyUnique).forEach(benefitCase => {
            if (this.dispenseDetails.memberPolicyConsumption[benefitCase]) {
                this.dispenseDetails.memberPolicyConsumption[benefitCase].currentConsumption = 0;
            }
        })
        if (this.selectedDrugs.length > 0) {
            this.dispenseDetails.prescriptionDrugs?.filter(drug => this.selectedDrugs.find(x => x.scientificCode == drug.scientificCode! && x.isBrand == drug.isBrand)).forEach(drug => {
                this.totals.grandTotal += Number(drug.totalPrice ?? 0);
                this.totals.noOfDrugs += 1;
                this.totals.quantity += Number(drug.quantity ?? 0);

                let calcPaitentShare = 0;
                if (drug.benefitCase == "REPLACEABLE_BRAND" || drug.benefitCase == "IRREPLACEABLE_BRAND") {
                    calcPaitentShare = (Number(drug.patientShare)) / 100 * (drug.totalPrice!);
                    this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].patientShare += calcPaitentShare;
                }

                //drug.patientShare = calcPaitentShare;
                drug.net = drug.totalPrice! - calcPaitentShare;

                if (drug.benefitCase == "REPLACEABLE_BRAND" || drug.benefitCase == "IRREPLACEABLE_BRAND") {
                    this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].currentConsumption += calcPaitentShare;

                    if (this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].currentConsumption > this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].maxPatientShare) {
                        drug.patientShare = this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].currentConsumption - this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].maxPatientShare;
                        drug.net = drug.totalPrice! - drug.patientShare;
                        this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].currentConsumption = this.dispenseDetails.memberPolicyConsumption[(drug.benefitCase!)].maxPatientShare;
                    }
                }
            })
        }

        this.totals.patientShare = this.dispenseDetails.prescriptionDrugs!.map(drug => drug.benefitCase ?? '')
            .filter(onlyUnique).map(benefitCase => this.dispenseDetails.memberPolicyConsumption[benefitCase]?.currentConsumption ? this.dispenseDetails.memberPolicyConsumption[benefitCase].currentConsumption : 0)
            .reduce((pre, curr) => pre + curr);
        this.totals.net = this.totals.grandTotal - this.totals.patientShare;
    }


}



function onlyUnique(value: any, index: number, array: any[]) {
    return array.indexOf(value) === index;
}