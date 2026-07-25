package com.shoppingService.Test;

import java.util.List;

public class Test264 {
    private static Object describe(Object o) {
        return (Object) switch (o) {
            case String str -> str.length();
            case Integer intVal -> intVal * 2;
            case Double dob -> Math.round(dob);
            default -> o;
        };
    }


    private static String objectByType(Object o) {
        return switch (o) {
            case Integer val -> "INT:" + val;
            case String val -> "STR:" + val.length() + ":" + val;
            case Double val -> "DBL:" + val;
            case null -> "NULL";
            default -> "UNKNOWN";
        };
    }

    private static String numByGaurd(Object o) {
        return switch (o) {
            case Integer val when val < 10 -> "small";
            case Integer val when val >= 10 -> "big";
            case Double val when val < 0 -> "negative double";
            case Double val when val >= 0 -> "posetive double";
            default -> "other";
        };
    }

//    private static String identityListaType(Object o){
////        return switch(0){
////            case List<String> list->"empty list";
////        };
//    }
    public static void main(String[] args) {
//        System.out.println(describe("sdfsdf"));
//        System.out.println(describe(1));
//        System.out.println(describe(1.43));
//        System.out.println(objectByType(1));
//        System.out.println(objectByType("sdgwe"));
//        System.out.println(objectByType(2.5));
//        System.out.println(objectByType(null));
        System.out.println(numByGaurd("sdfs"));
        System.out.println(numByGaurd(1));
        System.out.println(numByGaurd(10));
        System.out.println(numByGaurd(11));
        System.out.println(numByGaurd(-1.05));
        System.out.println(numByGaurd(0.0));
        System.out.println(numByGaurd(0.9));
    }
}
