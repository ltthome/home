package ch.mykompass.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContractReleasePage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_30:j_id_3c_input')";
  }

  public ContractReleasePage setNextPeriodDate(String date) {
    WebElement txtDate = findElementById("tab-container:j_id_30:j_id_3c_input");
    txtDate.click();
    type(By.id("tab-container:j_id_30:j_id_3c_input"), date);
    return this;
  }

}
