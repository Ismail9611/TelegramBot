import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    private static final Logger LOG = Logger.getLogger(Bot.class.getName());
    private Properties properties = new Properties();
    private String[] allCommands = {"/start", "/help", "/stop"};

    Bot() throws FileNotFoundException {
        try {
            properties.load(new FileInputStream("resources/bot_settings.properties"));
            logToFile("Loaded properties: " + properties.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        messageProcess(message);

    }

    private void sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        System.out.println(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    static void logToFile(String str) {
        try {
            str = str + "\n";
            File filename = new File("resources/logs.txt");
            FileUtils.writeStringToFile(filename, str, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void messageProcess(Message message) {
        String sms = message.getText();
        if (sms != null) {
            switch (sms) {
                case "/help":
                    showAllCommands(message);
                    break;
                case "/start":
                    sendMsg(message, "Hi,\"/help\" for all commands ");
                    break;
                case "/stop":
                    //stop
                    break;
                default:
                    sendMsg(message, "Sorry, not found command like: " + message.getText() + ". For help \"/help\"");
                    break;
            }

        }
    }

    private void showAllCommands(Message message) {
        sendMsg(message, "All possible commands");
        for (String str : allCommands) {
            sendMsg(message, str);
        }
    }


    @Override
    public String getBotUsername() {
        return properties.getProperty("bot_username");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("bot_token");
    }
}
