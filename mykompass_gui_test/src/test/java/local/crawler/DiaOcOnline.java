package local.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import local.page.DiaOcOnlinePage;

import org.junit.AfterClass;
import org.junit.Test;

import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

public class DiaOcOnline {
  private static final String EXPORTED_FILE = "g:\\tmp\\crawler\\dia-oc-online.csv";
  private static final int NUMBER_OF_PAGES = 5;
  private static final String CHINH_CHU_URL = "http://diaoconline.vn/sieu-thi/loc?tp=3&chinhchu=1&pi=";
  private static final String NHA_SV_URL = "http://diaoconline.vn/sieu-thi/loc/?tp=3&nhasv=1&pi=";

  private static Browser browser;

  // @Rule
  // public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

  // @Before
  // public void setup() throws Exception {
  // browser = Browser.getBrowser();
  // browser.launch(BrowserType.FIREFOX, getProcessStartLink("xportal/13D1EC51435A0179/start.ivp"), "");
  // LoginPage loginPage = new LoginPage();
  // loginPage.login();
  // }

  @AfterClass
  public static void terminate() {
    Browser.getBrowser().shutdown();
  }

  public static Browser getBrowser() {
    return browser;
  }

  @Test
  public void crawl() throws Exception {
    browser = Browser.getBrowser();
    browser.launch(BrowserType.FIREFOX, CHINH_CHU_URL + 1, "");
    StringBuilder contentBuilder = readData(EXPORTED_FILE);
    for (int i = 1; i <= NUMBER_OF_PAGES; i++) {
      String url = CHINH_CHU_URL + i;
      browser.getDriver().get(url);
      DiaOcOnlinePage diaOcOnlinePage = new DiaOcOnlinePage();
      contentBuilder = diaOcOnlinePage.exportContact(contentBuilder);
    }

    for (int i = 1; i <= NUMBER_OF_PAGES; i++) {
      String url = NHA_SV_URL + i;
      browser.getDriver().get(url);
      DiaOcOnlinePage diaOcOnlinePage = new DiaOcOnlinePage();
      contentBuilder = diaOcOnlinePage.exportContact(contentBuilder);
    }

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
