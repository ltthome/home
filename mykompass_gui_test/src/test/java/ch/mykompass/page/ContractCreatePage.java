package ch.mykompass.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ContractCreatePage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:contract-position-form:add-normal-article-button')";
  }

  public ContractCreatePage setContactPerson(String contactPersonName) {
    findElementById("tab-container:contract-position-form:contact-person-fullname").sendKeys(contactPersonName);
    return this;
  }

  public ContractCreatePage setContractPriority(String priority) {
    WebElement priorityInput = findElementById("tab-container:contract-position-form:priorityDropDown_input");
    priorityInput.sendKeys(priority);
    waitAjaxIndicatorDisappear();
    priorityInput.sendKeys(Keys.ENTER);
    return this;
  }

  public ContractCreatePage setSalesPerson(String salesPerson) {
    WebElement salesPersonInput = findElementById("tab-container:contract-position-form:salesDropDown_input");
    salesPersonInput.sendKeys(salesPerson);
    waitAjaxIndicatorDisappear();
    salesPersonInput.sendKeys(Keys.ENTER);
    return this;
  }

  public ContractCreatePage setContactEmailAddress(String email) {
    findElementByCssSelector("input[id$='contact-email']").sendKeys(email);
    return this;
  }

  public ContractCreatePage addContractPosition(String articleType, String priceType, String prolongType,
      String discountedPercent) {
    clickAddArticleButton();
    setArticleType(articleType);
    setPriceType(priceType);
    setProlongType(prolongType);
    setDiscountedPercent(discountedPercent);
    saveContractPosition();
    return this;
  }

  private void clickAddArticleButton() {
    findElementById("tab-container:contract-position-form:add-normal-article-button").click();
    waitAjaxIndicatorDisappear();
    waitForElementDisplayed(By.cssSelector("input[id$='articleDropDown_input']"), true, DEFAULT_TIMEOUT);
  }

  private void setArticleType(String articleType) {
    WebElement articleDropDown = findElementByCssSelector("span[id$='articleDropDown']");
    WebElement articleDropDownButton = findChildElementByTagName(articleDropDown, "button");
    articleDropDownButton.click();
    waitAjaxIndicatorDisappear();
    List<WebElement> articleTypes = articleDropDown.findElements(By.xpath(".//li"));

    for (WebElement eachArticleType : articleTypes) {
      if (eachArticleType.getText().equals(articleType)) {
        eachArticleType.click();
        waitAjaxIndicatorDisappear();
        return;
      }
    }
  }

  private void setDiscountedPercent(String discountedPercent) {
    if (!discountedPercent.equals("")) {
      waitAjaxIndicatorDisappear();
      waitForElementEnabled(By.cssSelector("input[id$='discountPercentageInput_input']"), true, DEFAULT_TIMEOUT);
      type(By.cssSelector("input[id$='discountPercentageInput_input']"), discountedPercent);
    }
  }

  private void saveContractPosition() {
    findElementByXpath("//button[@title='Save']").click();
    waitAjaxIndicatorDisappear();
  }

  private void setProlongType(String prolongType) {
    WebElement prolongDropdown = findElementByCssSelector("div[id$='renewalStateDropDown']");
    prolongDropdown.findElement(By.tagName("label")).click();
    List<WebElement> prolongTypes = prolongDropdown.findElements(By.xpath(".//li"));

    for (WebElement eachProlongType : prolongTypes) {
      if (eachProlongType.getText().equals(prolongType)) {
        eachProlongType.click();
        waitAjaxIndicatorDisappear();
        return;
      }
    }
  }

  private void setPriceType(String priceType) {
    WebElement priceTypeDropdown = findElementByCssSelector("div[id$= 'priceTypeDropDown']");
    priceTypeDropdown.findElement(By.tagName("label")).click();
    List<WebElement> priceTypes = priceTypeDropdown.findElements(By.xpath(".//li"));

    for (WebElement eachPriceType : priceTypes) {
      if (eachPriceType.getText().equals(priceType)) {
        eachPriceType.click();
        waitAjaxIndicatorDisappear();
        return;
      }
    }
  }
}
