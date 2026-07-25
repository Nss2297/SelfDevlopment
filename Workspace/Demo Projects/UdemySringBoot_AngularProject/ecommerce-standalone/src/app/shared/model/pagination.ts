export interface Pagination<T> {
    totalPages: number,
    totalCount: number,
    pageIndex: number,
    pageSize: number;
    dataList: T
}
