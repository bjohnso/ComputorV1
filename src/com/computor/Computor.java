package com.computor;

public class Computor {

    public static void main(String args[]) {
        if (args.length == 1){
            Polynomial polynomial = new Polynomial(args[0]);
            if (polynomial.simplify()) {
                System.out.println("Reduced Form: " + polynomial.reducedForm());
                System.out.println("Degree: " + polynomial.findDegree());
                System.out.println("Discriminant: " + polynomial.findDiscriminant());
                for (Double d : polynomial.solveQuadratic(polynomial.findDiscriminant())) {
                    System.out.println("X = " + d);
                }
            }
            else{
                System.out.println("Polynomial could not be solved");
            }
        } else {
            System.out.println("You must enter a single equation");
            System.exit(-1);
        }
    }
}