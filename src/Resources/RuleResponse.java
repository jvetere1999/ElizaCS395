package Resources;

public class RuleResponse {

    public int[] responseVar;
    public String[] responseFormat;

    public RuleResponse(int[] responseVar, String[] responseFormat){
        this.responseVar = responseVar;
        this.responseFormat = responseFormat;
    }

    @Override
    public String toString(){
        String rtr = responseFormat[0];
        return rtr;
    }
}
