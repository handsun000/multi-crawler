package com.ll.multi_crawler.crawl.service;

import com.ll.multi_crawler.crawl.crawler.InfiniteScrollCrawler;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
