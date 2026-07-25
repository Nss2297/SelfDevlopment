export type Environment = {
    name: string,
    backend: {
        hostname: string,
        authService: string,
        admin: {
            customization: string,
            dssCustomization:string,
            // dsscustomizationage:string
            lov: string
        },
        prescriptionservice: {
            prescription: string,
            lov: string,
            dssLov: string,
            prescriptionProvider: string,
            customizationService: string;
            customizationLov: string,
            drugFormularyService: string,
            drugExclusionService: string,
            memberManagementService: string,
            sfdaManagementService: string
        }

    },
    featureToggle: { [key: string]: boolean },
    apm: string
}