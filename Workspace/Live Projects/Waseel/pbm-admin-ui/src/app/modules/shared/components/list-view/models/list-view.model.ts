import { ListItem } from "./list-item.model";


export class ListViewModel<T extends ListItem> {

    content: T[] = [];

    number: number = 0;
    size: number = 10
    totalPages: number = 0;
    
    totalElements?: number;

    first?: boolean;
    last?: boolean;
    drugCode?: number

}