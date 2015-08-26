package ch.mykompass.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends MainPage {

  private static final String TEST_USERNAME = "AM";
  private static final String TEST_PASSWORD = "AM";
  private static final String TEST_LANGUAGE = "German";

  @Override
  protected String getLoadedLocator() {
    return "id('frmLogin')";
  }

  public void login() {
    getUsernameElement().sendKeys(TEST_USERNAME);
    getPasswordElement().sendKeys(TEST_PASSWORD);

    findElementById("frmLogin:languages_label").click();
    WebElement languageList = findElementById("frmLogin:languages_panel");
    List<WebElement> languages = languageList.findElements(By.xpath("//ul/li"));

    for (WebElement eachLanguage : languages) {
      if (eachLanguage.getText().equals(TEST_LANGUAGE)) {
        eachLanguage.click();
      }
    }

    getLoginButtonElement().click();
    waitForElementEnabled(By.id("btnLogout"), true, DEFAULT_TIMEOUT);
  }

  private WebElement getUsernameElement() {
    return findElementById("frmLogin:username");
  }

  private WebElement getPasswordElement() {
    return findElementById("frmLogin:password");
  }

  private WebElement getLoginButtonElement() {
    return findElementById("frmLogin:loginBtn");
  }
}
