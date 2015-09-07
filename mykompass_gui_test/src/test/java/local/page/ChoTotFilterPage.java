package local.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import ch.xpertline.base.pages.AbstractPage;

public class ChoTotFilterPage extends AbstractPage {

  public ChoTotFilterPage() {
    waitForPageLoaded(10);
  }

  @Override
  protected String getLoadedLocator() {
    return "id('searchbutton')";
  }

  public void configureResultDisplayedAsList() {
    WebElement resultView = findElementByCssSelector(".sort_price.sort_right");
    if (resultView.getAttribute("class").indexOf("sort_inactive") > -1) {
      resultView.click();
    } 
  }
  
  public List<String> getURLForDetailPage() {
    List<String> urls = new ArrayList<>();

    List<WebElement> urlElements = findListElementsByCssSelector(".subject > a");
    for (WebElement urlElement : urlElements) {
      String url = urlElement.getAttribute("href");
      urls.add(url);
    }
    return urls;
  }

}
