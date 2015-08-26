package local;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ch.mykompass.page.CustomerSearchPage;
import ch.xpertline.base.pages.AbstractPage;

public class TaskPage extends AbstractPage {

  private static final String COLUMN_SEPARATOR = "`";
  private static final String STRING_SEPARATOR = "~";

  public TaskPage() {
    waitForPageLoaded();
  }

  @Override
  protected String getLoadedLocator() {
    return "id('issue_actions_container')";
  }

  public CustomerSearchPage openCustomerSearch() {
    String customerSearchXpath = "id('leftMenuForm:j_id_1l_node_1')/td";
    waitForElementDisplayed(By.xpath(customerSearchXpath), true, DEFAULT_TIMEOUT);
    findElementByXpath(customerSearchXpath).click();
    return new CustomerSearchPage();
  }

  public String exportLoggedTime() throws IOException, ParseException {
    List<WebElement> worklogs = findListElementsByXpath("//*[@class='issue-data-block']");
    StringBuilder outputText = new StringBuilder();
    for (WebElement worklog : worklogs) {
      outputText.append(worklog.findElement(By.xpath(".//*[contains(@id,'worklogauthor')]")).getText());
      outputText.append(COLUMN_SEPARATOR);
      String timestamp = worklog.findElement(By.xpath(".//*[@class='subText']")).getText();
      outputText.append(getDateAsString(timestamp));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(getTimeAsString(timestamp));
      outputText.append(COLUMN_SEPARATOR);
      String duration = worklog.findElement(By.xpath(".//*[@class='worklog-duration']")).getText();
      outputText.append(getDurationInHours(duration));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("summary-val").getText());
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(STRING_SEPARATOR)
          .append(worklog.findElement(By.xpath(".//*[@class='worklog-comment']")).getText()).append(STRING_SEPARATOR);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("parent_issue_summary").getAttribute("title"));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("parent_issue_summary").getAttribute("href"));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("key-val").getAttribute("href"));
      outputText.append("\r\n");
      break;
    }

    return outputText.toString();
  }


  private String getDurationInHours(String duration) {
    if (duration.endsWith("h")) {
      return duration.substring(0, duration.length() - 1);
    } else if (duration.endsWith("m")) {
      String timeInMinutes = duration.substring(0, duration.length() - 1);
      Double timeInHours = Integer.parseInt(timeInMinutes) / 60.0;
      return timeInHours.toString().substring(0, 4);
    } else {
      return duration;
    }
  }

  private String getTimeAsString(String timestamp) {
    String time = timestamp.split(" ")[1] + ":00 " + timestamp.split(" ")[2];
    return time;
  }

  private String getDateAsString(String timestamp) throws ParseException {
    SimpleDateFormat dateFormatOfJIRA = new SimpleDateFormat("dd/MMM/yy");
    Date date = dateFormatOfJIRA.parse(timestamp);
    SimpleDateFormat dateFormatOfCsv = new SimpleDateFormat("MM/dd/yy");
    String dateInCsvFormat = dateFormatOfCsv.format(date);
    return dateInCsvFormat;
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
