package ch.mykompass.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PeriodicSupportPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:support-form:j_id_3o_input')";
  }

  public PeriodicSupportPage setNextPeriodDate(String date) {
    WebElement txtDate = findElementById("tab-container:support-form:j_id_3o_input");
    txtDate.click();
    type(By.id("tab-container:support-form:j_id_3o_input"), date);
    return this;
  }

  public PeriodicSupportPage addNote(String note) {
    findElementById("tab-container:support-form:notes").sendKeys(note);
    return this;
  }

}
