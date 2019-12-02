package com.computor;

public class Computor {

    public static void main(String args[]) {
        if (args.length == 1){
            Polynomial polynomial = new Polynomial(args[0]);
            System.out.println(polynomial.reducedForm());
        } else {
            System.out.println("You must enter a single equation");
            System.exit(-1);
        }
    }
}