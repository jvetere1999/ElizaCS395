package Main;

import Eliza.Eliza;
import Resources.RuleResponse;
import Rule.RuleMap;

public class main {
    public static void main(String[] args){
        RuleMap rule = new RuleMap("rules2.txt");
        String keyRaw = "darn";
        String[] key = keyRaw.split(" ");
        String[] value = rule.getValue(key);
        System.out.println(value[1]);
    }
}
