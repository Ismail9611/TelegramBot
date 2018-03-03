import logging.LoggingUtil;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.io.FileNotFoundException;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
            LoggingUtil.logToFile("Bot initialized(" + new Date() + ")");
        } catch (TelegramApiException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
