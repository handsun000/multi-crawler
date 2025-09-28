package com.ll.multi_crawler.crawler;

import com.ll.multi_crawler.dto.NewsDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployCrawler {
    public List<NewsDto> crawl() {
        List<NewsDto> newsList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://news.naver.com/").get();
            Elements itemsCnf = doc.select("ul.cnf_news_list");
            Elements itemsCni = doc.select("ul.cni_news_list");

            addList(itemsCnf, newsList);
            addList(itemsCni, newsList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private static void addList(Elements items, List<NewsDto> newsList) {
        for (Element item : items) {
            String title = item.text();
            String url = item.select("a").attr("href");
            String imgUrl = item.select("img").attr("src");

            newsList.add(new NewsDto(title, url, imgUrl, ""));
        }
    }
}
