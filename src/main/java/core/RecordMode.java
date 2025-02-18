package core;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import static core.UpdateInfo.gettedchatid;
@Component
public class RecordMode {
    public static void Record(Update update){
        String foldertoproject = Paths.get("").toAbsolutePath().toString();
        StringBuilder folder = new StringBuilder(foldertoproject);
        folder.append("\\src\\main\\resources\\UserData\\" + String.valueOf(gettedchatid) + "\\" + LocalDate.now() + ".txt");
        File datefile = new File(String.valueOf(folder));
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
