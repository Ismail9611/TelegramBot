import logging.LoggingUtil;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {


    private Properties properties = new Properties();
    private String[] allCommands = {"/start", "/help", "/stop", "/get_screenshot"};

    Bot() throws FileNotFoundException {
        try {
            properties.load(new FileInputStream("resources/bot_settings.properties"));
            LoggingUtil.logToFile("Loaded properties: " + properties.toString());
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
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendScreen(Message message){
        ScreenShotTool.takeScreenShot();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setNewPhoto(new File("resources/screenshots/screen.png"));
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void messageProcess(Message message) {
        String sms = message.getText();
        if (sms != null) {
            LoggingUtil.logToFile("Got command" + "(" + new Date().toString() + "): " + sms);
            switch (sms) {
                case "/start":
                    sendMsg(message, "Hi,\"/help\" for all commands ");
                    break;
                case "/help":
                    showAllCommands(message);
                    break;
                case "/get_screenshot":
                    sendScreen(message);
                    break;
                case "/stop":
                    //stop
                    break;
                default:
                    sendMsg(message, "Sorry, not found command like \'" + message.getText() + "\'. For help \"/help\"");
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
        return properties.getProperty("bot_username", "point_ht_bot");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("bot_token", "");
    }
}
