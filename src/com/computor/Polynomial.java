package com.computor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Polynomial {

    private double a;
    private double b;
    private double c;
    private int degree = 0;
    private HashMap<String, Term> termMap = new HashMap<>();

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

                //check if new term or sign indicator
                if (term.getStatus()) {
                    //Check if term alias exists
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
                term.setExponent(Integer.parseInt("" + split[i].charAt(2)));
                term.setAlias(term.getVariable() + '^' + term.getExponent());
            } else if (split[i].charAt(0) >= '0' && split[i].charAt(0) <= '9')
                term.setCoefficient(Double.parseDouble(split[i]));
        }

        if (term.getStatus()) {
            //Check if term alias exists
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

    public boolean simplify(){
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

        System.out.println("EXPONENTS : " + exponents + " TERMS : " + termMap.size());

        if (exponents < termMap.size() && findDegree() <= 2)
            return true;
        else if (exponents == termMap.size()){
            it = termMap.entrySet().iterator();
            ArrayList<Term> termList = new ArrayList<>();
            while (it.hasNext()) {
                pair = it.next();
                Term term = pair.getValue();
                term.setExponent(term.getExponent() - 1);
                term.setAlias(term.getVariable() + "^" + term.getExponent());
                termList.add(term);
            }
            termMap.clear();
            for (Term t : termList){
                termMap.put(t.getAlias(), t);
            }
            return simplify();
        } else {
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

    public ArrayList<Double> solveQuadratic(Double discriminant){
        ArrayList<Double> answers = new ArrayList<>();
        answers.add(((-1 * b) + Math.sqrt(discriminant)) / (2 * a));
        answers.add(((-1 * b) - Math.sqrt(discriminant)) / (2 * a));
        return answers;
    }
}
