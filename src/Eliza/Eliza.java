package Eliza;

import Resources.RuleResponse;
import Rule.RuleMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Eliza {

    private RuleMap rules;
    private ArrayList<String> memory;
    private ArrayList<Integer> memoryAge;
    public Eliza() {
        this.rules = new RuleMap("rules.txt");
        this.memory = new ArrayList<>();
        this.memoryAge = new ArrayList<>();
    }

    public String getResponse (String userMessage) {
        int rand;

        String[] splitMsg = userMessage.toLowerCase().split(" ");
        ArrayList<ArrayList<String>> responses = findAllReponses(splitMsg);


        if (splitMsg.length <= 4)
            responses.add(this.rules.getValue("short1"));
        if (responses.size() > 10 && this.memory.size() > 0) {
            responses.add(rules.getValue("default1"));
            ArrayList<String> recall1 = rules.getValue("recall1");
            rand = ThreadLocalRandom.current().nextInt(0, memory.size());
            int index = recall1.size() - 1;
            recall1.remove(index);
            recall1.add(memory.get(rand));
            responses.add(recall1);
        }
        else if (responses.size() <= 10 && memory.size() > 0) {
            responses.add(rules.getValue("default2"));
            responses.add(rules.getValue("default3"));
            ArrayList<String> recall1 = rules.getValue("recall2");
            rand = ThreadLocalRandom.current().nextInt(0, memory.size());
            int index = recall1.size() - 1;
            recall1.remove(index);
            recall1.add(memory.get(rand));
            responses.add(recall1);
        }
        else {
            responses.add(rules.getValue("default1"));
            responses.add(rules.getValue("default2"));
            responses.add(rules.getValue("default3"));
        }
         rand =ThreadLocalRandom.current().nextInt(0, responses.size());
        String rtr = "";
        for (String word: responses.get(rand)) {
            rtr += word + " ";
        }

        return rtr;
        //return rules.getValue(temp);
    }

    private ArrayList<ArrayList<String>> findAllReponses(String[] splitMsg) {
        ArrayList<ArrayList<String>> possibleResponse = new ArrayList<>();

        if(Arrays.asList(splitMsg).contains("yes") && Arrays.asList(splitMsg).contains("no")) {
            possibleResponse.add(rules.getValue("yes no"));
        }

        for (int msgIndex = 0; msgIndex < splitMsg.length; msgIndex++) {

            for (String potentialTrigger: rules.keySet()) {

                String[] temp = potentialTrigger.split(" ");
                if ( splitMsg[msgIndex].equals( temp[0] ) ){
                    int[] ruleVar = rules.getRuleVar(potentialTrigger);

                    if (ruleVar[0] == 1){
                        getAddOnText(splitMsg, possibleResponse, msgIndex, potentialTrigger, ruleVar);

                        break;
                    }
                    else if(checkRule(potentialTrigger, splitMsg, msgIndex)) {
                        getAddOnText(splitMsg, possibleResponse, msgIndex, potentialTrigger, ruleVar);
                    }
                }
            }
        }
        return possibleResponse;
    }

    private void getAddOnText(String[] splitMsg, ArrayList<ArrayList<String>> possibleResponse, int msgIndex, String potentialTrigger, int[] ruleVar) {
        ArrayList<String> tempResponse = rules.getValue(potentialTrigger);
        if(ruleVar[2]!=0 && (msgIndex+ruleVar[2]+1) < splitMsg.length){
            int index = tempResponse.size() - 1;
            tempResponse.remove(index);
            String mem = "";
            if(ruleVar[2]>0) {
                for (int i = msgIndex + ruleVar[0]; i < msgIndex +  ruleVar[0] +ruleVar[2]; i++) {
                    tempResponse.add(splitMsg[i]);
                    mem += splitMsg[i] + " ";
                }
            }
            else{
                for (int i = msgIndex ; i > msgIndex + ruleVar[2] ; i--) {
                    tempResponse.add(splitMsg[i]);
                    mem += splitMsg[i] + " ";
                }
            }
            memory.add(mem);
            memoryAge.add(0);
            possibleResponse.add(tempResponse);
        }
        else if (ruleVar[2]>0 && (msgIndex+ruleVar[2]+1) > splitMsg.length){

        }
        else{
            possibleResponse.add(tempResponse);
        }
    }

    private boolean checkRule(String potentialTrigger, String[] splitMsg, int msgIndex) {

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

            return true;
        }

        return false;
    }

    private void manageMemory(){
        for (int index = 0; index < memoryAge.size(); index++){
            memoryAge.add(index, memoryAge.get(index)+1);
        }
    }
    public void printRules(){
        System.out.println(rules.toString());
    }

}
