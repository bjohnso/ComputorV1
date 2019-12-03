package com.computor;

public class Computor {

    public static void main(String args[]) {
        if (args.length == 1){
            Polynomial polynomial = new Polynomial(args[0]);
            if (polynomial.solve()) {
                System.out.println("Reduced Form: " + polynomial.reducedForm());
                System.out.println("Degree: " + polynomial.findDegree());
                if (polynomial.getStatus().equals("quadratic")){
                    if (polynomial.findDiscriminant() > 0)
                        System.out.println("Discriminant is strictly positive, the two solutions are:");
                    else if (polynomial.findDiscriminant() == 0)
                        System.out.println("Discriminant is strictly zero, teh repeated solution are:");
                    else
                        System.out.println("Discriminant is strictly negative, only one solution is an element of the reals:");
                }
                for (Double d : polynomial.getAnswers())
                    System.out.println("X = " + d);
            }
            else{
                System.out.println("Polynomial could not be solved");
                System.exit(0);
            }
        } else {
            System.out.println("You must enter a single equation");
            System.exit(-1);
        }
    }
}