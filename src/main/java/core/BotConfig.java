package core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class BotConfig {
    @Value("${bot.token}")
    private  String BotToken;
    @Value("${bot.name}")
    private  String BotName;

    public String getBotName() {
        return "ActivityDetector_Bot";
    }

    public String getBotTooken() {
        return "7775506535:AAHQ9aT_uwSpNAcikvbSUSAs50a5Gn8IhiU";
    }

    @Override
    public String toString(){
        return BotName+" "+BotToken;
    }
}
