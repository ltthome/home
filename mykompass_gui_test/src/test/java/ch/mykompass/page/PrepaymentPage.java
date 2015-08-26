package ch.mykompass.page;

import org.openqa.selenium.By;

public class PrepaymentPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_32:j_id_37:advance-payment-as-number')";
  }


  public PrepaymentPage setPrepaymentAmmount(String amount) {
    waitForElementDisplayed(By.id("tab-container:j_id_32:j_id_37:advance-payment-as-number"), true, DEFAULT_TIMEOUT);
    findElementById("tab-container:j_id_32:j_id_37:advance-payment-as-number").clear();
    findElementById("tab-container:j_id_32:j_id_37:advance-payment-as-number").sendKeys(amount);
    return this;
  }

  public void proceed() {
    waitAjaxIndicatorDisappear();
    waitForElementDisplayed(By.id("tab-container:j_id_32:j_id_3g:done-button"), true, DEFAULT_TIMEOUT);
    findElementById("tab-container:j_id_32:j_id_3g:done-button").click();
  }
}
