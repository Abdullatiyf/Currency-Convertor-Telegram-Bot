package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.Currency;
import com.company.entity.UserCurrency;
import com.company.files.WorkWithFiles;
import com.company.service.UserService;
import com.company.util.InlineKeyboardUtil;
import com.company.util.ReplyKeyboardConstant;
import com.company.util.ReplyKeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;


import java.io.File;
import java.text.DecimalFormat;
import java.util.List;


public class MainController {
    public static void handleMessage(Message message) {
        if (message.hasText()) {
            handleText(message);
        } else if (message.hasContact()) {
            handleContact(message);
        } else if (message.hasDocument()) {
            handleDocument(message);
        } else if (message.hasPhoto()) {
            handlePhoto(message);
        }
    }

    private static void handlePhoto(Message message) {
        String chatId = String.valueOf(message.getChatId());
        List<PhotoSize> photoSizes = message.getPhoto();
    }

    private static void handleDocument(Message message) {
        String chatId = String.valueOf(message.getChatId());
        Document document = message.getDocument();
    }

    private static void handleContact(Message message) {
        String chatId = String.valueOf(message.getChatId());
        Contact contact = message.getContact();
    }

    private static void handleText(Message message) {
        Boolean canEnter = Database.LET_ENTER_AMOUNT.get(message.getChatId());
        String chatId = String.valueOf(message.getChatId());
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        User user = message.getFrom();
        if (text.equals("/start") || text.equals(ReplyKeyboardConstant.BACK)) {
            sendMessage.setText("Feel free to use our bot. " + user.getFirstName());
            sendMessage.setReplyMarkup(ReplyKeyboardUtil.getUserMenu());
            sendMessage.setChatId(chatId);
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(ReplyKeyboardConstant.FOR_DEVS)) {
            sendMessage.setText("<b>Here is link for GitHub repository of the bot's source code\n</b>" +
                    "<b>https://github.com/Abdullatiyf/Currency-Convertor-Telegram-Bot</b>");
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(ReplyKeyboardUtil.back());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(ReplyKeyboardConstant.HELP) || text.equals("/help")) {
            String helper = UserService.helper(message.getFrom().getFirstName());
            sendMessage.setText(helper);
            sendMessage.setChatId(chatId);
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setReplyMarkup(ReplyKeyboardUtil.back());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(ReplyKeyboardConstant.RATE_INFO) || text.equals("/info")) {
            String diffText = Database.currencyList.get(0).getDiff().startsWith("-") ? " UZS dropped" :
                    " UZS increased";
            sendMessage.setText("\uD83D\uDCD1 Info:\n\n" +
                    "\uD83D\uDCB4 Currency: " + Database.currencyList.get(1).getCcyNmEN() + "\n" +
                    "\uD83D\uDCCA Rate: " + Database.currencyList.get(1).getRate() + " UZS\n" +
                    "\uD83D\uDCC8 Last change:  " + Database.currencyList.get(1).getDiff() + diffText + "\n" +
                    "\uD83D\uDCC5 Date: " + " as of  " + Database.currencyList.get(1).getDate());
            sendMessage.setReplyMarkup(ReplyKeyboardUtil.back());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(ReplyKeyboardConstant.TO_FILE)) {
            sendMessage.setText("Please choose file type" + "\n" +
                    "if you want to /back just write or click it");
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getFiles());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(ReplyKeyboardConstant.UNIVERSAL)) {
            sendMessage.setText("Please choose first currency" + "\n" +
                    "if you want to /back just write or click it");
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getCurrToUser("from"));
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (canEnter) {
            convert(message);
        }
    }

    public static void handleCallBackQuery(Message message, String data) {
        SendDocument sendDocument = new SendDocument();
        String[] split = data.split("/");
        switch (split[1]) {
            case "from" -> {
                Currency currency = getCurrency(split[0]);
                UserCurrency userCurrency = new UserCurrency();
                userCurrency.setCurrencyFrom(currency);
                Database.usersList.put(message.getChatId(), userCurrency);
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(message.getChatId());
                editMessageText.setMessageId(message.getMessageId());
                editMessageText.setText("<b>Please choose second currency</b>");
                editMessageText.setParseMode(ParseMode.HTML);
                editMessageText.setReplyMarkup((InlineKeyboardMarkup) InlineKeyboardUtil.getCurrToUser("to"));
                ComponentContainer.MY_BOT.sendMsg(editMessageText);
            }
            case "to" -> {
                Currency currency = getCurrency(split[0]);
                UserCurrency userCurrency = Database.usersList.get(message.getChatId());
                userCurrency.setCurrencyTo(currency);
                Database.usersList.put(message.getChatId(), userCurrency);
                UserCurrency userCurrencyNow = Database.usersList.get(message.getChatId());
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setMessageId(message.getMessageId());
                editMessageText.setChatId(message.getChatId());
                editMessageText.setText("<b>You are converting from " + userCurrencyNow.getCurrencyFrom()
                        .getCcyNmEN() + " "
                        + "to " + userCurrencyNow.getCurrencyTo()
                        .getCcyNmEN() + "\n" + "Enter amount</b>");
                editMessageText.setParseMode(ParseMode.HTML);
                ComponentContainer.MY_BOT.sendMsg(editMessageText);
                Database.LET_ENTER_AMOUNT.put(message.getChatId(), true);
            }
            case "pdf" -> {
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(message.getChatId());
                deleteMessage.setMessageId(message.getMessageId());
                ComponentContainer.MY_BOT.sendMsg(deleteMessage);
                File pdf = WorkWithFiles.toPdf();
                sendDocument.setDocument(new InputFile(pdf));
                sendDocument.setCaption("\uD83D\uDCC9Currency Rate Information as PDF file\n" +
                        "\uD83E\uDD16 Source @valyutaOnlineBot");
                sendDocument.setChatId(message.getChatId());
                sendDocument.setParseMode(ParseMode.HTML);
                ComponentContainer.MY_BOT.sendMsg(sendDocument);
            }
            case "xlsx" -> {
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(message.getChatId());
                deleteMessage.setMessageId(message.getMessageId());
                ComponentContainer.MY_BOT.sendMsg(deleteMessage);
                File excel = WorkWithFiles.toExcel();
                sendDocument.setCaption("<b>\uD83D\uDCC9Currency Rate Information as Excel file\n</b>" +
                        "<b>\uD83E\uDD16 Source @valyutaOnlineBot</b>");
                sendDocument.setDocument(new InputFile(excel));
                sendDocument.setChatId(message.getChatId());
                sendDocument.setParseMode(ParseMode.HTML);
                ComponentContainer.MY_BOT.sendMsg(sendDocument);
            }
            case "word" -> {
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(message.getChatId());
                deleteMessage.setMessageId(message.getMessageId());
                ComponentContainer.MY_BOT.sendMsg(deleteMessage);
                File word = WorkWithFiles.toWord();
                sendDocument.setDocument(new InputFile(word));
                sendDocument.setChatId(message.getChatId());
                sendDocument.setCaption("<b>\uD83D\uDCC9Currency Rate Information as Word file\n</b>" +
                        "<b>\uD83E\uDD16 Source @valyutaOnlineBot</b>");
                sendDocument.setParseMode(ParseMode.HTML);
                ComponentContainer.MY_BOT.sendMsg(sendDocument);
            }
        }
    }

    private static void convert(Message message) {
        SendMessage sendMessage = new SendMessage();
        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());
        double amount;
        double rateFrom;
        double rateTo;
        try {
            amount = Double.parseDouble(text);
            if (amount < 0) {
                sendMessage.setText("<b>Minimum entry amount is 1$</b>\n<b>Ty Again!!!</b>");
                sendMessage.setChatId(chatId);
                sendMessage.setParseMode(ParseMode.HTML);
                ComponentContainer.MY_BOT.sendMsg(sendMessage);
            } else {
                UserCurrency userCurrency = Database.usersList.get(Long.parseLong(chatId));
                rateFrom = Double.parseDouble(userCurrency.getCurrencyFrom().getRate());
                rateTo = Double.parseDouble(userCurrency.getCurrencyTo().getRate());
                DecimalFormat df = new DecimalFormat("#.0");
                String format = df.format((amount * rateFrom) / rateTo);
                sendMessage.setText("<b>" + amount + " " + userCurrency.getCurrencyFrom().getCcyNmEN() + " ➡️ " +
                        " " + format + " " + userCurrency.getCurrencyTo().getCcyNmEN() + " </b>");
                sendMessage.setParseMode(ParseMode.HTML);
                sendMessage.setChatId(chatId);
                ComponentContainer.MY_BOT.sendMsg(sendMessage);
                Database.LET_ENTER_AMOUNT.put(message.getChatId(), false);
            }
        } catch (Exception e) {
            sendMessage.setText("Error Try again!!!");
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(ReplyKeyboardUtil.getUserMenu());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        }
    }

    private static Currency getCurrency(String incomeId) {
        int id = Integer.parseInt(incomeId);
        return Database.currencyList.stream()
                .filter(currency -> currency.getId() == (id))
                .findFirst()
                .orElse(null);
    }

}
