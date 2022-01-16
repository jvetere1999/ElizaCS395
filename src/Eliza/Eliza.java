package Eliza;

import Resources.RuleResponse;
import Rule.RuleMap;

import java.util.ArrayList;
import java.util.Arrays;

public class Eliza {

    private RuleMap rules;
    private ArrayList<String> memory;
    private ArrayList<Integer> memoryAge;
    public Eliza() {
        this.rules = new RuleMap("rules.txt");
        memory = new ArrayList<>();
    }

    public String[] getResponse (String userMessage) {
        String[] splitMsg = userMessage.toLowerCase().split(" ");
        ArrayList<String[]> responses = findAllReponses(splitMsg);
        for(String word: responses.get(0)) System.out.print(word + " ");
        return rules.getValue(responses.get(0));
    }

    private ArrayList<String[]> findAllReponses(String[] splitMsg) {

        ArrayList<String[]> possibleResponseKeys = new ArrayList<>();

        if(Arrays.asList(splitMsg).contains("yes") && Arrays.asList(splitMsg).contains("yes"))
            possibleResponseKeys.add(new String[] {"yes", "no"});


        for (int msgIndex = 0; msgIndex < splitMsg.length; msgIndex++) {

            for (String[] potentialTrigger: rules.keySet()) {
                if ( splitMsg[msgIndex].equals( potentialTrigger[0] ) ){
                    int[] ruleVar = rules.getRuleVar(potentialTrigger);
                    if (ruleVar[0] == 1){
                        possibleResponseKeys.add(potentialTrigger);
                        break;
                    }
                    else if(checkRule(potentialTrigger, splitMsg, msgIndex)) {

                        possibleResponseKeys.add(potentialTrigger);
                    }
                }
            }
        }
        System.out.println("Possible keys found\n" + possibleResponseKeys.size() + " usable keys");
        return possibleResponseKeys;
    }
    private boolean checkRule(String[] potentialTrigger, String[] splitMsg, int msgIndex) {

        RuleResponse current = rules.get(potentialTrigger);

        int[] ruleVar = rules.getRuleVar(potentialTrigger);
        int remainingWords = splitMsg.length - msgIndex + 1;

        if (remainingWords > ruleVar[0]) {
            for (int index = 1; index < (msgIndex + current.responseVar[0]); index++) {
                if (potentialTrigger[index] != splitMsg[index + msgIndex])
                    return false;

            }
            return true;
        }
        return false;
    }
    public void printRules(){
        System.out.println(rules.toString());
    }

}
