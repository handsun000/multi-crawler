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
            Elements links = doc.select("ul.cnf_news_list a");

            for (var el : links) {
                String title = el.text();
                String url = el.absUrl("href");
                String imgUrl = el.absUrl("src");

                newsList.add(new NewsDto(title, url, imgUrl, ""));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
