import { Component, Input } from '@angular/core';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { exclusionDetailsModel } from '../../drug-exclusion-models/exclusion-details.model';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-add-exclusion-list-based-on-dialog',
  templateUrl: './add-exclusion-list-based-on-dialog.component.html',
  styles: [
  ]
})
export class AddExclusionListBasedOnDialogComponent {

  basedOnExclusionForm: FormGroup = new FormGroup({
    networkExclusion: new FormControl(''),
    providerExclusion: new FormControl(''),
    specialityExclusion: new FormControl(''),
  });

  @Input('onClose')
  onClose?: any;

  @Input('fromPage')
  fromPage?: string;

  basedOn = 'high-cost';
  networkIdInitialOption?: { key: string, value: string };
  selectednetworkId: any = "";
  getNetworkExclusion?: Subscription;
  getSpecialityExclusion?: Subscription;
  networkExclusionValidationMsg!: string;
  selectedNetworkExclusions: { key: string, value: string }[] = [];
  selectedSpecialityExclusions: { key: string, value: string }[] = [];
  specialityExclusionValidationMsg!: string;
  specialityExclusionErrorMsg!: string;
  networkExclusionErrorMsg!: string;
  providerExclusionErrorMsg!: string
  getProviderExclusion?: Subscription;
  providerIdInitialOption?: { key: string, value: string };
  selectedproviderExclusions: { key: string, value: string }[] = [];
  constructor(
    public networkExclusionSvc: ExclusionServiceTsService,
    private translate: TranslateService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.networkExclusionSvc.networkExclusionList$.next([]);
    this.networkExclusionSvc.providerExclusionList$.next([]);
    this.networkExclusionSvc.specialityExclusionList$.next([]);
  }

  fetchNetworkExclusion(query: string) {
    if (query) {
      if (this.getNetworkExclusion != null) {
        this.getNetworkExclusion.unsubscribe();
        this.networkExclusionSvc.networkExclusionList$.next([]);
      }

      this.getNetworkExclusion = this.networkExclusionSvc.getNetworkExclusion({ value: query || '' })
        .subscribe({
          next: (network) => {
            this.getNetworkExclusion = undefined;
            this.selectedNetworkExclusions.push(...network);
            this.networkExclusionErrorMsg = '';
          },
          error: (networkId) => this.getNetworkExclusion = undefined,
        });
    }
    else {
      this.networkExclusionSvc.networkExclusionList$.next([]);
    }
  }



  fetchProviderExclusion(query: string) {
    if (query) {
      if (this.getProviderExclusion != null) {
        this.getProviderExclusion.unsubscribe();
        this.networkExclusionSvc.providerExclusionList$.next([]);
      }

      this.getProviderExclusion = this.networkExclusionSvc.getProviderExclusion({ value: query || '' })
        .subscribe({
          next: (provider) => {
            this.getProviderExclusion = undefined;
            this.selectedproviderExclusions.push(...provider);
            this.providerExclusionErrorMsg = '';

          },
          error: (providerId) => this.getProviderExclusion = undefined,
        });
    }
    else {
      this.networkExclusionSvc.providerExclusionList$.next([]);
    }
  }

  fetchSpecialityExclusion(query: any) {
    if (query) {
      if (this.getSpecialityExclusion != null) {
        this.getSpecialityExclusion.unsubscribe();
        this.networkExclusionSvc.specialityExclusionList$.next([]);
      }

      this.getSpecialityExclusion = this.networkExclusionSvc.getSpecialityExclusion({ value: query || '' })
        .subscribe({
          next: (speciality) => {
            this.getSpecialityExclusion = undefined;
            this.selectedSpecialityExclusions.push(...speciality);
            this.specialityExclusionErrorMsg = "";

          },
          error: (specialityId) => this.getSpecialityExclusion = undefined,
        });
    }
    else {
      this.networkExclusionSvc.specialityExclusionList$.next([]);
    }
  }

