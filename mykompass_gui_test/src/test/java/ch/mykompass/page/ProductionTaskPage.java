package ch.mykompass.page;

public class ProductionTaskPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_30:contactPerson')";
  }

  public ProductionTaskPage setContact(String contact) {
    findElementById("tab-container:j_id_30:contactPerson").sendKeys(contact);
    return this;
  }

  public ProductionTaskPage addNote(String note) {
    findElementById("tab-container:j_id_30:notes").sendKeys(note);
    return this;
  }

}
