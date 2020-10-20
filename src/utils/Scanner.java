package utils;

import hashTable.HashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scanner {
    private final List<Character> specialCharacters = Arrays.asList(',', '?', '!', '.', ';', '_', '\'', '"', '+', '-', '*', '/', '=', '<', '>', '%', '{', '}', '[', ']', '(', ')');
    private List<String> keywords;
    private String string;
    private List<Pair<String, Pair<Integer, Integer>>> PIF;
    private HashTable<String> symbolTable;
    private List<String> error;

    public Scanner(String string, List<String> keywords) {
        this.string = string;
        this.PIF = new ArrayList<>();
        this.symbolTable = new HashTable<>(101);
        this.keywords = keywords;
        this.error = new ArrayList<>();
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<Pair<String, Integer>> parse() {
        List<Pair<String, Integer>> tokens = new ArrayList<>();
        int line = 0;
        List<Pair<String, Integer>> initial = new ArrayList<>();
        String e = "";
        for (int j = 0; j < this.string.length(); j++) {
            if (this.string.charAt(j) != ' ' && !Character.isWhitespace(this.string.charAt(j)))
                e += this.string.charAt(j);
            else if (this.string.charAt(j) == ' ' && e.length() > 0) {
                initial.add(new Pair<>(e, line));
                e = "";
            } else if (Character.isWhitespace(this.string.charAt(j)) && e.length() > 0) {
                initial.add(new Pair<>(e, line));
                line++;
                e = "";
            }
        }

        int s = 0;
        while (s < initial.size()) {
            String element = initial.get(s).element1;
            Integer position = initial.get(s).element2;
            String newString = "";
            for (int i = 0; i < element.length(); i++) {
                if (newString.length() == 0)
                    newString = String.valueOf(element.charAt(i));
                else if (Character.isLetterOrDigit(newString.charAt(newString.length() - 1)) && Character.isLetterOrDigit(element.charAt(i)))
                    newString += String.valueOf(element.charAt(i));
                else if (newString.charAt(newString.length() - 1) == '"' || newString.charAt(newString.length() - 1) == '\'') {
                    newString += String.valueOf(element.charAt(i));
                } else if (Character.isLetterOrDigit(newString.charAt(newString.length() - 1)) && !this.isAValidCharacter(element.charAt(i)))
                    newString += String.valueOf(element.charAt(i));
                else if (element.charAt(i) == '"' || element.charAt(i) == '\'') {
                    newString += String.valueOf(element.charAt(i));
                    tokens.add(new Pair<>(newString, position));
                    newString = "";
                } else if (!Character.isLetterOrDigit(newString.charAt(newString.length() - 1)) && !Character.isLetterOrDigit(element.charAt(i)) && element.charAt(i) == '=') {
                    newString += String.valueOf(element.charAt(i));
                    tokens.add(new Pair<>(newString, position));
                    newString = "";
                }
                else if((newString.charAt(newString.length() - 1)=='+' || newString.charAt(newString.length() - 1)=='-') && Character.isDigit(element.charAt(i)) && !(this.isAValidNumber(tokens.get(tokens.size()-1).element1)) && !(this.isIdentifier(tokens.get(tokens.size()-1).element1)) && !(this.isAVectorAccess(tokens.get(tokens.size()-1).element1))){
                    newString += String.valueOf(element.charAt(i));
                }
                else {
                    tokens.add(new Pair<>(newString, position));
                    newString = String.valueOf(element.charAt(i));
                }

            }
            if (newString.length() > 0)
                tokens.add(new Pair<>(newString, position));
            s++;
        }

        //System.out.println(tokens);
        return tokens;
    }

    public void run() {
        List<Pair<String, Integer>> tokens = this.parse();
        for (Pair<String, Integer> pair : tokens) {
            String element = pair.element1;
            //System.out.println(element);
            if (keywords.contains(element))
                this.PIF.add(new Pair<>(element, new Pair<>(-1, -1)));
            else if (this.isAValidNumber(element) || element.equals("true") || element.equals("false") || this.isAValidCharacter(element) || this.isAValidString(element)) {
                this.symbolTable.add(element);
                this.PIF.add(new Pair<>("constant", this.symbolTable.get(element)));
            }
            else if (!Character.isLetterOrDigit(element.charAt(0)) && element.charAt(0) != '\'' && element.charAt(0) != '"' && specialCharacters.contains(element.charAt(0)))
                this.PIF.add(new Pair<>(element, new Pair<>(-1, -1)));
            else if (this.isIdentifier(element)) {
                this.symbolTable.add(element);
                this.PIF.add(new Pair<>("identifier", this.symbolTable.get(element)));
            } else {
                this.error.add("error at line: " + pair.element2 + ", token: " + element);
                return;
            }
        }
    }


    public boolean isAValidString(String s){
        if(s.length()>=3 && s.charAt(0)=='"' && s.charAt(s.length()-1)=='"' && this.isAValidWord(s))
            return true;
        return false;
    }

    public boolean isAValidCharacter(String s){
        if(s.length()==3 && s.charAt(0)=='\'' && s.charAt(2)=='\'' && this.isAValidCharacter(s.charAt(1)))
            return true;
        return false;
    }

    public boolean isAValidNumber(String s){
        if(s.length()<=1 && !Character.isDigit(s.charAt(0)))
            return false;
        else if(!Character.isDigit(s.charAt(0)) && s.charAt(0)!='-' && s.charAt(0)!='+')
            return false;
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }


    public boolean isAValidWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i)) && !specialCharacters.contains(s.charAt(i)))
                return false;
        }
        return true;
    }

    public boolean isAValidCharacter(char s) {
        if (!Character.isLetterOrDigit(s) && !specialCharacters.contains(s))
            return false;
        return true;
    }

    public boolean isIdentifier(String s) {
        if(s.length()==0)
            return false;
        if (!Character.isLetter(s.charAt(0)))
            return false;
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    public boolean isAVectorAccess(String s){
        if(s.length()<4)
            return false;
        if(s.charAt(s.length()-1)!=']')
            return false;
        String identifier = "";
        int i=0;
        while(i<s.length()-1 && s.charAt(i)!='[')
        {
            identifier += s.charAt(i);
            i++;
        }
        if(!this.isIdentifier(identifier))
            return false;
        String number = "";
        if(i<s.length()-1){
            i++;
            while(i<s.length()-1)
            {
                number += s.charAt(i);
                i++;
            }
        }
        if(!isIdentifier(number) && !isAValidNumber(number))
            return false;
        return true;
    }

    public List<Pair<String, Pair<Integer, Integer>>> getPIF() {
        return PIF;
    }

    public void setPIF(List<Pair<String, Pair<Integer, Integer>>> PIF) {
        this.PIF = PIF;
    }

    public HashTable<String> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(HashTable<String> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
