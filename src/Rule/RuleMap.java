package Rule;

import Resources.RuleResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class RuleMap {

    public HashMap<String, ArrayList<String>> rules;

    private HashMap<String, int[]> ruleVarMap; // [0] KeyLength [1] Value length [2] context length

    private String entryFile;

    public RuleMap(String entryFile){
        this.entryFile = entryFile;

        this.rules = new HashMap<String, ArrayList<String>>();
        this.ruleVarMap = new HashMap<String, int[]>();

        breakInput();
    }

    public RuleResponse get(String key){
        return new RuleResponse( this.ruleVarMap.get(key), this.rules.get(key) );
    }

    public ArrayList<String> getValue(String key) {
        return this.rules.get(key);
    }

    public int[] getRuleVar(String key) {
        return this.ruleVarMap.get(key);
    }

    public Set<String> keySet(){
        return this.rules.keySet();
    }
    public Collection<ArrayList<String>> values(){ return this.rules.values();}

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

        String key = "";
        ArrayList<String> value = new ArrayList<>();

        for (int keyIndex = 0; keyIndex < keyLength; keyIndex++){
            if(keyIndex == keyLength - 1){
                key += text[keyIndex];
            }
            else {
                key += text[keyIndex] + " ";
            }
        }

        for (int valueIndex = 0; valueIndex < valueLength; valueIndex++){
            value.add( valueIndex, text[valueIndex + keyLength]);

        }

        this.rules.put(key, value);
        this.ruleVarMap.put(key, new int[]{keyLength, valueLength, responseLength});
    }

    @Override
    public String toString(){
        String rtr = "";
        for (String key: rules.keySet()) {
            String temp = "";
            temp += key + " ";

            temp += ": ";
            for (String word:rules.get(key)) {
                temp += word + " ";
            }
            rtr += temp + "\n";
        }
        return rtr;
    }
}
