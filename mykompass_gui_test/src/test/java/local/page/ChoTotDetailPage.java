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
    String price;
    try {
      price = findElementByClassName("price").getText();
    } catch (Exception e) {
      price = "";
    }
    if (outputText.indexOf(phone) == -1) {
      outputText.append(name);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(STRING_SEPARATOR);
      outputText.append(phone);
      outputText.append(STRING_SEPARATOR);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(STRING_SEPARATOR);
      outputText.append(price);
      outputText.append(STRING_SEPARATOR);
      outputText.append(COLUMN_SEPARATOR);

      String[] partsOfUrl = getDriver().getCurrentUrl().split("/");
      String category = partsOfUrl[4];
      String district = partsOfUrl[3];
      String detail = partsOfUrl[5];
      
      outputText.append(category);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(detail);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(district);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(new SimpleDateFormat("MM/dd/yy").format(new Date()));

      outputText.append("\r\n");
    }
    return outputText;
  }

}
