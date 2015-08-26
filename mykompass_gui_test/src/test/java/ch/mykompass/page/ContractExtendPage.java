package ch.mykompass.page;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContractExtendPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:extend-contract-form:creditworthiness_label')";
  }

  public ContractExtendPage selectCreditworthiness(String creditWorthiness) {
    findElementById("tab-container:extend-contract-form:creditworthiness_label").click();
    WebElement CreditWorthinessList = findElementById("tab-container:extend-contract-form:creditworthiness_panel");
    List<WebElement> creditWorthinesses = CreditWorthinessList.findElements(By.xpath("//ul/li"));

    for (WebElement eachCreditWorthiness : creditWorthinesses) {
      if (eachCreditWorthiness.getText().equals(creditWorthiness)) {
        eachCreditWorthiness.click();
        waitAjaxIndicatorDisappear();
        return this;
      }
    }
    throw new IllegalStateException("Could not find the given Creditworthiness: " + creditWorthiness);
  }

  public ContractExtendPage uploadFile() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("ManualProlongTest_Attachment.txt").getFile());
    findElementByXpath("//input[@type='file']").sendKeys(file.getAbsolutePath());
    waitForElementEnabled(By.xpath("//button[contains(@class, 'ui-fileupload-upload')]"), true, DEFAULT_TIMEOUT);
    findElementByXpath("//button[contains(@class, 'ui-fileupload-upload')]").click();
    waitForElementPresent(By.xpath("//td[@class='ui-fileupload-progress']"), false, DEFAULT_TIMEOUT);
    return this;
  }
}
