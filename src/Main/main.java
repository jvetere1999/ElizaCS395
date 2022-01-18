package Main;

import Eliza.Eliza;
import Resources.RuleResponse;
import Rule.RuleMap;

import java.util.ArrayList;
import java.util.Arrays;

public class main {
    public static void main(String[] args){
        Eliza s = new Eliza();
        System.out.println(s.getResponse("darn yes no i don't ?"));
        s.printRules();


    }
}
