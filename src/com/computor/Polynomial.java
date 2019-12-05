package com.computor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Polynomial {

    private double a = 0.0;
    private double b = 0.0;
    private double c = 0.0;
    private int degree = 0;
    private HashMap<String, Term> termMap = new HashMap<>();
    private ArrayList<String> answers = new ArrayList<>();
    private String status = "";

    public Polynomial(String equation) {
        //COLLECTS AND ADDS LIKE TERMS
        parsePolynomial(equation);
        //REMOVES TERMS WITH COEFFICIENT OF ZERO
        cleanTermMap();
    }

    private void parsePolynomial(String equation) {
        boolean equals = false;
        String split[] = equation.split(" ");
        Term term = new Term();

        for (int i = 0; i < split.length; i++){
            //Check for delimiter
            if (split[i].charAt(0) == '+' || split[i].charAt(0) == '-' || split[i].charAt(0) == '='){

                if (split[i].charAt(0) == '=')
                    equals = true;

                //CHECK IF NEW SIGN INDICATOR
                if (term.getStatus()) {
                    //CHECK IF TERM ALIAS EXISTS
                    if (termMap.get(term.getAlias()) != null){
                        termMap.get(term.getAlias()).modCoefficient(term.getCoefficient());
                    } else
                        termMap.put(term.getAlias(), term);
                    term = new Term();
                    if (equals)
                        term.setSign(-1);
                }

                if (split[i].charAt(0) == '+')
                    term.setSign(1);
                else if (split[i].charAt(0) == '-')
                    term.setSign(-1);

            } else if (split[i].charAt(0) == '*' || split[i].charAt(0) == '/'){
                term.setOperation("" + split[i].charAt(0));
            } else if ((split[i].charAt(0) >= 'a' && split[i].charAt(0) <= 'z')
                    || split[i].charAt(0) >= 'A' && split[i].charAt(0) <= 'Z'){
                term.setVariable("" + split[i].charAt(0));
                if (split[i].length() < 3)
                    term.setExponent(1);
                else
                    term.setExponent(Integer.parseInt("" + split[i].charAt(2)));
                term.setAlias(term.getVariable() + '^' + term.getExponent());
                if (!term.isCoefficientSet())
                    term.setCoefficient(1.0);
            } else if (split[i].charAt(0) >= '0' && split[i].charAt(0) <= '9')
                term.setCoefficient(Double.parseDouble(split[i]));
        }

        if (term.getStatus()) {
            //CHECK IF TERM ALIAS EXISTS
            if (termMap.get(term.getAlias()) != null){
                termMap.get(term.getAlias()).modCoefficient(term.getCoefficient());
            } else
                termMap.put(term.getAlias(), term);
        }
    }

    public void cleanTermMap(){
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() == 0)
                termMap.remove(pair);
        }
    }

    public boolean solve(){
        String common = "";
        int exponents = 0;
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        Map.Entry<String, Term> pair;

        if (it.hasNext()) {
            pair = it.next();
            common = pair.getValue().getVariable();
            if (pair.getValue().getCoefficient() != 0 && pair.getValue().getExponent() != 0){
                exponents++;
            }
        } else {
            return false;
        }

        while (it.hasNext()) {
            pair = it.next();
            if (!common.equals(pair.getValue().getVariable())){
                return false;
            }
            if (pair.getValue().getCoefficient() != 0 && pair.getValue().getExponent() != 0){
                exponents++;
            }
        }

        if (exponents < termMap.size() && findDegree() == 2) {
            //FIND DISCRIMINANT AND SOLVE USING QUADRATIC FORMULA
            this.answers = solveQuadratic(findDiscriminant());
            this.status = "quadratic";
            return true;
        } else if (exponents == termMap.size()){
            int mod;
            if (findInverseDegree() > 0)
                mod = 1;
            else
                mod = -1;
            it = termMap.entrySet().iterator();
            ArrayList<Term> termList = new ArrayList<>();
            while (it.hasNext()) {
                pair = it.next();
                Term term = pair.getValue();
                term.setOperation("*");
                term.setExponent(term.getExponent() + mod);
                term.setAlias(term.getVariable() + "^" + term.getExponent());
                termList.add(term);
            }
            termMap.clear();
            for (Term t : termList) {
                termMap.put(t.getAlias(), t);
            }
            return solve();
        } else if (findDegree() == 1){
            //IF DEGREE IS ONE WE CAN SOLVE FOR X
            this.answers = solveSimple();
            this.status = "simple";
            return true;
        } else if (exponents == 0){
            this.status = "infinite";
            return true;
        }
        else {
            //POLYNOMIAL IS UNSOLVABLE
            return false;
        }
    }

    public int findDegree(){
        int degree = 0;
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() != 0){
                if (degree < pair.getValue().getExponent())
                    degree = pair.getValue().getExponent();
            }
        }
        return degree;
    }

    public int findInverseDegree(){
        int inverse = 0;
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() != 0){
                if (inverse > pair.getValue().getExponent())
                    inverse = pair.getValue().getExponent();
            }
        }
        return inverse;
    }

    public int getDegree(){
        return this.degree;
    }

    public String reducedForm(){
        String toReturn = "";
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() != 0) {
                if (pair.getValue().getCoefficient() < 0) {
                    if (toReturn.equals(""))
                        toReturn += "- " + pair.getValue().getCoefficient() * -1 + " * " + pair.getValue().getAlias();
                    else
                        toReturn += " - " + pair.getValue().getCoefficient() * -1 + " * " + pair.getValue().getAlias();
                } else {
                    if (toReturn.equals(""))
                        toReturn += pair.getValue().getCoefficient() + " * " + pair.getValue().getAlias();
                    else
                        toReturn += " + " + pair.getValue().getCoefficient() + " * " + pair.getValue().getAlias();
                }
            }
        }

        if (toReturn.equals(""))
            toReturn += "0";

        return toReturn + " = 0";
    }

    public double findDiscriminant() {
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() != 0) {
                switch (pair.getValue().getExponent()) {
                    case 2:
                        this.a += pair.getValue().getCoefficient();
                        break ;
                    case 1:
                        this.b += pair.getValue().getCoefficient();
                        break ;
                    default:
                        this.c += pair.getValue().getCoefficient();
                }
            }
        }
        return (b * b) + (-4 * a * c);
    }

    private ArrayList<String> solveQuadratic(Double discriminant){
        ArrayList<String> answers = new ArrayList<>();
        if (discriminant < 0){
            return solveComplex(discriminant);
        }
        answers.add("" + ((-1 * b) + Math.sqrt(discriminant)) / (2 * a));
        answers.add("" +((-1 * b) - Math.sqrt(discriminant)) / (2 * a));
        return answers;
    }

    private ArrayList<String> solveSimple(){
        Double ans = 0.0;
        Double fin = 0.0;
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getExponent() == 0)
                ans += pair.getValue().getCoefficient();
            else if (pair.getValue().getExponent() == 1)
                fin = pair.getValue().getCoefficient();
        }
        ans *= -1;
        ArrayList<String> answers = new ArrayList<>();
        answers.add("" + ans / fin);
        return answers;
    }

    public ArrayList<String> solveComplex(double discriminant){
        double term1 = (this.b * -1) / (2 * this.a);
        double term2 = (Math.sqrt(discriminant * -1) / (2 * this.a));
        answers.add(term1 + " + " + term2 + " * i");
        answers.add(term1 + " - " + term2 + " * i");
        return answers;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getStatus() {
        return status;
    }
}
