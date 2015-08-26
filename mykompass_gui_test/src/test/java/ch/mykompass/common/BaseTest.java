package ch.mykompass.common;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;

import ch.mykompass.page.LoginPage;
import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

import com.thoughtworks.selenium.SeleneseTestBase;

public class BaseTest extends SeleneseTestBase {
  private static Browser browser;

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

  @Before
  public void setup() throws Exception {
    browser = Browser.getBrowser();
    browser.launch(BrowserType.FIREFOX, getProcessStartLink("xportal/13D1EC51435A0179/start.ivp"), "");
    LoginPage loginPage = new LoginPage();
    loginPage.login();
  }


  @AfterClass
  public static void terminate() {
    Browser.getBrowser().shutdown();
  }

  public static String getProcessStartLink(String relativeIvpPath) {
    return "http://" + PropsUtils.getServerName() + ":" + PropsUtils.getIvyEnginePort() + "/ivy/" + "pro/"
        + PropsUtils.getApplicationName() + "/" + relativeIvpPath;
  }

  public static Browser getBrowser() {
    return browser;
  }
}
