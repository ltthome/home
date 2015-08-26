package ch.mykompass.page;

import org.openqa.selenium.By;

public class ManualConfirmationPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_31:j_id_3d')";
  }

  public ManualConfirmationPage clickOnConfirmationCheck() {
    findElementById("tab-container:j_id_31:j_id_34").click();
    return this;
  }

  public ManualConfirmationPage addNote(String note) {
    waitForElementDisplayed(By.id("tab-container:j_id_31:j_id_3d"), true, DEFAULT_TIMEOUT);
    findElementById("tab-container:j_id_31:j_id_3d").sendKeys(note);
    return this;
  }

}
