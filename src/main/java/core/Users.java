package core;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Users {
    private static List<String> Userlist = new ArrayList<>();
    private File file = new File("C:\\Users\\leoca\\IdeaProjects\\ActivityWriterTelegramBot\\ActivityDetectorTelegramBot\\src\\main\\resources\\users.txt");
    int linescount = 0;
    static String[] userfiles;

    static Path Datafilesdirectorypath = Paths.get("C:\\Users\\leoca\\IdeaProjects\\ActivityWriterTelegramBot\\ActivityDetectorTelegramBot\\src\\main\\resources\\UserData");

    public static boolean OldUser(long chatid){
        if(Userlist.contains(String.valueOf(chatid))){
            return true;
        }
        else {
            return false;
        }
    }

    public void AddUser(long chatid){
        Userlist.add(String.valueOf(chatid));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            for(int i = 0; i<linescount; i++){
            writer.newLine();
            }
            writer.write(String.valueOf(chatid));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public  Users() {
        try {
            Scanner scanner = new Scanner(file);
            if(scanner.hasNextLine()) {
                linescount++;
                while (scanner.hasNextLine()) {
                    Userlist.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
