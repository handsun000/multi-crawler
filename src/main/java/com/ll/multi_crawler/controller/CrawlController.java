package com.ll.multi_crawler.controller;

import com.ll.multi_crawler.crawler.NewsCrawler;
import com.ll.multi_crawler.dto.NewsDto;
import com.ll.multi_crawler.ml.service.OpenNlpNerService;
import com.ll.multi_crawler.service.EmployCrawlerService;
import com.ll.multi_crawler.service.NewsCrawlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/crawl")
public class CrawlController {

    private final NewsCrawlerService newsCrawlerService;
    private final EmployCrawlerService employCrawlerService;
    private final OpenNlpNerService openNlpNerService;

    public CrawlController(NewsCrawler newsCrawler, NewsCrawlerService newsCrawlerService, EmployCrawlerService employCrawlerService, OpenNlpNerService openNlpNerService) {
        this.newsCrawlerService = newsCrawlerService;
        this.employCrawlerService = employCrawlerService;
        this.openNlpNerService = openNlpNerService;
    }

    @GetMapping("/news")
    public List<NewsDto> crawlNews() {
        return newsCrawlerService.getNews();
    }

    @GetMapping("/employ")
    public String crawlEmploy() throws InterruptedException, IOException {
        String cleanText = employCrawlerService.getInfinite("https://www.wanted.co.kr/wdlist/518?country=kr&job_sort=job.popularity_order&years=-1&locations=all");
        String[] entities = openNlpNerService.findEntities(cleanText);

        return Arrays.toString(entities);
    }
}
