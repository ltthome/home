package ch.mykompass.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ch.mykompass.common.TaskName;

public class CustomerTaskListPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('j_id_2v:j_id_3k')";
  }

  public void startTask(String taskName, String availableDate) {
    List<WebElement> customerTaskRows = getCustomerTaskRows();
    for (WebElement customerTaskRow : customerTaskRows) {
      String rowTaskName = customerTaskRow.findElement(By.className("column-task-name")).getText();
      String rowAvailableDate = customerTaskRow.findElement(By.className("column-task-expiry-date")).getText();

      if (rowTaskName.equals(taskName) && rowAvailableDate.equals(availableDate)) {
        customerTaskRow.findElement(By.xpath(".//td[3]//span")).click();
        waitAjaxIndicatorDisappear();
        break;
      }
    }

    handleDialogOfRenewTask(taskName);
  }

  public void startTask(String taskName) {
    List<WebElement> customerTaskRows = getCustomerTaskRows();
    for (WebElement customerTaskRow : customerTaskRows) {
      String rowTaskName = customerTaskRow.findElement(By.className("column-task-name")).getText();

      if (rowTaskName.equals(taskName)) {
        customerTaskRow.findElement(By.xpath(".//td[3]//span")).click();
        break;
      } else {
        System.out.println("This task: " + taskName + "does not exist");
      }

    }

    handleDialogOfRenewTask(taskName);
  }

  public boolean isTaskAvailable(String taskName) {
    boolean taskAvailable = false;
    List<WebElement> customerTaskRows = getCustomerTaskRows();
    for (WebElement customerTaskRow : customerTaskRows) {
      String rowTaskName = customerTaskRow.findElement(By.className("column-task-name")).getText();
      if (rowTaskName.equals(taskName)) {
        taskAvailable = true;
        break;
      }
    }
    return taskAvailable;
  }

  public ContractCreatePage openContractCreate() {
    findElementById("j_id_2v:j_id_3k").click();
    return new ContractCreatePage();
  }

  public void goToCustomerSupportPage() {
    findElementByXpath("//button/span[text()='Allgemeine Betreuung']");
  }

  public void clickCancelButton() {
    findElementByXpath("id('j_id_2v:customer-task-dialog')//button/span[text()='Abbrechen']").click();
    waitForElementDisplayed(By.id("j_id_2v:customer-task-dialog"), false, DEFAULT_TIMEOUT);
  }

  private List<WebElement> getCustomerTaskRows() {
    waitAjaxIndicatorDisappear();
    WebElement customerTaskTable = findElementByXpath("id('j_id_2v:tasks_data')");
    waitForElementDisplayed(By.xpath("id('j_id_2v:tasks_data')/tr[@class!='ui-datatable-empty-message'][last()]"),
        true, DEFAULT_TIMEOUT);
    List<WebElement> taskListRows = customerTaskTable.findElements(By.tagName("tr"));
    return taskListRows;
  }

  private void handleDialogOfRenewTask(String taskName) {
    if (TaskName.RENEW.equals(taskName)) {
      By pickUpButtonIdBy = By.id("j_id_2v:j_id_3s");
      waitForElementPresent(pickUpButtonIdBy, true, DEFAULT_TIMEOUT);
      waitForElementDisplayed(pickUpButtonIdBy, true, DEFAULT_TIMEOUT);
      click(By.id("j_id_2v:j_id_3s"));
    }
  }
}
