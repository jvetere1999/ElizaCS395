package Rule;

import Resources.RuleResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class RuleMap {

    public HashMap<String[], String[]> rules;

    private HashMap<String[], int[]> ruleVarMap; // [0] KeyLength [1] Value length [2] context length

    private String entryFile;

    public RuleMap(String entryFile){
        this.entryFile = entryFile;

        this.rules = new HashMap<String[], String[]>();
        this.ruleVarMap = new HashMap<String[], int[]>();

        breakInput();
    }

    public RuleResponse get(String key[]){
        return new RuleResponse( this.ruleVarMap.get(key), this.rules.get(key) );
    }

    public String[] getValue(String[] key) {
        return this.rules.get(key);
    }

    public int[] getRuleVar(String[] key) {
        return this.ruleVarMap.get(key);
    }

    public Set<String[]> keySet(){
        return this.ruleVarMap.keySet();
    }

    private void breakInput(){
        Scanner fileRead;
        URL url = getClass().getResource(this.entryFile);
        try {
            fileRead = new Scanner(new File(url.getPath()));
        }
        catch (FileNotFoundException e){
            System.out.println("rules.txt not found");
            return;
        }
        while(fileRead.hasNext()){
            String[] text = fileRead.nextLine().split(" ");
            String[] tempLengths = fileRead.nextLine().split(" ");

            int keyLength = Integer.parseInt(tempLengths[0]);
            int valueLength = Integer.parseInt(tempLengths[1]);
            int responseLength = Integer.parseInt(tempLengths[2]);

            addToMap(text, keyLength, valueLength, responseLength);
        }
        System.out.println("Map Loaded.\n" + rules.size() + " rules loaded");
    }

    private void addToMap(String[] text, int keyLength, int valueLength, int responseLength){

        String[] key = new String[keyLength];
        String[] value = new String[valueLength];

        for (int keyIndex = 0; keyIndex < keyLength; keyIndex++){
            key[keyIndex] = text[keyIndex];
        }

        for (int valueIndex = 0; valueIndex < valueLength; valueIndex++){
            value[valueIndex] = text[valueIndex + keyLength];
            System.out.println(value[valueIndex]);
        }


        this.rules.put(key, value);
        this.ruleVarMap.put(key, new int[]{keyLength, valueLength, responseLength});
    }

    @Override
    public String toString(){
        String rtr = "";
        for (String[] key: rules.keySet()) {
            String temp = "";
            for(String word: key){
                temp += word + " ";
            }
            temp += ": ";
            for (String word:rules.get(key)) {
                temp += word + " ";
            }
            rtr += temp + "\n";
        }
        return rtr;
    }
}
