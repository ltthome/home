package local;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ch.xpertline.base.pages.AbstractPage;

public class LoginPage extends AbstractPage {

  private static final String TEST_USERNAME = "tung.le";
  private static final String TEST_PASSWORD = "Abcd1234";

  @Override
  protected String getLoadedLocator() {
    return "id('login-form')";
  }

  public void login() {
    getUsernameElement().sendKeys(TEST_USERNAME);
    getPasswordElement().sendKeys(TEST_PASSWORD);
    getLoginButtonElement().click();
    waitForElementEnabled(By.id("advanced-search"), true, DEFAULT_TIMEOUT);
  }

  private WebElement getUsernameElement() {
    return findElementById("login-form-username");
  }

  private WebElement getPasswordElement() {
    return findElementById("login-form-password");
  }

  private WebElement getLoginButtonElement() {
    return findElementById("login-form-submit");
  }
}
