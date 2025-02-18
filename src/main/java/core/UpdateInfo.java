package core;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class UpdateInfo {
    public static Long gettedchatid = null;
    public static String name;
    public static String gettedmessage;
    public static String callbackdata;
    public static String gettedchatidString;
    public void setUpdate (Update update){
        if(update.hasCallbackQuery()){
            name = update.getCallbackQuery().getMessage().getChat().getUserName();
            gettedchatid = update.getCallbackQuery().getMessage().getChatId();
            gettedmessage = null;
            callbackdata = update.getCallbackQuery().getData();
            gettedchatidString = String.valueOf(gettedchatid);
        } else {
            name = update.getMessage().getChat().getUserName();
            gettedchatid = update.getMessage().getChatId();
            gettedmessage = update.getMessage().getText();
            gettedchatidString = String.valueOf(gettedchatid);
        }
    }
    public String toString(){
        return name+" "+gettedchatid+" "+gettedmessage+" "+callbackdata;
    }
}
