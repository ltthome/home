package local.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import local.page.ChoTotDetailPage;
import local.page.ChoTotFilterPage;

import org.junit.AfterClass;
import org.junit.Test;

import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

public class ChoTot {
  private static final String EXPORTED_FILE = "g:\\tmp\\crawler\\cho-tot.csv";
  private static final int NUMBER_OF_PAGES = 2;
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
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder = readData(EXPORTED_FILE);
    browser = Browser.getBrowser();
    browser.launch(BrowserType.FIREFOX, FILTER_URL, "");

    for (int i = 1; i <= NUMBER_OF_PAGES; i++) {
      String url = FILTER_URL + i;
      browser.getDriver().get(url);
      ChoTotFilterPage filterPage = new ChoTotFilterPage();
      List<String> detailPageURLs = filterPage.getURLForDetailPage();
      for (String detailPageURL : detailPageURLs) {
        browser.getDriver().get(detailPageURL);
        ChoTotDetailPage detailPage = new ChoTotDetailPage();
        contentBuilder = detailPage.exportContact(contentBuilder);
      }
    }
    
    writeFile(contentBuilder);
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
