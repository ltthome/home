package ch.mykompass.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ch.xpertline.base.pages.AbstractPage;

public class MainPage extends AbstractPage {

  public MainPage() {
    waitForPageLoaded();
  }

  @Override
  protected String getLoadedLocator() {
    return "id('leftMenuDiv')";
  }

  public CustomerSearchPage openCustomerSearch() {
    String customerSearchXpath = "id('leftMenuForm:j_id_1l_node_1')/td";
    waitForElementDisplayed(By.xpath(customerSearchXpath), true, DEFAULT_TIMEOUT);
    findElementByXpath(customerSearchXpath).click();
    return new CustomerSearchPage();
  }

  public WebElement getLogoutButton() {
    return findElementById("btnLogout");
  }

  public void waitAjaxIndicatorDisappear() {
    WebElement xportalAjaxIndicator = findElementByClassName("xportalAjaxIndicator");
    if (xportalAjaxIndicator.isDisplayed()) {
      waitForElementDisplayed(xportalAjaxIndicator, false, DEFAULT_TIMEOUT);
    }
  }

  public void proceed() {
    findElementByCssSelector("button[id$='done-button']").click();
  }

}
