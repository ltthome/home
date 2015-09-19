package local.jsoup.page;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import local.crawler.ChoTot;
import local.page.ImageToNumberProcessor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ChoTotDetailPage {

  private static final String COLUMN_SEPARATOR = "`";
  private static final String STRING_SEPARATOR = "~";
  private static final boolean IS_PERSONAL_ADVERTISEMENT = ChoTot.FILTER_URL.contains("f=p");
  public static int REQUEST_COUNTER = 0;
  public static int NEW_CONTACT_COUNTER = 0;

  private String url;

  public ChoTotDetailPage(String url) {
    this.url = url;
  }

  public StringBuilder exportContact(StringBuilder outputText) throws IOException, ParseException {
    if (outputText.indexOf(url.split("/")[5]) != -1) {
      return outputText;
    }
    REQUEST_COUNTER++;
    Document document = Jsoup.parse(new URL(url), 10000);
    Element phoneElement = document.select("#real-phone").first();
    String phoneURL = phoneElement.attr("src");
    String phone = ImageToNumberProcessor.getNumber(phoneURL);

    String name = document.select(".advertised_user").first().text();

    String price = document.select(".price").text();

    if (outputText.indexOf(phone) == -1) {
      NEW_CONTACT_COUNTER++;
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

      String[] partsOfUrl = url.split("/");
      String category = partsOfUrl[4];
      String district = partsOfUrl[3];
      String detail = partsOfUrl[5];

      outputText.append(category);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(detail);
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(district);
      outputText.append(COLUMN_SEPARATOR);
      if (IS_PERSONAL_ADVERTISEMENT) {
        outputText.append("person");
      } else {
        outputText.append("company");
      }
      outputText.append(COLUMN_SEPARATOR);
      outputText.append(new SimpleDateFormat("MM/dd/yy").format(new Date()));

      outputText.append("\r\n");
    }
    return outputText;
  }

}
