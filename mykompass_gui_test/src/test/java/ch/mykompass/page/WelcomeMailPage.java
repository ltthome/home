package ch.mykompass.page;


public class WelcomeMailPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_2z:j_id_3f:done-button')";
  }

}
