package com.ll.multi_crawler.service;

import com.ll.multi_crawler.crawler.NewsCrawler;
import com.ll.multi_crawler.dto.NewsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCrawlerService{
    private final NewsCrawler newsCrawler;

    public NewsCrawlerService(NewsCrawler newsCrawler) {
        this.newsCrawler = newsCrawler;
    }


    public List<NewsDto> getNews() {
        return newsCrawler.crawl();
    }
}
