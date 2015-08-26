package local;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;
import ch.xpertline.base.pages.AbstractPage;

public class FilterTaskPage extends AbstractPage {

  private static final String COLUMN_SEPARATOR = "`";
  private static final String STRING_SEPARATOR = "~";

  private static final Browser browser = Browser.getBrowser();

  public FilterTaskPage() {
    waitForPageLoaded();
  }

  @Override
  protected String getLoadedLocator() {
    // return "id('advanced-search')";
    return "id('bulkedit')";
  }

  public List<String> getIssueKeys() {
    // browser.launch(BrowserType.FIREFOX, "http://swbntsrv24.soreco.wan/jira/issues/?filter=22700", "");
    List<WebElement> tasks = findListElementsByXpath("//*[@class='summary']//a[2]");
    List<String> issueKeys = new ArrayList<String>();
    for (WebElement task : tasks) {
      issueKeys.add(task.getAttribute("data-issue-key"));
    }
    return issueKeys;
  }

  public void exportLoggedTime(String issueKey) throws Exception {
    String issueURL =
        "http://swbntsrv24.soreco.wan/jira/browse/" + issueKey
            + "?page=com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel";
    browser.launch(BrowserType.FIREFOX, issueURL, "");

    List<WebElement> worklogs = findListElementsByXpath("//*[@class='issue-data-block']");
    StringBuilder outputText = new StringBuilder();
    for (WebElement worklog : worklogs) {
      outputText.append(worklog.findElement(By.xpath(".//*[contains(@id,'worklogauthor')]")).getText());
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(worklog.findElement(By.xpath(".//*[@class='subText']")).getText());
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(worklog.findElement(By.xpath(".//*[@class='worklog-duration']")).getText());
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(STRING_SEPARATOR)
          .append(worklog.findElement(By.xpath(".//*[@class='worklog-comment']")).getText()).append(STRING_SEPARATOR);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("parent_issue_summary").getAttribute("title"));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("parent_issue_summary").getAttribute("href"));
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("summary-val").getText());
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(findElementById("key-val").getAttribute("href"));
      outputText.append("\r\n");
    }

    Path exportedFile = Paths.get("d:\\tmp\\time.csv");
    Files.write(exportedFile, outputText.toString().getBytes());

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
