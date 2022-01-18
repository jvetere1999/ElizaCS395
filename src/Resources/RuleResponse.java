package Resources;

import java.util.ArrayList;

public class RuleResponse {

    public int[] responseVar;
    public  ArrayList<String> responseFormat;

    public RuleResponse(int[] responseVar, ArrayList<String> responseFormat){
        this.responseVar = responseVar;
        this.responseFormat = responseFormat;
    }

    @Override
    public String toString(){
        String rtr = responseFormat.get(0);
        return rtr;
    }
}
