package core;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import static core.UpdateInfo.gettedchatid;
@Component
public class RecordMode {
    public static void Record(Update update){
        String s = "\\";
        String pathname = "C:\\Users\\leoca\\IdeaProjects\\ActivityWriterTelegramBot\\ActivityDetectorTelegramBot\\src\\main\\resources\\UserData\\" + String.valueOf(gettedchatid) + s + LocalDate.now() + ".txt";
        File datefile = new File(pathname);
                if (!datefile.exists()) {
            try {
                datefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(datefile, true));
            StringBuilder text = new StringBuilder(LocalTime.now() + "* " + update.getMessage().getText());
            int x = text.indexOf("*");
            text.delete(6,x+1);
            writer.newLine();
            if(!update.getMessage().getText().equals("Главное меню")) {
                writer.write(String.valueOf(text));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
