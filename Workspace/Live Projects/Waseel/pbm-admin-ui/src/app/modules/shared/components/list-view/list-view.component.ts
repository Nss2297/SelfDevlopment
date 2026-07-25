import { Component, EventEmitter, Input, OnChanges, OnInit, Output, TemplateRef } from '@angular/core';
import { FormControl } from '@angular/forms';
import "reflect-metadata";
import { ListViewModel } from './models/list-view.model';
import { BehaviorSubject } from 'rxjs';
import { ListItem } from './models/list-item.model';

@Component({
    selector: 'list-view',
    templateUrl: './list-view.component.html',
    styles: []
})
export class ListViewComponent implements OnInit, OnChanges {
    selectedCheckBoxItem: string[] = new Array();
    selectedCheckboxCountOfPage = 0;
    allCheckBoxIsIndeterminate: boolean = false;
    allCheckBoxIsChecked: boolean = false;
    @Input("list")
    list?: ListViewModel<any>;
    listData: any;
    @Input()
    prototype!: ListItem;

    @Input()
    isLoading: boolean = false;

    @Input("extraClasses")
    extraClasses: { [row_col: string]: string } = {};

    @Input("translateData")
    translateData: string[] = [];

    @Input("showEditButton")
    showEditButton: boolean | ((item: any) => boolean) = true;
    @Input("showDeleteButton")
    showDeleteButton: boolean | ((item: any) => boolean) = true;
    @Input("showAcceptButton")
    showAcceptButton: boolean | ((item: any) => boolean) = false;
    @Input("showRejectButton")
    showRejectButton: boolean | ((item: any) => boolean) = false;
    @Input("showCommentButton")
    showCommentButton: boolean | ((item: any) => boolean) = false;
    @Input("commentButtonColor")
    commentButtonColor: string = "default";
    @Input("showMoreActionsMenu")
    showMoreActionsMenu: boolean = false;
    @Input("showCheckBox")
    showCheckBox: boolean = false;
    @Input("hideScrollbar")
    hideScrollbar?: boolean = false;
    @Input()
    showEmptyRows: boolean = true;
    @Input()
    showItemsPerPageSelection: boolean = true;
    @Input()
    moreActionsList: { action: string, displayCode: string, isVisible: (item: any) => boolean, allowedAuthorities?: string[] }[] = [];

    @Input()
    customCells: { [propertyName: string]: TemplateRef<any> } = {};
    @Input()
    propertiesToHide: string[] = [];

    @Input("noContentTitle")
    noContentTitle: string = " ";

    @Input("noContentSubtitle")
    noContentSubtitle: string = " ";

    @Input("isNoContentSearchAgain")
    isNoContentSearchAgain: boolean = false;


    @Output("onNext")
    onNextEmitter: EventEmitter<{ event: MouseEvent, pageNumber: number }> = new EventEmitter();
    @Output("onPrevious")
    onPreviousEmitter: EventEmitter<{ event: MouseEvent, pageNumber: number }> = new EventEmitter();
    @Output("onSizeChange")
    onSizeChangeEmitter: EventEmitter<{ pageSize: number }> = new EventEmitter();
    @Output("onEdit")
    onEditEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    @Output("onDelete")
    onDeleteEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    @Output("onAccept")
    onAcceptEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    @Output("onReject")
    onRejectEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    @Output("onComment")
    onCommentEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    // onCommentEmitter: EventEmitter<{ action: string, id: string }> = new EventEmitter();
    @Output("onItemClick")
    onItemClickEmitter: EventEmitter<{ event: MouseEvent, id: string }> = new EventEmitter();
    isItemClickEmitterUsed: boolean = false;
    @Output("onMoreActionMenu")
    onMoreActionsMenuItemClickEmitter: EventEmitter<{ action: string, id: string }> = new EventEmitter();
    @Output("onChange")
    onChangeEmitter: EventEmitter<{ isChecked: boolean, isHeaderCheckBox: boolean, data: any }> = new EventEmitter();

