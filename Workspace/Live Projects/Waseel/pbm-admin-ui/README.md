# PbmAdminUi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 15.0.3.


# App Security

## Routing Security Guide

![example](/README_ASSETS/routing_security.png)

### `MainRouteGuard`
This guard checks if there is a signed in user or not, to decide either to show sign in page, or allow the user to proceed.


### `ModulesGuard`
This guard checks if the signed in user have the require authorities to access any specific module.
the guard requires extra data as showing in the example above
from those extra data `allowedAuthorities` list is required
the other two `allowedAccountId` & `onFailRedirectTo` are optional.

The example above is from the [`app-routing.module.ts`](/src/app/app-routing.module.ts) file which is the main routing module.
as showing the main route with `path: ''` will try to redirect to `/prescription` module, but if user does not have one of the `allowedAuthorities` of prescription module, user will be redirected to `customization` module.

This guard can also be used in in-module routing the same way, check [`prescription.module.ts](/src/app/modules/prescription/prescription.module.ts) file for more examples on that.


## In Component Security

### [Secured-Element Directive](src\app\modules\shared\directives\secured-element\secured-element.directive.ts)

```html
<waseel-button icon="left" iconName="add" routerLink="/prescription/add"
    *ifHasAnyAllowedAuthority="['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION','FOLLOW_UP_PRESCRIPTION'">
    {{'ADD-NEW' | translate}}
</waseel-button>
```

### [`Secured-Element Component`](/src/app/modules/shared/components/secured-element/)
This component can be used like:
```html
        <secured-element
            [allowedAuthorities]="['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'NEW_PRESCRIPTION','FOLLOW_UP_PRESCRIPTION']">
            <waseel-button icon="left" iconName="add" routerLink="/prescription/add">{{'ADD-NEW' |
                translate}}</waseel-button>
        </secured-element>
```
It surrounds the elements that needs be hidden based on user authorities.
And it is mandatory to have the `allowedAuthorities` list otherwise the surrounded element will never show.


# [ListView](src/app/modules/shared/components/list-view/list-view.component.ts) Component

This component is a table like view, that can be used to display list of data. It also handles the pagination and user click events.

## Properties

<strong>Inputs</strong>:

- list
  - of type [`ListViewModel`](src/app/modules/shared/components/list-view/models/list-view.model.ts)`<T extends `[`ListItem`](src/app/modules/shared/components/list-view/models/list-item.model.ts)`>`
  it holds the `content` which is an array of type `T[]` and the page information, like page `number` and `size`.
  
- prototype
  - The javascript prototype of class `T` used in the `ListViewModel`. This is required because the class must contain information on how to render the table headers and in which order. For example:
  ```Typescript
  export class PrescriptionDetailsDiagnosis extends ListItem {
    override id?: string;

    @Header('customization.icdCode', 1, ['diagnosisName'])
    diagnosisCode?: string;
    @Header('prescription.codeDescription', 2)
    diagnosisCodeDescription?: string;
    @Header('prescription.type', 3)
    diagnosisType?: string;

    diagnosisName?: string;
  }
  ```
  Based on this model, the list view will only render 3 columns corresponding to the properties that have [`@Header`](src/app/modules/shared/components/list-view/models/decorators/header.decorator.ts) decorator on them. `@Header` takes tree parameters; the display code, which is a code that comes from the [localization files](src/assets/i18n), the order of the header, and optionally a list of other properties that should be embedded in the same column. in the example above `diagnosisCode` cells will also contain the `diagnosisName` value.
- isLoading
  - of type `boolean`. When it is true a loading spinner will show. should be set to true whenever data is being loaded from backend.
- extraClasses
  - extra classes to be added on rows (`tr`), all the cells (`td`) in a column or a single cell.
  - of type `{ [row_col: string]: string }`.
  - example: 
  ```Typescript
  extraClasses = {'0:':'firstRowOnlyClass'. ':0':'firstColumnOnlyClass', '0:0':'firstCellOnlyClass'};
  ```
- translateData
  - to indicate which row, column, or cell to use the `translate` pipe on its values
  - example:
  ```Typescript
  translateData = [
        '2:', //third row
        ':0', //first column
        '4:1' //second cell in fifth row
  ]
  ```
- showEditButton
  - to show or hide edit icon
- showDeleteButton
  - to show or hide delete icon
- showMoreActionsMenu
  - to show or hide more actions icon that opens menu of actions
- moreActionsList
  - list of actions that will show in the more actions menu
  - of type
  ```Typescript
    { 
        action: string, // a name to be used to identify this action, it should be unique.
        displayCode: string, // to display the label of the action based on selected language.
        isVisible: (item: any) => boolean, // a function to check if this action should be visible for this item.
        allowedAuthorities?: string[] // which authorities a user must have to be able to use this action.
    }[]
  ```
- showCheckBox
  - to show or hide check boxes in the first column
- showEmptyRows
  - to show or hide extra empty rows when the page content are less than the preferred page size
- customCells
  - to customize how cells should be rendered.
  - example:
  ![01](/README_ASSETS/customCellsExample-01.jpg)
  ![02](/README_ASSETS/customCellsExample-02.jpg)
  this example tells the `ListView` that whenever it tries to render a `memberName` cell, it should use the `ng-template`. And the `ng-template` requires the full row data as input. With this we can also add form elements or anything inside the ng-template.

<strong>Outputs:</strong>

- onNext
  - when next page button is clicked
- onPrevious
  - when previous page button is clicked
- onSizeChange
  - when page size is changed
- onEdit
  - when edit icon is clicked
- onDelete
  - when delete is clicked
- onItemClick
  - when user clicks on one of the rows
- onMoreActionMenu
  - when any action in the more actions menu is clicked

# Auto Generated README

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
