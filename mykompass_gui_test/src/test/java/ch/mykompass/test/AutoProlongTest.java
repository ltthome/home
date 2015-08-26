// package ch.mykompass.test;
//
// import java.text.SimpleDateFormat;
// import java.util.Calendar;
// import java.util.Date;
//
// import org.junit.Test;
//
// import ch.mykompass.common.BaseTest;
// import ch.mykompass.common.TaskName;
// import ch.mykompass.page.ContractCreatePage;
// import ch.mykompass.page.ContractExtendPage;
// import ch.mykompass.page.ContractReleasePage;
// import ch.mykompass.page.ContractRenewPage;
// import ch.mykompass.page.CustomerSearchPage;
// import ch.mykompass.page.CustomerTaskListPage;
// import ch.mykompass.page.MainPage;
// import ch.mykompass.page.PeriodicSupportPage;
// import ch.mykompass.page.ProductionTaskPage;
// import ch.mykompass.page.WelcomeMailPage;
//
// public class AutoProlongTest extends BaseTest {
//
// private static final String TEST_ARTICLE_TYPE = "TST Wawa";
// private static final String TEST_PRICE_TYPE = "3 Jahre mit Einmalzahlung";
// private static final String TEST_PROLONG_PRICE = "automatisch";
// private static final String TEST_CUSTOMER = "AutoTest";
// private static final String TEST_CREDITWORTHINESS = "Gut";
// private static final String TEST_CONTACT = "Auto Test";
// private static final String TEST_NOTE = "This is test ";
// private static final String TEST_SALES_PERSON = "Daniele Galante";
// private static final String TEST_CONTACT_PERSON = "Daniele Galante";
// private static final String TEST_CONTRACT_PRIORITY = "Mittel";
// private static final String TEST_EMAIL_ADDRESS = "wawa.test@axonactive.vn";
//
// private MainPage mainPage;
// private CustomerSearchPage customerSearchPage;
// private CustomerTaskListPage customerTaskListPage;
// private ContractCreatePage contractCreatePage;
// private WelcomeMailPage welcomeMailPage;
// private ContractExtendPage contractExtendPage;
// private ProductionTaskPage productionTaskPage;
// private ContractReleasePage contractReleasePage;
// private PeriodicSupportPage periodicSupportPage;
// private ContractRenewPage contractRenewPage;;
//
// @Test
// public void testAutoProlongContract() {
//
// mainPage = new MainPage();
//
// // Create Contract
// openContractCreate();
// contractCreatePage.setContactPerson(TEST_CONTACT_PERSON).setContractPriority(TEST_CONTRACT_PRIORITY)
// .setSalesPerson(TEST_SALES_PERSON).setContactEmailAddress(TEST_EMAIL_ADDRESS)
// .addContractPosition(TEST_ARTICLE_TYPE, TEST_PRICE_TYPE, TEST_PROLONG_PRICE, "").proceed();
//
// // Welcome Mail
// welcomeMailPage = new WelcomeMailPage();
// welcomeMailPage.proceed();
//
// // Extend Contract
// startTask(TaskName.EXTEND_CONTRACT);
// contractExtendPage = new ContractExtendPage();
// contractExtendPage.selectCreditworthiness(TEST_CREDITWORTHINESS).proceed();
//
// // Finish Production task
// startTask(SpecificTaskName.PRODUCTION);
// productionTaskPage = new ProductionTaskPage();
// productionTaskPage.setContact(TEST_CONTACT).addNote(TEST_NOTE).proceed();
//
// // Release Account
// Date now = new Date();
// String firstPeriodDate = prepareFirstPeriodDateFrom(now);
//
// startTask(SpecificTaskName.DATA_RELEASE);
// contractReleasePage = new ContractReleasePage();
// contractReleasePage.setNextPeriodDate(firstPeriodDate).proceed();
//
// // Set next Periodic support
// String secondPeriodDate = prepareSecondPeriodDateFrom(now);
//
// startTask(SpecificTaskName.PERIODIC_SUPPORT);
// periodicSupportPage = new PeriodicSupportPage();
// periodicSupportPage.setNextPeriodDate(secondPeriodDate).addNote(TEST_NOTE).proceed();
//
// // Renew contract
// startTask(TaskName.RENEW);
// contractRenewPage = new ContractRenewPage();
// contractRenewPage.addNote(TEST_NOTE).proceed();
// }
//
// private String prepareFirstPeriodDateFrom(Date fromDate) {
// Calendar periodDate = Calendar.getInstance();
// periodDate.setTime(fromDate);
// periodDate.add(Calendar.MONTH, 1);
// return asString(periodDate);
// }
//
// private String prepareSecondPeriodDateFrom(Date fromDate) {
// Calendar periodDate = Calendar.getInstance();
// periodDate.setTime(fromDate);
// periodDate.add(Calendar.MONTH, 2);
// return asString(periodDate);
// }
//
// private String asString(Calendar cal) {
// SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
// return formatter.format(cal.getTime());
// }
//
// private void openContractCreate() {
// openCustomerTaskList();
// contractCreatePage = customerTaskListPage.openContractCreate();
// }
//
// private void startTask(String taskName) {
// openCustomerTaskList();
// assertTrue(customerTaskListPage.isTaskAvailable(taskName));
// customerTaskListPage.startTask(taskName);
// }
//
// private void openCustomerTaskList() {
// customerSearchPage = mainPage.openCustomerSearch();
// customerTaskListPage = customerSearchPage.openCustomerTaskList(TEST_CUSTOMER);
// }
//
// private static class SpecificTaskName {
// public static final String PRODUCTION = "Wawa Test - Wawa Test";
// public static final String DATA_RELEASE = "Auftragserf\u00FCllung kontrollieren: Wawa Test";
// public static final String PERIODIC_SUPPORT = "Periodische betreuung: Wawa Test";
// }
//
//
// }
