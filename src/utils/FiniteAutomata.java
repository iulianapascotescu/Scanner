package utils;

import utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private List<String> states;
    private List<String> alphabet;
    private String initialState;
    private List<String> finalStates;
    private List<Pair<Pair<String, String>, String>> transitions;
    private String file;

    public FiniteAutomata(String file) throws FileNotFoundException {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.file = file;
        this.readFromFile();
    }

    public void readFromFile() throws FileNotFoundException {
        File file = new File(this.file);
        Scanner scanner = new Scanner(file);

        List<String> lines = new ArrayList<>();
        while(scanner.hasNextLine())
            lines.add(scanner.nextLine());

        if(lines.size()==5)
        {
            this.initialState = lines.get(3);
            this.states = this.split(lines.get(0));
            this.alphabet = this.split(lines.get(1));
            this.finalStates = this.split(lines.get(4));
            this.transitions =this.splitProductions(lines.get(2));
        }

        scanner.close();
    }

    public List<String> split(String line){
        //{p, q, r}
        List<String> result = new ArrayList<>();
        String string="";
        for(int i=1;i<line.length()-1;i++)
            string+=line.charAt(i);
        result = Arrays.asList(string.split(", "));
        return result;
    }

    public List<Pair<Pair<String, String>, String>> splitProductions(String line){
        List<Pair<Pair<String, String>, String>> result = new ArrayList<>();
        String string="";
        for(int i=1;i<line.length()-1;i++)
            string+=line.charAt(i);
        List<String> strings = new ArrayList<>();
        strings = Arrays.asList(string.split("; "));
        for(String s : strings)
        {
            List<String> ss = Arrays.asList(s.split("->"));
            String el1 = ss.get(0);
            String el2 = ss.get(1);
            String s1="";
            for(int i=1;i<el1.length()-1;i++)
                s1+=el1.charAt(i);
            List<String> p1=Arrays.asList(s1.split(","));
            Pair<String, String> pair = new Pair<>(p1.get(0), p1.get(1));
            Pair<Pair<String, String>, String> element = new Pair<>(pair, el2);
            result.add(element);
        }
        return result;
    }

    public boolean isAccepted(String DFA){
        String state1 = this.initialState;
        for(int i=0;i<DFA.length();i++){
            String state2 = String.valueOf(DFA.charAt(i));
            boolean ok = false;
            for(int j = 0; j< transitions.size(); j++)
            {
                Pair<Pair<String, String>, String> element = transitions.get(j);
                if(element.element1.element1.equals(state1) && element.element1.element2.equals(state2))
                {
                    ok=true;
                    if(i==DFA.length()-1 && this.finalStates.contains(element.element2))
                        return true;
                    state1=element.element2;
                    break;
                }
            }
            if(ok==false)
                return false;

        }
        return false;
    }

    public String toString(){
        String result = "Q = { ";
        for(int i=0; i<this.states.size();i++){
            result += this.states.get(i);
            if(i!=this.states.size()-1)
                result+=",";
            result +=" ";
        }
        result+="}\nE = { ";
        for(int i=0; i<this.alphabet.size();i++){
            result += this.alphabet.get(i);
            if(i!=this.alphabet.size()-1)
                result+=",";
            result +=" ";
        }
        result+="}\nS = { ";
        for(int i = 0; i<this.transitions.size(); i++){
            result += this.transitions.get(i);
            if(i!=this.transitions.size()-1)
                result+=",";
            result +=" ";
        }
        result+="}\nq0 = "+this.initialState+"\nF = { ";
        for(int i=0; i<this.finalStates.size();i++){
            result += this.finalStates.get(i);
            if(i!=this.finalStates.size()-1)
                result+=",";
            result +=" ";
        }
        result += "}";
        return result;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    public List<Pair<Pair<String, String>, String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Pair<Pair<String, String>, String>> transitions) {
        this.transitions = transitions;
    }
}
