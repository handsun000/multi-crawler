package com.ll.multi_crawler.crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class InfiniteScrollCrawler {

    public String crawl(String url) throws InterruptedException, IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "C:\\dev\\chromedriver-win64\\chromedriver.exe");
        } else if (os.contains("mac")) {
            Process process = Runtime.getRuntime().exec("xattr -d com.apple.quarantine drivers/chromedriver_mac");
            process.waitFor();
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_mac");
        } else if (os.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_linux");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        // 이 부분 추가해야 차단당하지 않음 User-Agent 설정 (Selenium은 기본 브라우저 User-Agent 사용하지만 명시 가능)
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114.0.0.0 Safari/537.36");


        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            // 자동화 탐지 요소 숨기기
            js.executeScript(
                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});" +
                            "window.navigator.chrome = {runtime: {}};" +
                            "Object.defineProperty(navigator, 'languages', {get: () => ['en-US', 'en']});" +
                            "Object.defineProperty(navigator, 'plugins', {get: () => [1,2,3,4,5]});"
            );


            long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

            for (int i = 0; i < 10; i++) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

                Thread.sleep(1000 + new Random().nextInt(2000));
                js.executeScript("window.scrollBy(0, window.innerHeight / 2);");
                Thread.sleep(500 + new Random().nextInt(1000));
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

                long newHeight = (long) js.executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    System.out.println("더 이상 로딩할 콘텐츠 없음");
                    break;
                }
                lastHeight = newHeight;
            }

            String pageSource = driver.getPageSource();

            Document doc = Jsoup.parse(pageSource);
            String cleanText = doc.body().text();  // HTML에서 텍스트만 추출

            cleanText = cleanText.replaceAll("\\s+", " ").trim();  // 불필요 공백 제거

            // cleanText를 머신러닝 모델 입력용으로 반환하거나 별도 처리

            return cleanText;
        } finally {
            driver.quit();
        }
    }
}

