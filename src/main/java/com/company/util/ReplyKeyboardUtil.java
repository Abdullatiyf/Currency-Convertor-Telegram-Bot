package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class ReplyKeyboardUtil {
    public static ReplyKeyboard back() {
        return getMarkUp(getRowList(
                getRow(
                        getButton(ReplyKeyboardConstant.BACK))));
    }

    public static ReplyKeyboard getUserMenu() {
        return getMarkUp(getRowList(
                getRow(
                        getButton(ReplyKeyboardConstant.RATE_INFO),
                        getButton(ReplyKeyboardConstant.UNIVERSAL)
                ),
                getRow(getButton(ReplyKeyboardConstant.TO_FILE),
                        getButton(ReplyKeyboardConstant.HELP))
                , getRow(
                        getButton(ReplyKeyboardConstant.FOR_DEVS)
                )
        ));
    }


    private static ReplyKeyboard getMarkUp(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rowList);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        return markup;
    }

    private static List<KeyboardRow> getRowList(KeyboardRow... rows) {
        return List.of(rows);
    }

    private static KeyboardRow getRow(KeyboardButton... buttons) {
        return new KeyboardRow(List.of(buttons));
    }

    private static KeyboardButton getButton(String demo) {
        return new KeyboardButton(demo);
    }

}
