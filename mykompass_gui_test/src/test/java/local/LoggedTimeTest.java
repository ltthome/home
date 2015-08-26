package local;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import ch.mykompass.common.PropsUtils;
import ch.mykompass.common.ScreenshotTestRule;
import ch.mykompass.common.TaskName;
import ch.mykompass.page.ContractCreatePage;
import ch.mykompass.page.ContractExtendPage;
import ch.mykompass.page.ContractReleasePage;
import ch.mykompass.page.ContractRenewPage;
import ch.mykompass.page.CustomerSearchPage;
import ch.mykompass.page.CustomerTaskListPage;
import ch.mykompass.page.MainPage;
import ch.mykompass.page.PeriodicSupportPage;
import ch.mykompass.page.ProductionTaskPage;
import ch.mykompass.page.WelcomeMailPage;
import ch.xpertline.base.client.Browser;
import ch.xpertline.base.enums.BrowserType;

public class LoggedTimeTest {
  private static Browser browser;

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

  // @Before
  // public void setup() throws Exception {
  // browser = Browser.getBrowser();
  // browser.launch(BrowserType.FIREFOX, getProcessStartLink("xportal/13D1EC51435A0179/start.ivp"), "");
  // LoginPage loginPage = new LoginPage();
  // loginPage.login();
  // }


  @AfterClass
  public static void terminate() {
    Browser.getBrowser().shutdown();
  }

  public static String getProcessStartLink(String relativeIvpPath) {
    return "http://" + PropsUtils.getServerName() + ":" + PropsUtils.getIvyEnginePort() + "/ivy/" + "pro/"
        + PropsUtils.getApplicationName() + "/" + relativeIvpPath;
  }

  public static Browser getBrowser() {
    return browser;
  }



  private static final String TEST_ARTICLE_TYPE = "TST Wawa";
  private static final String TEST_PRICE_TYPE = "3 Jahre mit Einmalzahlung";
  private static final String TEST_PROLONG_PRICE = "automatisch";
  private static final String TEST_CUSTOMER = "AutoTest";
  private static final String TEST_CREDITWORTHINESS = "Gut";
  private static final String TEST_CONTACT = "Auto Test";
  private static final String TEST_NOTE = "This is test ";
  private static final String TEST_SALES_PERSON = "Daniele Galante";
  private static final String TEST_CONTACT_PERSON = "Daniele Galante";
  private static final String TEST_CONTRACT_PRIORITY = "Mittel";
  private static final String TEST_EMAIL_ADDRESS = "wawa.test@axonactive.vn";

  private MainPage mainPage;
  private CustomerSearchPage customerSearchPage;
  private CustomerTaskListPage customerTaskListPage;
  private ContractCreatePage contractCreatePage;
  private WelcomeMailPage welcomeMailPage;
  private ContractExtendPage contractExtendPage;
  private ProductionTaskPage productionTaskPage;
  private ContractReleasePage contractReleasePage;
  private PeriodicSupportPage periodicSupportPage;
  private ContractRenewPage contractRenewPage;;

  @Test
  public void testLoggedTime() throws Exception {
    browser = Browser.getBrowser();
    browser.launch(BrowserType.FIREFOX,
        "http://swbntsrv24.soreco.wan/jira/login.jsp?os_destination=%2Fissues%2F%3Ffilter%3D22700", "");
    LoginPage loginPage = new LoginPage();
    loginPage.login();
    browser.getDriver().get(
        "http://swbntsrv24.soreco.wan/jira/secure/views/bulkedit/BulkEdit1!default.jspa?reset=true&tempMax=999");

    FilterTaskPage filterTaskPage = new FilterTaskPage();
    List<String> issueKeys = filterTaskPage.getIssueKeys();

    StringBuilder contentBuilder = new StringBuilder();
    for (String issueKey : issueKeys) {
      String issueURL =
          "http://swbntsrv24.soreco.wan/jira/browse/" + issueKey
              + "?page=com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel";
      browser.getDriver().get(issueURL);
      TaskPage taskPage = new TaskPage();
      contentBuilder.append(taskPage.exportLoggedTime());
    }


    Path exportedFile = Paths.get("d:\\tmp\\time.csv");
    Files.write(exportedFile, contentBuilder.toString().getBytes());

  }

  // @Test
  public void testAutoProlongContract() {

    mainPage = new MainPage();

    // Create Contract
    openContractCreate();
    contractCreatePage.setContactPerson(TEST_CONTACT_PERSON).setContractPriority(TEST_CONTRACT_PRIORITY)
        .setSalesPerson(TEST_SALES_PERSON).setContactEmailAddress(TEST_EMAIL_ADDRESS)
        .addContractPosition(TEST_ARTICLE_TYPE, TEST_PRICE_TYPE, TEST_PROLONG_PRICE, "").proceed();

    // Welcome Mail
    welcomeMailPage = new WelcomeMailPage();
    welcomeMailPage.proceed();

    // Extend Contract
    startTask(TaskName.EXTEND_CONTRACT);
    contractExtendPage = new ContractExtendPage();
    contractExtendPage.selectCreditworthiness(TEST_CREDITWORTHINESS).proceed();

    // Finish Production task
    startTask(SpecificTaskName.PRODUCTION);
    productionTaskPage = new ProductionTaskPage();
    productionTaskPage.setContact(TEST_CONTACT).addNote(TEST_NOTE).proceed();

    // Release Account
    Date now = new Date();
    String firstPeriodDate = prepareFirstPeriodDateFrom(now);

    startTask(SpecificTaskName.DATA_RELEASE);
    contractReleasePage = new ContractReleasePage();
    contractReleasePage.setNextPeriodDate(firstPeriodDate).proceed();

    // Set next Periodic support
    String secondPeriodDate = prepareSecondPeriodDateFrom(now);

    startTask(SpecificTaskName.PERIODIC_SUPPORT);
    periodicSupportPage = new PeriodicSupportPage();
    periodicSupportPage.setNextPeriodDate(secondPeriodDate).addNote(TEST_NOTE).proceed();

    // Renew contract
    startTask(TaskName.RENEW);
    contractRenewPage = new ContractRenewPage();
    contractRenewPage.addNote(TEST_NOTE).proceed();
  }

  private String prepareFirstPeriodDateFrom(Date fromDate) {
    Calendar periodDate = Calendar.getInstance();
    periodDate.setTime(fromDate);
    periodDate.add(Calendar.MONTH, 1);
    return asString(periodDate);
  }

  private String prepareSecondPeriodDateFrom(Date fromDate) {
    Calendar periodDate = Calendar.getInstance();
    periodDate.setTime(fromDate);
    periodDate.add(Calendar.MONTH, 2);
    return asString(periodDate);
  }

  private String asString(Calendar cal) {
    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
    return formatter.format(cal.getTime());
  }

  private void openContractCreate() {
    openCustomerTaskList();
    contractCreatePage = customerTaskListPage.openContractCreate();
  }

  private void startTask(String taskName) {
    openCustomerTaskList();
    customerTaskListPage.startTask(taskName);
  }

  private void openCustomerTaskList() {
    customerSearchPage = mainPage.openCustomerSearch();
    customerTaskListPage = customerSearchPage.openCustomerTaskList(TEST_CUSTOMER);
  }

  private static class SpecificTaskName {
    public static final String PRODUCTION = "Wawa Test - Wawa Test";
    public static final String DATA_RELEASE = "Auftragserf\u00FCllung kontrollieren: Wawa Test";
    public static final String PERIODIC_SUPPORT = "Periodische betreuung: Wawa Test";
  }


}
