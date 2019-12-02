package com.computor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Polynomial {

    private int degree = 0;
    private int noTerms;
    private HashMap<String, Term> termMap = new HashMap<>();

    public Polynomial(String equation) {
        parsePolynomial(equation);
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

        this.noTerms = termMap.size();
        findDegree();
    }

    public void findDegree(){
        Iterator<Map.Entry<String, Term>> it = termMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Term> pair = it.next();
            if (pair.getValue().getCoefficient() != 0){
                if (this.degree < pair.getValue().getExponent())
                    this.degree = pair.getValue().getExponent();
            }
        }
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

}
