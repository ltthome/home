package ch.mykompass.page;

import static org.junit.Assert.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CustomerSearchPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('j_id_2v:searchText')";
  }

  public CustomerTaskListPage openCustomerTaskList(String customerName) {
    searchCustomer(customerName);
    List<WebElement> customerRows =
        getCustomerTableElement().findElements(By.xpath("tr[@class!='ui-datatable-empty-message']"));

    for (WebElement customerRow : customerRows) {
      if (customerRow.getText().contains(customerName)) {
        customerRow.findElement(By.xpath(".//td[2]")).click();
        waitForElementPresent(By.id("j_id_2v:customer-task-dialog"), true, DEFAULT_TIMEOUT);
        waitAjaxIndicatorDisappear();
        return new CustomerTaskListPage();
      }
    }

    fail("Could not open customer task list with given customer name: " + customerName);
    return null;
  }

  private void searchCustomer(String searchKey) {
    findElementById("j_id_2v:searchText").sendKeys(searchKey);
    clickSearchButton();
    waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
  }

  private void clickSearchButton() {
    findElementByXpath("//div[@class = 'contentArea']//button[@type='submit']").click();
  }

  private WebElement getCustomerTableElement() {
    return findElementById("j_id_2v:customers_data");
  }
}
