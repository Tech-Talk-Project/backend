package com.example.backend.board.util;


import org.jsoup.Jsoup;

public class HtmlTextExtractor {

    private HtmlTextExtractor() {}

    public static String extract(String html) {
        return Jsoup.parse(html).text();
    }
}
