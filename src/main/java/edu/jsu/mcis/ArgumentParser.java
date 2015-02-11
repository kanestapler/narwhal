package edu.jsu.mcis;

import java.util.*;


public class ArgumentParser {
    
    private static Map<String, Integer> myArgs = new HashMap<>();
    private static LinkedList myNames = new LinkedList();
    
    public void addArg(String s, int i) {
        myArgs.put(s, i);
    }
    
    public int getValue(String s) {
        return myArgs.get(s);
    }
        
}

