package com.org.effective;

public class Client {
    public static void main(String[] args){
        int minsWorked = 40;
        float payRate = 23.2f;
        for (PayDay payDay : PayDay.values()){
            System.out.printf("pay %f on %s: %n", payDay.pay(Integer.valueOf(minsWorked), Float.valueOf(payRate)), payDay.toString());
        }
    }
}
