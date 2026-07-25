import "reflect-metadata";

export function Header(displayCode: string, order: number, nestedProperties?: string[]) {
    return function (target: any, propertyKey: string) {
        Reflect.defineMetadata("Header", displayCode, target, propertyKey);
        Reflect.defineMetadata("Order", order, target, propertyKey);
        Reflect.defineMetadata("NestedProperties", nestedProperties, target, propertyKey);
    };
}