package com.ll.multi_crawler.service;

import com.ll.multi_crawler.crawler.InfiniteScrollCrawler;
import com.ll.multi_crawler.crawler.NewsCrawler;
import com.ll.multi_crawler.dto.NewsDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployCrawlerService {
    private final InfiniteScrollCrawler infiniteScrollCrawler;

    public EmployCrawlerService(InfiniteScrollCrawler infiniteScrollCrawler) {
        this.infiniteScrollCrawler = infiniteScrollCrawler;
    }


    public String getInfinite(String url) throws InterruptedException, IOException {
        return infiniteScrollCrawler.crawl(url);
    }
}
