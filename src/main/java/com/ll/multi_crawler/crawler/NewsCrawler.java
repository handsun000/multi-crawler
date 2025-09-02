package com.ll.multi_crawler.crawler;

import com.ll.multi_crawler.dto.NewsDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsCrawler {
    public List<NewsDto> crawl() {
        List<NewsDto> newsList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://news.naver.com/").get();
            Elements linksCnf = doc.select("ul.cnf_news_list");
            Elements linksCni = doc.select("ul.cni_news_list");

            addList(linksCnf, newsList);
            addList(linksCni, newsList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private static void addList(Elements linksCni, List<NewsDto> newsList) {
        for (var el : linksCni) {
            String title = el.text();
            String url = el.select("a").attr("href");
            String imgUrl = el.select("img").attr("src");

            newsList.add(new NewsDto(title, url, imgUrl, ""));
        }
    }
}
