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
                        System.out.println("Discriminant is strictly zero, the repeated solutions are:");
                    else
                        System.out.println("Discriminant is strictly negative, the solutions are unreal:");
                }
                if (polynomial.getStatus().equals(("infinite"))){
                    System.out.println("All the real numbers are the solution");
                } else {
                    for (String s : polynomial.getAnswers())
                        System.out.println("X = " + s);
                }
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