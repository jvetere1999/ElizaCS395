package Main;

import Eliza.Eliza;
import Resources.Log;

import java.util.Scanner;

public class main {
    public static void main(String[] args){
        boolean runtime = true;
        Scanner scan = new Scanner(System.in);
        String input, elizaOutput, username;
        System.out.println("What is your username?");
        username = scan.nextLine();

        Log log = new Log(username);
        Eliza eliza = new Eliza();

        while(runtime){
            System.out.print(username+ ": ");
            input = scan.nextLine().toLowerCase();
            runtime = !input.equals("exit");
            if (!runtime) break;
            log.addToLog(input, false);
            elizaOutput = eliza.getResponse(input);
            log.addToLog(elizaOutput, true);
            System.out.println("Eliza: "+ elizaOutput);
            System.out.println("____________________________________________________________________________");
        }
        log.saveLog();
    }
}
