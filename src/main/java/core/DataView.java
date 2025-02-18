package core;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class DataView {
    public static String DayData(String namedirectory, String date){
        String s = "\\";
        String pathname;
        if(!(date == null)) {
            StringBuilder path = new StringBuilder("C:\\Users\\leoca\\IdeaProjects\\ActivityWriterTelegramBot\\ActivityDetectorTelegramBot\\src\\main\\resources\\UserData\\" + String.valueOf(namedirectory) + s);
            if(LocalDate.now().getMonthValue()<10) {
                path.append(LocalDate.now().getYear() + "-" +"0"+LocalDate.now().getMonthValue() + "-");
            } else {
                path.append(LocalDate.now().getYear()+"-"+LocalDate.now().getMonthValue() + "-");
            }
            if(Integer.parseInt(date)<10){
            path.append("0"+ date + ".txt");
            } else path.append(date + ".txt");
            pathname = String.valueOf(path);
        } else {
            pathname = "C:\\Users\\leoca\\IdeaProjects\\ActivityWriterTelegramBot\\ActivityDetectorTelegramBot\\src\\main\\resources\\UserData\\" + String.valueOf(namedirectory) + s + LocalDate.now() + ".txt";
        }
        File file = new File(pathname);
        StringBuilder text = new StringBuilder();
        if(file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                do {
                    text.append(scanner.nextLine());
                    text.append("\n");
                } while (scanner.hasNextLine());
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            text.append("Пока что тут пусто...");
        }
        return String.valueOf(text);
    }
}
