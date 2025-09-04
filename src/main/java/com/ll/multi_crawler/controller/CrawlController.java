package com.ll.multi_crawler.controller;

import com.ll.multi_crawler.crawler.NewsCrawler;
import com.ll.multi_crawler.dto.NewsDto;
import com.ll.multi_crawler.service.NewsCrawlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrawlController {

    private final NewsCrawlerService newsCrawlerService;

    public CrawlController(NewsCrawler newsCrawler, NewsCrawlerService newsCrawlerService) {
        this.newsCrawlerService = newsCrawlerService;
    }

    @GetMapping("/crawl/news")
    public List<NewsDto> crawlNews() {
        return newsCrawlerService.getNews();
    }
}
