import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;
import logging.LoggingUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import utils.ScreenShotTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {


    private Properties properties = new Properties();
    private static String[] allCommands = {"/start", "/help", "/stop", "/get_screenshot", "/login", "/logout"};
    private UserDao userDao = new UserDaoImpl();


    public Bot() throws FileNotFoundException {
        try {
            properties.load(new FileInputStream("resources/bot_settings.properties"));
            LoggingUtil.logToFile("Loaded properties: " + properties.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        messageProcess(update);
    }

    private void sendMsg(Update update, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendScreen(Update update){
        ScreenShotTool.takeScreenShot();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        sendPhoto.setNewPhoto(new File("resources/screenshots/screen.png"));
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void messageProcess(Update update) {
        String sms = update.getMessage().getText();
        if (sms != null) {
            LoggingUtil.logToFile("Got command" + "(" + new Date().toString() + "): " + sms);
            switch (sms) {
                case "/start":
                    sendMsg(update, "Hi,\"/help\" for all commands ");
                    break;
                case "/help":
                    showAllCommands(update);
                    break;
                case "/get_screenshot":
                    sendScreen(update);
                    break;
                case "/stop":
                    break;
                case "/login":

                    break;
                default:
                    sendMsg(update, "Sorry, not found command like \'" + update.getMessage().getText() + "\'. For help \"/help\"");
                    break;
            }
        }
    }


    private void showAllCommands(Update update) {
        sendMsg(update, "All possible commands");
        StringBuilder commands = new StringBuilder();
        for (String str : allCommands) {
            commands.append(str + "  ");
        }
        sendMsg(update, commands.toString());
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("bot_username", "point_ht_bot");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("bot_token", "");
    }
}
