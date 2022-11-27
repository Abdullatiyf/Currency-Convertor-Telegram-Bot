package com.company.service;

public class UserService {
    public static String helper(String userName) {
        return "<b>\uD83D\uDC4BHello </b>" + userName + "\n\n" +
                "<b>Here is the \uD83E\uDDF7list of what this \uD83E\uDD16bot can do\n\n</b>" +
                "<b>1:Info Command sends you latest USD rate to UZS\n\n</b>" +
                "<b>2:Get as File Command lets you get all available 75 Currency rate as PDF,Excel,Word file\n\n</b>" +
                "<b>3:Help as it stands helps you to understand what thi bot does\n\n</b>" +
                "<b>4:Developer by using this command you can get source code of this bot</b>\n\n" +
                "<b>5:CONVERT Command lets you convert to any Currency available in data as much as you want\n\n</b>";
    }
}