    paginationOptions = [
        { key: '5', value: '5' },
        { key: '10', value: '10', selected: true },
        { key: '25', value: '25' },
        { key: '50', value: '50' }
    ]

    pageSizeControl: FormControl = new FormControl();

    orderedHeaders: { headerDisplayCode: string, order: number, propertyName: string, nestedProperties: string[] }[] = [];

    hasContent$: BehaviorSubject<boolean> = new BehaviorSubject(false);



    constructor() {

    }

    ngOnInit(): void {
        this.listData = this.list?.content;
        this.pageSizeControl.valueChanges.subscribe(() => {
            this.onSizeChangeEmitter.emit({ pageSize: this.pageSizeControl.value });
        });
        this.isItemClickEmitterUsed = this.onItemClickEmitter.observed;
    }

    ngOnChanges(): void {
        if (this.list && this.list.content?.length > 0) {
            if (!this.pageSizeControl.value) {
                this.pageSizeControl.setValue(this.list.size);
            }
            this.orderedHeaders = Object.getOwnPropertyNames(this.list.content[0])
                .filter(propertyName => !this.propertiesToHide.includes(propertyName))
                .map(propertyName => {
                    const headerDisplayCode = Reflect.getMetadata("Header", this.prototype, propertyName);
                    const order = Reflect.getMetadata("Order", this.prototype, propertyName);
                    const nestedProperties = Reflect.getMetadata("NestedProperties", this.prototype, propertyName);
                    return { headerDisplayCode, order, propertyName, nestedProperties };
                })
                .filter(header => header != undefined && header.headerDisplayCode != undefined)
                .sort((h1, h2) => h1.order - h2.order);
            //START: For Header checkbox 
            this.selectedCheckboxCountOfPage = 0;
            for (const list of this.list.content) {
                if (this.selectedCheckBoxItem.includes(list.id)) {
                    this.selectedCheckboxCountOfPage++;
                }
            }
            this.setAllCheckBoxIsIndeterminate();
            //END: For Header checkbox 
            this.hasContent$.next(true);
        } else {
            this.hasContent$.next(false);
        }
    }

    ngAfterViewInit() {
        this.pageSizeControl.setValue(this.list?.size);
    }

    get emptyRows() {
        if (this.showEmptyRows)
            if (this.list)
                if (this.list.size <= 10 && this.list.content.length < this.list.size)
                    return Array(this.list.size - this.list.content.length).fill(0);
                else return [];
            else
                return Array(10).fill(0);
        else return [];
    }

    get hasPrevious() {
        return (this.list && this.list.number > 0);
    }

    get hasNext() {
        return (this.list && this.list.number < (this.list.totalPages - 1));
    }

    onNext(event: MouseEvent) {
        if (this.list && this.list.number < (this.list.totalPages - 1) && !this.isLoading)
            this.onNextEmitter.emit({ event: event, pageNumber: this.list.number + 1 });
    }
    onPrevious(event: MouseEvent) {
        if (this.list && this.list.number > 0 && !this.isLoading)
            this.onPreviousEmitter.emit({ event: event, pageNumber: this.list.number - 1 });
    }
    onEdit(event: MouseEvent, itemId: string) {
        event.stopPropagation();
        this.onEditEmitter.emit({ event: event, id: itemId });
    }
    onDelete(event: MouseEvent, itemId: string) {
        event.stopPropagation();
        this.onDeleteEmitter.emit({ event: event, id: itemId });
    }
    onAccept(event: MouseEvent, itemId: string) {
        event.stopPropagation();
        this.onAcceptEmitter.emit({ event: event, id: itemId });
    }
    onReject(event: MouseEvent, itemId: string) {
        event.stopPropagation();
        this.onRejectEmitter.emit({ event: event, id: itemId });
    }
    onComment(event: MouseEvent, itemId: string) {
        event.stopPropagation();
        this.onCommentEmitter.emit({ event: event, id: itemId });
    }
    onItemClick(event: MouseEvent, itemId: string) {
        this.onItemClickEmitter.emit({ event: event, id: itemId });
    }

