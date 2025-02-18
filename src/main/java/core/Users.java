package core;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Users {
    private static List<String> Userlist = new ArrayList<>();
    String path = Paths.get("").toAbsolutePath().toString();
    String p = path+"\\src\\main\\resources\\users.txt";
    private File file = new File(String.valueOf(p));
    int linescount = 0;
    static String[] userfiles;


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
