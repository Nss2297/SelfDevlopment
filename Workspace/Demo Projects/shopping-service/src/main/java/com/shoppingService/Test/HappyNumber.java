package com.shoppingService.Test;

public class HappyNumber {
    private static void populateNums(int num1,int num2,int num){
        num1=num/10;
        num2=num%10;
    }
    public static void main(String[] args) {
        int num=19;
        while(String.valueOf(num).length()!=1){
            int num1=0;
            int num2=0;
            populateNums(num1,num2,num);
            System.out.println(num1);
            System.out.println(num2);
            // if(2==String.valueOf(num).length()&&0==num1||0==num2)break;
            if(0==num1&&String.valueOf(num).length()>2)populateNums(0,0,num2);
            if(0==num2&&String.valueOf(num).length()>2)populateNums(0,0,num1);
            num=num1*num1+num2*num2;
            System.out.println(num);
        }
        String msg=num==1?"Happy number":"Unhappy num";
        System.out.println(num+" is a "+msg);
    }
}
