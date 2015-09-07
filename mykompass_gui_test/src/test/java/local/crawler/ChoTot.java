package local.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import local.page.ChoTotDetailPage;
import local.page.ChoTotFilterPage;

import org.junit.AfterClass;
import org.junit.Test;

import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

public class ChoTot {
  private static final int LIMIT_PAGES_NOT_CRASH_BROWSER = 5;

  private static final String EXPORTED_FILE = "G:\\data\\dropbox\\Dropbox\\storage\\contact-data\\contact.csv";

  private static final int NUMBER_OF_PAGES = 150;
  private static final int FROM_PAGE = 1;
  private static final String FILTER_URL = "http://www.chotot.vn/tp-ho-chi-minh/mua-ban?f=p&o=";

  private static Browser browser;

  @AfterClass
  public static void terminate() {
    Browser.getBrowser().shutdown();
  }

  public static Browser getBrowser() {
    return browser;
  }

  @Test
  public void crawl() throws Exception {
    System.out.println(new Date());
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder = readData(EXPORTED_FILE);
    int originalLengthOfBuilder = contentBuilder.length();
    final StringBuilder outputContent = new StringBuilder(contentBuilder);
    browser = Browser.getBrowser();
    browser.launch(BrowserType.FIREFOX, FILTER_URL, "");

    for (int i = FROM_PAGE; i < FROM_PAGE + NUMBER_OF_PAGES; i++) {
      if (i % LIMIT_PAGES_NOT_CRASH_BROWSER == 0 || i == FROM_PAGE) {
        restartBrowser();
        configureResultDisplayedAsList();
      }

      String filterPageURL = FILTER_URL + i;
      List<String> detailPageURLs = getDetailPageURLs(filterPageURL);

      for (String detailPageURL : detailPageURLs) {
        browser.getDriver().get(detailPageURL);
        ChoTotDetailPage detailPage = new ChoTotDetailPage();
        contentBuilder = detailPage.exportContact(contentBuilder);
      }
    }

    outputContent.insert(0, contentBuilder.substring(originalLengthOfBuilder));
    writeFile(outputContent);
    System.out.println(new Date());
  }

  private List<String> getDetailPageURLs(String filterPageURL) {
    browser.getDriver().get(filterPageURL);
    ChoTotFilterPage filterPage = new ChoTotFilterPage();
    List<String> detailPageURLs = filterPage.getURLForDetailPage();
    return detailPageURLs;
  }

  private void configureResultDisplayedAsList() throws Exception {
    browser.launch(BrowserType.FIREFOX, FILTER_URL + 1, "");
    ChoTotFilterPage filterPage = new ChoTotFilterPage();
    filterPage.configureResultDisplayedAsList();
  }

  private void restartBrowser() {
    browser.shutdown();
    browser = Browser.getBrowser();
  }

  private void writeFile(StringBuilder contentBuilder) throws Exception {
    Path exportedFile = Paths.get(EXPORTED_FILE);
    try (BufferedWriter writer = Files.newBufferedWriter(exportedFile)) {
      writer.write(contentBuilder.toString());
    } catch (Exception e) {
      throw e;
    }
  }

  public StringBuilder readData(String filePath) {
    StringBuilder contentBuilder = new StringBuilder();
    Path path = Paths.get(filePath);
    byte[] bytes;
    try {
      bytes = Files.readAllBytes(path);
    } catch (IOException e) {
      e.printStackTrace();
      return contentBuilder;
    }
    String fileAsString = new String(bytes, StandardCharsets.UTF_8);
    contentBuilder.append(fileAsString);
    return contentBuilder;
  }
}