  changeBasedOnValue() {
    this.basedOnExclusionForm.reset();
    this.networkExclusionErrorMsg = "";
    this.providerExclusionErrorMsg = "";
    this.specialityExclusionErrorMsg = "";
    this.networkExclusionSvc.networkExclusionList$.next([]);
    this.networkExclusionSvc.providerExclusionList$.next([]);
    this.networkExclusionSvc.specialityExclusionList$.next([]);
  }

  saveExclusionBasedOnList() {
    this.networkExclusionErrorMsg = "";
    if (this.fromPage == "create") {
      if (this.basedOn == "network-exclusion" && this.basedOnExclusionForm.get('networkExclusion')?.value) {
        this.authService.showSystemLoader();
        this.networkExclusionValidationMsg = "";
        let selectedNetworkExclusion: { key: string, value: string };
        selectedNetworkExclusion = this.selectedNetworkExclusions.filter(x => x.key == this.basedOnExclusionForm.get('networkExclusion')?.value)[0];

        let networkExclusion: exclusionDetailsModel = new exclusionDetailsModel();
        networkExclusion.networkId = Number(selectedNetworkExclusion.key);
        networkExclusion.networkName = selectedNetworkExclusion.value;
        networkExclusion.type = "Network Exclusion";
        networkExclusion.providerName = "-";
        networkExclusion.speciality = "-"
        let modalData = {
          basedOn: this.basedOn,
          formData: networkExclusion
        }
        this.onClose(modalData)
      } else if (this.basedOn == "provider-exclusion" && this.basedOnExclusionForm.get('providerExclusion')?.value) {
        this.authService.showSystemLoader();
        this.networkExclusionValidationMsg = "";
        let selectedProviderExclusion: { key: string, value: string };
        selectedProviderExclusion = this.selectedproviderExclusions.filter(x => x.key == this.basedOnExclusionForm.get('providerExclusion')?.value)[0];

        let providerExclusion: exclusionDetailsModel = {
          id: selectedProviderExclusion.key,
          providerId: Number(selectedProviderExclusion.key),
          providerName: selectedProviderExclusion.value,
          type: "Provider Exclusion",
          networkName: "-",
          speciality: "-"
        }
        let modalData = {
          basedOn: this.basedOn,
          formData: providerExclusion
        }
        this.onClose(modalData)
      } else if (this.basedOn == "high-cost") {
        this.authService.showSystemLoader();
        let highCostMedicine: exclusionDetailsModel = {
          id: '0',
          networkName: "-",
          type: "High Cost Medicine",
          providerName: "-",
          speciality: "-"
        }
        let highCostModalData = {
          basedOn: this.basedOn,
          formData: highCostMedicine
        }
        this.onClose(highCostModalData)
      } else if (this.basedOn == "speciality-exclusion" && this.basedOnExclusionForm.get('specialityExclusion')?.value) {
        this.authService.showSystemLoader();
        this.specialityExclusionValidationMsg = "";
        let selectedSpecialityExclusion: { key: string, value: string };
        selectedSpecialityExclusion = this.selectedSpecialityExclusions.filter(x => x.key == this.basedOnExclusionForm.get('specialityExclusion')?.value)[0];

        let specialityExclusion: exclusionDetailsModel = new exclusionDetailsModel();
        specialityExclusion.specialityId = Number(selectedSpecialityExclusion.key);
        specialityExclusion.speciality = selectedSpecialityExclusion.value;
        specialityExclusion.type = "Speciality Exclusion";
        specialityExclusion.providerName = "-";
        specialityExclusion.networkName = "-";

        let modalData = {
          basedOn: this.basedOn,
          formData: specialityExclusion
        }
        this.onClose(modalData)
      } else {
        this.translate.get('prescription.specialityExclusionErrorMsg').subscribe(text => this.specialityExclusionErrorMsg = text);
        this.translate.get('prescription.networkExclusionErrorMsg').subscribe(text => this.networkExclusionErrorMsg = text);
        if (!this.basedOnExclusionForm.get('providerExclusion')?.value) {
          this.translate.get('prescription.providerExclusionErrorMsg').subscribe(text => this.providerExclusionErrorMsg = text);
        }
      }
    } else {
      if (this.basedOn == "network-exclusion" && this.basedOnExclusionForm.get('networkExclusion')?.value) {
        this.networkExclusionErrorMsg = "";
        let selectedNetworkExclusion: { key: string, value: string };
        selectedNetworkExclusion = this.selectedNetworkExclusions.filter(x => x.key == this.basedOnExclusionForm.get('networkExclusion')?.value)[0];

        let networkExclusion: exclusionDetailsModel = new exclusionDetailsModel();
        networkExclusion.networkId = Number(selectedNetworkExclusion.key);
        networkExclusion.networkName = selectedNetworkExclusion.value;
        networkExclusion.type = "Network Exclusion";
        networkExclusion.providerName = "-";
        networkExclusion.speciality = "-"
        let modalData = {
          basedOn: this.basedOn,
          formData: networkExclusion
        }
        this.onClose(modalData);
      } else if (this.basedOn == "provider-exclusion" && this.basedOnExclusionForm.get('providerExclusion')?.value) {
        this.providerExclusionErrorMsg = "";
        let selectedProviderExclusion: { key: string, value: string };
        selectedProviderExclusion = this.selectedproviderExclusions.filter(x => x.key == this.basedOnExclusionForm.get('providerExclusion')?.value)[0];

        let providerExclusion: exclusionDetailsModel = new exclusionDetailsModel()
        providerExclusion.providerId = Number(selectedProviderExclusion.key);
        providerExclusion.providerName = selectedProviderExclusion.value;
        providerExclusion.type = "Provider Exclusion";
        providerExclusion.networkId = Number("-");
        providerExclusion.networkName = "-";
        providerExclusion.speciality = "-";

        let modalData = {
          basedOn: this.basedOn,
          formData: providerExclusion
        }
        this.onClose(modalData)
      } else if (this.basedOn == "high-cost") {
        let highCostMedicine: exclusionDetailsModel = new exclusionDetailsModel();
        highCostMedicine.networkId = Number("-");
        highCostMedicine.networkName = "-";
        highCostMedicine.type = "High Cost Medicine";
        highCostMedicine.providerName = "-";
        highCostMedicine.speciality = "-";

        let highCostmodalData = {
          basedOn: this.basedOn,
          formData: highCostMedicine
        }
        this.onClose(highCostmodalData);
      } else if (this.basedOn == "speciality-exclusion" && this.basedOnExclusionForm.get('specialityExclusion')?.value) {
        this.specialityExclusionErrorMsg = "";
        let selectedspecialityExclusion: { key: string, value: string };
        selectedspecialityExclusion = this.selectedSpecialityExclusions.filter(x => x.key == this.basedOnExclusionForm.get('specialityExclusion')?.value)[0];

        let specialityExclusion: exclusionDetailsModel = new exclusionDetailsModel();
        specialityExclusion.specialityId = Number(selectedspecialityExclusion.key);
        specialityExclusion.speciality = selectedspecialityExclusion.value;
        specialityExclusion.type = "Speciality Exclusion";
        specialityExclusion.providerName = "-";
        specialityExclusion.networkName = "-";

        let modalData = {
          basedOn: this.basedOn,
          formData: specialityExclusion
        }
        this.onClose(modalData);
      } else {
        this.translate.get('prescription.networkExclusionErrorMsg').subscribe(text => this.networkExclusionErrorMsg = text);
        this.translate.get('prescription.specialityExclusionErrorMsg').subscribe(text => this.specialityExclusionErrorMsg = text);
        if (!this.basedOnExclusionForm.get('providerExclusion')?.value) {
          this.translate.get('prescription.providerExclusionErrorMsg').subscribe(text => this.providerExclusionErrorMsg = text);
        }
      }
    }
  }


  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
}
