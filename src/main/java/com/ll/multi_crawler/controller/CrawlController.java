package com.ll.multi_crawler.controller;

import com.ll.multi_crawler.crawler.NewsCrawler;
import com.ll.multi_crawler.dto.NewsDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrawlController {

    private final NewsCrawler newsCrawler;

    public CrawlController(NewsCrawler newsCrawler) {
        this.newsCrawler = newsCrawler;
    }

    @GetMapping("/crawl/news")
    public List<NewsDto> crawlNews() {
        return newsCrawler.crawl();
    }
}
