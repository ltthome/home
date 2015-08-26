package local.page;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

import ch.xpertline.base.pages.AbstractPage;

public class DiaOcOnlinePage extends AbstractPage {

  private static final String COLUMN_SEPARATOR = "`";
  private static final String STRING_SEPARATOR = "~";

  public DiaOcOnlinePage() {
    waitForPageLoaded();
  }

  @Override
  protected String getLoadedLocator() {
    return "id('result_block')";
  }

  public StringBuilder exportContact(StringBuilder outputText) throws IOException, ParseException {
    List<WebElement> contactInfos = findListElementsByClassName("contact_info");
    for (WebElement contactInfo : contactInfos) {
      String[] infoParts = contactInfo.getText().split("\n");
      String phone = getPhoneNumber(infoParts[2]);
      if (outputText.indexOf(phone) == -1) {
        outputText.append(infoParts[1]);
        outputText.append(COLUMN_SEPARATOR);
        outputText.append(STRING_SEPARATOR);
        outputText.append(phone);
        outputText.append(STRING_SEPARATOR);
        outputText.append(COLUMN_SEPARATOR);
        outputText.append(new SimpleDateFormat("MM/dd/yy").format(new Date()));
        outputText.append(COLUMN_SEPARATOR);
        outputText.append(getDriver().getCurrentUrl());
        outputText.append("\r\n");
      }
    }

    return outputText;
  }

  private String getPhoneNumber(String phoneInfo) {
    return phoneInfo.replaceAll(" ", "").replaceAll("\\.", "");
  }


}
