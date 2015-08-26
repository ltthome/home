package ch.mykompass.page;


public class ContractRenewPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:form:notes')";
  }

  public ContractRenewPage addNote(String note) {
    findElementById("tab-container:form:notes").sendKeys(note);
    return this;
  }

}
