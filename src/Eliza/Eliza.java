package Eliza;

import Resources.RuleResponse;
import Rule.RuleMap;

import java.util.ArrayList;
import java.util.Arrays;

public class Eliza {

    private RuleMap rules;
    private ArrayList<String> memory;
    private ArrayList<Integer> memoryAge;
    private ArrayList<Integer> triggerEnd;
    public Eliza() {
        this.rules = new RuleMap("rules2.txt");
        memory = new ArrayList<>();
    }

    public String getResponse (String userMessage) {
        String[] splitMsg = userMessage.toLowerCase().split(" ");
        ArrayList<String[]> responses = findAllReponses(splitMsg);

        String[] key = responses.get(0);
        String temp = "";
        for (int keyIndex = 0; keyIndex < key.length; keyIndex++){
            System.out.println(key[keyIndex]);
            if(keyIndex == key.length - 1){
                temp += key[keyIndex];
            }
            else {
                temp += key[keyIndex] + " ";
            }

        }
        int[] var = rules.getRuleVar(temp);
        int addedText = var[2];
        int startIndex = triggerEnd.get(0);
        String arr = "";
        if(addedText != 0){

            for(int index = 0; index < addedText; index++){
                arr += splitMsg[startIndex + index] + " ";
            }
        }
        return arr;
        //return rules.getValue(temp);
    }

    private ArrayList<String[]> findAllReponses(String[] splitMsg) {
        ArrayList<String[]> possibleResponseKeys = new ArrayList<>();

        if(Arrays.asList(splitMsg).contains("yes") && Arrays.asList(splitMsg).contains("no"))
            possibleResponseKeys.add(new String[] {"yes", "no"});


        for (int msgIndex = 0; msgIndex < splitMsg.length; msgIndex++) {

            for (String potentialTrigger: rules.keySet()) {

                String[] temp = potentialTrigger.split(" ");
                if ( splitMsg[msgIndex].equals( temp[0] ) ){
                    int[] ruleVar = rules.getRuleVar(potentialTrigger);

                    if (ruleVar[0] == 1){
                        possibleResponseKeys.add(temp);
                        break;
                    }
                    else if(checkRule(potentialTrigger, splitMsg, msgIndex)) {

                        possibleResponseKeys.add(temp);
                    }
                }
            }
        }
        System.out.println("Possible keys found\n" + possibleResponseKeys.size() + " usable keys");
        return possibleResponseKeys;
    }
    private boolean checkRule(String potentialTrigger, String[] splitMsg, int msgIndex) {
        System.out.println("CHECKING RULES");
        String[] triggers = potentialTrigger.split(" ");
        RuleResponse current = rules.get(potentialTrigger);

        int[] ruleVar = rules.getRuleVar(potentialTrigger);
        int remainingWords = splitMsg.length - msgIndex + 1;
        int checkIndex = msgIndex + 1;
        if (remainingWords > ruleVar[0]) {
            for (int index = 1; index <  current.responseVar[0]; index++) {
                if (!triggers[index].equals(splitMsg[checkIndex]))
                    return false;
                checkIndex++;
            }
            System.out.println("Response Detected");
            return true;
        }
        triggerEnd.add(checkIndex);
        return false;
    }
    public void printRules(){
        System.out.println(rules.toString());
    }

}
