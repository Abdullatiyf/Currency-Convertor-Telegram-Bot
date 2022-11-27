package com.company.db;

import com.company.entity.Currency;
import com.company.entity.UserCurrency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Database {
    public static List<Currency> currencyList = getCountryList();
    public static String BASE_PATH = "src/main/resources/docs";
    public static Map<Long, UserCurrency> usersList = new HashMap<>();
    public static Map<Long, Boolean> LET_ENTER_AMOUNT = new HashMap<>();


    private static List<Currency> getCountryList() {
        Currency currency = new Currency();
        currency.setId(0);
        currency.setDiff("0.0");
        currency.setCcyNmEN("Uzbek Sum");
        currency.setDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        currency.setCcy("UZS");
        currency.setRate("1");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Currency> currencies = null;
        String format = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        try {
            URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/all/" + format + "/");
            URLConnection urlConnection = url.openConnection();
            Reader reader = new InputStreamReader(urlConnection.getInputStream());
            Type type = new TypeToken<List<Currency>>() {
            }.getType();
            currencies = gson.fromJson(reader, type);
            currencies.add(0, currency);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    public static List<String> countryList = new ArrayList<>(List.of(
            "\uD83C\uDDFA\uD83C\uDDFF", "\uD83C\uDDFA\uD83C\uDDF8", "\uD83C\uDDEA\uD83C\uDDFA", "\uD83C\uDDF7\uD83C\uDDFA", "\uD83C\uDDEC\uD83C\uDDE7",
            "\uD83C\uDDEF\uD83C\uDDF5", "\uD83C\uDDE6\uD83C\uDDFF", "\uD83C\uDDE7\uD83C\uDDE9", "\uD83C\uDDE7\uD83C\uDDEC",
            "\uD83C\uDDE7\uD83C\uDDED", "\uD83C\uDDE7\uD83C\uDDF3", "\uD83C\uDDE7\uD83C\uDDF7", "\uD83C\uDDE7\uD83C\uDDFE",
            "\uD83C\uDDE8\uD83C\uDDE6", "\uD83C\uDDE8\uD83C\uDDED", "\uD83C\uDDE8\uD83C\uDDF3", "\uD83C\uDDE8\uD83C\uDDFA",
            "\uD83C\uDDE8\uD83C\uDDFF", "\uD83C\uDDE9\uD83C\uDDF0", "\uD83C\uDDE9\uD83C\uDDFF", "\uD83C\uDDEA\uD83C\uDDEC",
            "\uD83C\uDDE6\uD83C\uDDEB", "\uD83C\uDDE6\uD83C\uDDF7", "\uD83C\uDDEC\uD83C\uDDEA", "\uD83C\uDDED\uD83C\uDDF0",
            "\uD83C\uDDED\uD83C\uDDFA", "\uD83C\uDDEE\uD83C\uDDE9", "\uD83C\uDDEE\uD83C\uDDF1", "\uD83C\uDDEE\uD83C\uDDF3",
            "\uD83C\uDDEE\uD83C\uDDF6", "\uD83C\uDDEE\uD83C\uDDF7", "\uD83C\uDDEE\uD83C\uDDF8", "\uD83C\uDDEF\uD83C\uDDF4",
            "\uD83C\uDDE6\uD83C\uDDFA", "\uD83C\uDDF0\uD83C\uDDEC", "\uD83C\uDDF0\uD83C\uDDED", "\uD83C\uDDF0\uD83C\uDDF7",
            "\uD83C\uDDF0\uD83C\uDDFC", "\uD83C\uDDF0\uD83C\uDDFF", "\uD83C\uDDF1\uD83C\uDDE6", "\uD83C\uDDF1\uD83C\uDDE7",
            "\uD83C\uDDF1\uD83C\uDDFE", "\uD83C\uDDF2\uD83C\uDDE6", "\uD83C\uDDF2\uD83C\uDDE9", "\uD83C\uDDF2\uD83C\uDDF2",
            "\uD83C\uDDF2\uD83C\uDDF3", "\uD83C\uDDF2\uD83C\uDDFD", "\uD83C\uDDF2\uD83C\uDDFE", "\uD83C\uDDF3\uD83C\uDDF4",
            "\uD83C\uDDF3\uD83C\uDDFF", "\uD83C\uDDF4\uD83C\uDDF2", "\uD83C\uDDF5\uD83C\uDDED", "\uD83C\uDDF5\uD83C\uDDF0",
            "\uD83C\uDDF5\uD83C\uDDF1", "\uD83C\uDDF6\uD83C\uDDE6", "\uD83C\uDDF7\uD83C\uDDF4", "\uD83C\uDDF7\uD83C\uDDF8",
            "\uD83C\uDDE6\uD83C\uDDF2", "\uD83C\uDDF8\uD83C\uDDE6", "\uD83C\uDDF8\uD83C\uDDE9", "\uD83C\uDDF8\uD83C\uDDEA",
            "\uD83C\uDDF8\uD83C\uDDEC", "\uD83C\uDDF8\uD83C\uDDFE", "\uD83C\uDDF9\uD83C\uDDED", "\uD83C\uDDF9\uD83C\uDDEF",
            "\uD83C\uDDF9\uD83C\uDDF2", "\uD83C\uDDF9\uD83C\uDDF3", "\uD83C\uDDF9\uD83C\uDDF7", "\uD83C\uDDFA\uD83C\uDDE6",
            "\uD83C\uDDE6\uD83C\uDDEA", "\uD83C\uDDFA\uD83C\uDDFE", "\uD83C\uDDFB\uD83C\uDDEA", "\uD83C\uDDFB\uD83C\uDDF3"
            , "\uD83C\uDF0E", "\uD83C\uDDFE\uD83C\uDDEA", "\uD83C\uDDFF\uD83C\uDDE6"
    ));
}
