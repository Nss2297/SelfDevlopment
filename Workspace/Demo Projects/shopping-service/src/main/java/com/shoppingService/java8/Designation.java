package com.shoppingService.java8;

public enum Designation {

    SE("se"),SSE("sse"),TL("tl"),STL("stl"),APM("apm"),PM("pm"),SALES("sales"),HR("hr");

    public final String name;

    Designation(String name){
        this.name = name;
    }


    public static String valueOfDesignation(Designation designation) {
        for (Designation e : values()) {
            if (e.name.equals(designation.name)) {
                return e.name;
            }
        }
        return null;
    }






}