    onMoreActionsMenuItemClick(action: string, id: string) {
        this.onMoreActionsMenuItemClickEmitter.emit({ action, id });
    }

    onCheckboxChange(event: any, item: any) {
        event.stopPropagation();
        if (this.selectedCheckBoxItem.includes(item.id)) {
            this.selectedCheckBoxItem.splice(this.selectedCheckBoxItem.indexOf(item.id), 1);
            this.selectedCheckboxCountOfPage--;
        } else {
            this.selectedCheckBoxItem.push(item.id);
            this.selectedCheckboxCountOfPage++;
        }
        this.setAllCheckBoxIsIndeterminate();
        this.onChangeEmitter.emit({ isChecked: event.target.checked, isHeaderCheckBox: false, data: item });
    }

    onHeaderCheckboxChange(event: any, item: any) {
        if (this.selectedCheckboxCountOfPage != this.list?.content.length) {
            for (const list of this.list?.content!) {
                if (!this.selectedCheckBoxItem.includes(list.id)) {
                    this.selectCheckbox(list.id);
                }
            }
        } else {
            for (const claim of this.list?.content!) {
                this.selectCheckbox(claim.id);
            }
        }
        this.onChangeEmitter.emit({ isChecked: event.target.checked, isHeaderCheckBox: true, data: item });
    }

    setAllCheckBoxIsChecked() {
        if (this.list?.content != null) {
            this.allCheckBoxIsChecked = this.selectedCheckboxCountOfPage == this.list?.content.length;
        } else {
            this.allCheckBoxIsChecked = false;
        }
    }
    selectCheckbox(id: string) {
        if (!this.selectedCheckBoxItem.includes(id)) {
            this.selectedCheckBoxItem.push(id);
            this.selectedCheckboxCountOfPage++;
        } else {
            this.selectedCheckBoxItem.splice(this.selectedCheckBoxItem.indexOf(id), 1);
            this.selectedCheckboxCountOfPage--;
        }
        this.setAllCheckBoxIsIndeterminate();
    }

    setAllCheckBoxIsIndeterminate() {
        if (this.list?.content != null) {
            this.allCheckBoxIsIndeterminate = this.selectedCheckboxCountOfPage != this.list?.content.length && this.selectedCheckboxCountOfPage != 0;
        } else { this.allCheckBoxIsIndeterminate = false; }
        this.setAllCheckBoxIsChecked();
    }

    getItemId(item: any) {
        let prototypedItem = { ...item } as ListItem;
        Object.setPrototypeOf(prototypedItem, this.prototype);
        return prototypedItem.id;
    }


    evaluateShowingEditButton(row: any) {
        return (typeof this.showEditButton == 'boolean' && this.showEditButton) || (typeof this.showEditButton == 'function' && this.showEditButton(row));
    }

    evaluateShowingDeleteButton(row: any) {
        return (typeof this.showDeleteButton == 'boolean' && this.showDeleteButton) || (typeof this.showDeleteButton == 'function' && this.showDeleteButton(row));
    }

    evaluateShowingAcceptButton(row: any) {
        return (typeof this.showAcceptButton == 'boolean' && this.showAcceptButton) || (typeof this.showAcceptButton == 'function' && this.showAcceptButton(row));
    }

    evaluateShowingRejectButton(row: any) {
        return (typeof this.showRejectButton == 'boolean' && this.showRejectButton) || (typeof this.showRejectButton == 'function' && this.showRejectButton(row));
    }

    evaluateShowingEmptyCommentButton(row: any) {
        return row.showCommentIcon && row.decisionDescription == "";
    }

    evaluateShowingFilledCommentButton(row: any) {
        return row.showCommentIcon && row.decisionDescription != "";
    }
    evaluateShowingCommentButton(row: any) {
        return (typeof this.showCommentButton == 'boolean' && this.showCommentButton) || (typeof this.showCommentButton == 'function' && this.showCommentButton(row));
    }

    rowHasActions(row: any) {
        return this.moreActionsList.some(action => action.isVisible(row));
    }

}
