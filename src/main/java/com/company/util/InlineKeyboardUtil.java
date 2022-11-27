package com.company.util;

import com.company.db.Database;
import com.company.entity.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardUtil {

    public static ReplyKeyboard getCurrToUser(String data) {
        List<Currency> currencyList = Database.currencyList;
        List<String> countryList = Database.countryList;
        List<List<InlineKeyboardButton>> listForMarkUp = new ArrayList<>();
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        for (int i = 0; i < currencyList.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(countryList.get(i) + " " + currencyList.get(i).getCcy());
            button.setCallbackData(currencyList.get(i).getId() + "/" + data);
            buttonList.add(button);
            if (i % 3 == 0) {
                listForMarkUp.add(buttonList);
                buttonList = new ArrayList<>();
            }
        }
        return new InlineKeyboardMarkup(listForMarkUp);
    }

    public static ReplyKeyboard getFiles() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listForMarkUp = new ArrayList<>();

        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        InlineKeyboardButton buttonPdf = new InlineKeyboardButton();
        InlineKeyboardButton buttonXlsx = new InlineKeyboardButton();
        InlineKeyboardButton buttonWord = new InlineKeyboardButton();

        buttonPdf.setText("PDF");
        buttonPdf.setCallbackData("/pdf");

        buttonXlsx.setText("EXCEL");
        buttonXlsx.setCallbackData("/xlsx");

        buttonWord.setText("Word");
        buttonWord.setCallbackData("/word");

        buttonList.add(buttonPdf);
        buttonList.add(buttonXlsx);
        buttonList.add(buttonWord);

        listForMarkUp.add(buttonList);
        markup.setKeyboard(listForMarkUp);
        return markup;
    }

}
