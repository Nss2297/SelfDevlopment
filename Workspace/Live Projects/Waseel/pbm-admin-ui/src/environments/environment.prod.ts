import { Environment } from "./environment.type";


export const environment: Environment = {
    name: 'prod',
    backend: {
        hostname: "https://rx.waseel.com/api",
        authService: "/oauth",
        admin: {
            customization: '/customizations/drugs/diagnosis',
            dssCustomization: '/dss-customizations/drugs',
            // dsscustomizationage:'/dss-customizations/drugs/age',
            lov: '/lov'
        },
        prescriptionservice: {
            prescription: '/payers/102/prescriptions',
            lov: '/prescriptions/lov',
            dssLov: '/dss-lov',
            prescriptionProvider: '/prescriptions',
            customizationService: '/payer-customization-service',
            customizationLov: '/payer-customization-service/lovs',
            drugFormularyService: '/drug-formulary',
            drugExclusionService: '/drug-exclusion',
            memberManagementService: '/member-management',
            sfdaManagementService: '/sfda-management'
        }
    },
    featureToggle: {
        prescribeGenericOrBrandFlow: false
    },
    apm: "https://rx.waseel.com/apm"
}