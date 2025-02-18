package core;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static core.DataView.DayData;
import static core.RecordMode.Record;
import static core.UpdateInfo.*;
import static core.Users.OldUser;

public class TelegramBot extends TelegramLongPollingBot {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
    //......................................................получение компонентов через спринг
    BotConfig botConfig = context.getBean("botConfig", BotConfig.class);
    UpdateInfo updateInfo = context.getBean("updateInfo", UpdateInfo.class);
    Users users = new Users();
    private static Map<Long, BotState> UsersBotStates = new ConcurrentHashMap<Long, BotState>();

    public enum BotState{
        Default, Record, Check;
    }
    @Override
    public void onUpdateReceived(Update update) {
        updateInfo.setUpdate(update);
        System.out.println(LocalTime.now()+" "+updateInfo.toString());
        //...................................................record mode................................................................................
        if(update.hasMessage() & UsersBotStates.containsKey(gettedchatid)) {
            if (UsersBotStates.get(gettedchatid).equals(BotState.Record)){
                Record(update);
            }
            if (UsersBotStates.get(gettedchatid).equals(BotState.Check)){
                sendSimpleMessage(gettedchatid, "Вы перешли в режим просмотра журнала записей.", "MainMenuKey");
                sendSimpleMessage(gettedchatid, DayData(gettedchatidString, update.getMessage().getText()), "");
                UsersBotStates.remove(gettedchatid);
            }
        }
        //................................................CallbackData
        if(update.hasCallbackQuery() & OldUser(gettedchatid)){
            switch (callbackdata){
                case "record":
                    UsersBotStates.remove(gettedchatid);
                    UsersBotStates.put(gettedchatid, BotState.Record);
                    sendSimpleMessage(gettedchatid, "Вы перешли в режим записи,\n" +
                            "теперь отправляя сообщение боту вы будете делать запись в журнале действий.\n\n " +
                            "Для выхода из режима записи, нажмите на кнопку снизу вернувшись в главное меню.", "MainMenuKey");
                    break;
                case "check":
                    UsersBotStates.remove(gettedchatid);
                    UsersBotStates.put(gettedchatid, BotState.Check);
                    sendSimpleMessage(gettedchatid, "Введите номер числа активность за которое желаете получить:","");
                    break;
                case "Default":
                    UsersBotStates.remove(gettedchatid);
                    UsersBotStates.put(gettedchatid, BotState.Default);
                    break;
            }
        }
        //................................................/start
        else if(gettedmessage.equals("/start")){
            if(OldUser(gettedchatid)){
                sendSimpleMessage(gettedchatid, "Вы уже зарегистрированы.", "MainMenuKey");
            } else{
                String pathname = "C:\\Users\\leoca\\OneDrive\\Рабочий стол\\ActivityDetectorTelegramBot\\src\\main\\resources\\UserData\\"+String.valueOf(gettedchatid);
                File file = new File(pathname);
                file.mkdirs();
                users.AddUser(gettedchatid);
                UsersBotStates.put(gettedchatid, BotState.Default);
                sendSimpleMessage(gettedchatid, "Вы успешно зарегистрированы.", "MainMenuKey");

            }
        }
        else if (update.hasMessage() & update.getMessage().hasText() & !OldUser(gettedchatid)){
            sendSimpleMessage(gettedchatid, "Вы не зарегистрированы в системе, для регистрации введите '/start'.", "");
        }
        //................................................switch
        else if(update.hasMessage() & OldUser(gettedchatid)){
            switch (gettedmessage){
                case "Главное меню":
                    UsersBotStates.remove(gettedchatid);
                    UsersBotStates.put(gettedchatid, BotState.Default);
                    sendMainmenu(gettedchatid);
                    break;
                case "/help":
                    sendSimpleMessage(gettedchatid, "Для вызова главного меню напишите: \n 'Главное меню'.", "MainMenuKey");
                    break;
            }
        }

    }

    public void sendSimpleMessage(long chatid, String text, String keyboard) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatid);
        if(keyboard.equals("MainMenuKey")){
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> rowsinline = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton b1 = new KeyboardButton("Главное меню");
            row1.add(b1);
            rowsinline.add(row1);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            replyKeyboardMarkup.setKeyboard(rowsinline);
            message.setReplyMarkup(replyKeyboardMarkup);
        }
        try {
            execute(message);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
    public void sendMainmenu(long chatid) {
        SendMessage message = new SendMessage();
        message.setText("\uD83C\uDF0D Главное меню. "+"\n"+" "+"\n"+"\uD83D\uDCF1 Добро пожаловать в главное меню нашего бота," +
                " здесь вы можете отследить ваши действия или перейти в режим записи.");
        message.setChatId(chatid);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lines = new ArrayList<>();
        List<InlineKeyboardButton> line1 = new ArrayList<>();
        InlineKeyboardButton b1 = new InlineKeyboardButton("Режим записи");
        b1.setCallbackData("record");
        InlineKeyboardButton b2 = new InlineKeyboardButton("Просмотр активности");
        b2.setCallbackData("check");
        line1.add(b1);
        line1.add(b2);
        lines.add(line1);
        inlineKeyboardMarkup.setKeyboard(lines);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken(){
        return botConfig.getBotTooken();
    }





}
