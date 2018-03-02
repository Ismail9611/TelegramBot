import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.util.logging.Logger;


public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
            Bot.logToFile("Bot initialized");
        } catch (TelegramApiException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
