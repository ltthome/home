package local.page;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.xpertline.base.pages.AbstractPage;

public class ChoTotDetailPage extends AbstractPage {

  private static final String COLUMN_SEPARATOR = "`";
  private static final String STRING_SEPARATOR = "~";

  public ChoTotDetailPage() {
    waitForPageLoaded();
  }

  @Override
  protected String getLoadedLocator() {
    return "id('hidden-phone-b')";
  }

  public StringBuilder exportContact(StringBuilder outputText) throws IOException, ParseException {
    findElementById("hidden-phone-b").click();
    String phoneURL = findElementById("real-phone").getAttribute("src");
    String phone = ImageToNumberProcessor.getNumber(phoneURL);

    String name = findElementByClassName("advertised_user").getText();
    if (outputText.indexOf(phone) == -1) {
      outputText.append(name);
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
    return outputText;
  }

}
