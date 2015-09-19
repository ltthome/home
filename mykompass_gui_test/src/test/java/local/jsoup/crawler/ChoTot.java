package local.jsoup.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import local.jsoup.page.ChoTotDetailPage;
import local.page.ChoTotFilterPage;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

public class ChoTot {
  private static final String EXPORTED_FILE_NAME = "contact";
  // private static final String EXPORTED_FILE_NAME = "contact-tran";
  private static final String EXPORTED_FILE = "G:\\data\\dropbox\\Dropbox\\storage\\contact-data\\"
      + EXPORTED_FILE_NAME + ".csv";
  private static final String TMP_FILE = "G:\\data\\dropbox\\Dropbox\\storage\\contact-data\\" + EXPORTED_FILE_NAME
      + "%s.csv";

  private static final int NUMBER_OF_PAGES = 180;// 176
  private static final int FROM_PAGE = 1; // include 16/9 18h17 100
  public static final String FILTER_URL = "http://www.chotot.vn/tp-ho-chi-minh/mua-ban?f=p&o="; // ca nhan
  // public static final String FILTER_URL = "http://www.chotot.vn/tp-ho-chi-minh/mua-ban?f=c&o="; // cong ty
  // public static final String FILTER_URL = "http://www.chotot.vn/tp-ho-chi-minh/mua-ban-nha-dat?f=p&o="; // nha dat

  private static Browser browser;

  @AfterClass
  public static void terminate() {
    Browser.getBrowser().shutdown();
  }

  @Test
  public void crawl() throws Exception {
    System.out.println(new Date());
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder = readData(EXPORTED_FILE);
    int originalLengthOfBuilder = contentBuilder.length();
    final StringBuilder outputContent = new StringBuilder(contentBuilder);
    browser = Browser.getBrowser();
    configureResultDisplayedAsList();
    List<String> detailPageURLs = new ArrayList<>();

    for (int i = FROM_PAGE; i < FROM_PAGE + NUMBER_OF_PAGES; i++) {
      try {
        String filterPageURL = FILTER_URL + i;
        detailPageURLs.addAll(getDetailPageURLs(filterPageURL));
      } catch (Exception e) {
        System.out.println("Error when get filter page number " + i);
        e.printStackTrace();
        recoverWhenError();
      }
    }

    System.out.println("Finished getting URL: " + new Date());

    for (String detailPageURL : detailPageURLs) {
      try {
        ChoTotDetailPage detailPage = new ChoTotDetailPage(detailPageURL);
        contentBuilder = detailPage.exportContact(contentBuilder);
      } catch (Exception e) {
        System.out.println("Error when get detail page " + detailPageURL);
        System.out.println("Number of requests: " + ChoTotDetailPage.REQUEST_COUNTER + " / Number of new contacts: "
            + ChoTotDetailPage.NEW_CONTACT_COUNTER);
        writeFile(contentBuilder, String.format(TMP_FILE, new Date().getTime()));
      }
    }

    outputContent.insert(0, contentBuilder.substring(originalLengthOfBuilder));
    writeFile(outputContent);
    System.out.println("Number of requests: " + ChoTotDetailPage.REQUEST_COUNTER);
    System.out.println("Number of new contacts: " + ChoTotDetailPage.NEW_CONTACT_COUNTER);
    System.out.println(new Date());
  }

  private void recoverWhenError() throws IOException, Exception {
    System.out.println("Recover when error");
    Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
    restartBrowser();
    configureResultDisplayedAsList();
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
    WebElement html = browser.getDriver().findElement(By.tagName("html"));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
  }

  private void restartBrowser() {
    browser.shutdown();
    browser = Browser.getBrowser();
  }

  private void writeFile(StringBuilder contentBuilder, String filePath) throws Exception {
    Path exportedFile = Paths.get(filePath);
    try (BufferedWriter writer = Files.newBufferedWriter(exportedFile)) {
      writer.write(contentBuilder.toString());
    } catch (Exception e) {
      throw e;
    }
  }

  private void writeFile(StringBuilder contentBuilder) throws Exception {
    writeFile(contentBuilder, EXPORTED_FILE);
  }

  private StringBuilder readData(String filePath) {
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

  public static void main(String[] args) throws Exception {
    new ChoTot().crawl();
    terminate();
  }
}
