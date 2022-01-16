package Resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Log {
    private ArrayList<String> log;
    private String username;

    public Log(String username){
        this.username = username;
        log = new ArrayList<>();
    }

    /**
     * addToLog: Add chat message to log with set by before
     *
     * @param text
     * The chat message to be written
     *
     * @param isEliza
     * If Eliza sent the message. True: Write eliza, False: Write username
     */
    public void addToLog(String text, Boolean isEliza) {
        String entry = isEliza ? "Eliza\n": username + "\n";
        entry += text;
        log.add(entry);
    }

    /**
     * Creates file if it doesnt exist then writes the messages saved in log with a ASCII line break
     *
     * @param fileName
     * File name in which the log is to be saved
     */
    public void saveLog(String fileName){
        try {
            File logFile = new File(fileName);

            if (logFile.createNewFile()){
                System.out.println("Log created with name "+ fileName);
            }
            else{
                System.out.println("Log with that name already exists. Overwriting");
            }

            FileWriter logChat = new FileWriter(fileName);
            for (String msg: log) {
                logChat.write(msg +"\n____________________________________________________________________________\n");
            }

            logChat.close();
            System.out.println("Logs saved");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
