package com.computor;

public class Computor {

    public static void main(String args[]) {
        if (args.length == 1){
            Polynomial polynomial = new Polynomial(args[0]);
            System.out.println("Reduced Form: " + polynomial.reducedForm());
            System.out.println("Degree: " + polynomial.getDegree());
        } else {
            System.out.println("You must enter a single equation");
            System.exit(-1);
        }
    }
}