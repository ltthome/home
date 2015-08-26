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
// import ch.mykompass.page.CustomerSearchPage;
// import ch.mykompass.page.CustomerTaskListPage;
// import ch.mykompass.page.MainPage;
// import ch.mykompass.page.ManualConfirmationPage;
// import ch.mykompass.page.PeriodicSupportPage;
// import ch.mykompass.page.PrepaymentPage;
// import ch.mykompass.page.SpecialProductionTaskPage;
// import ch.mykompass.page.TaskConfigurationPage;
// import ch.mykompass.page.WelcomeMailPage;
//
// public class ManualProlongTest extends BaseTest {
//
// private static final String TEST_ARTICLE_TYPE = "TST Wawa Special";
// private static final String TEST_PRICE_TYPE = "3 Jahre mit Einmalzahlung";
// private static final String TEST_PROLONG_PRICE = "manuell";
// private static final String TEST_DISCOUNTED_PERCENT = "10";
// private static final String TEST_PREPAYMENT_AMOUNT = "500.0";
// private static final String TEST_PUBLICATION_TARGET = "Wawa Test Twitter";
//
// private static final String TEST_CUSTOMER = "AutoTest1";
// private static final String TEST_CREDITWORTHINESS = "Schlecht";
// private static final String TEST_NOTE = "This is test ";
// private static final String TEST_SALES_PERSON = "Daniele Galante";
// private static final String TEST_CONTACT_PERSON = "Daniele Galante";
// private static final String TEST_CONTRACT_PRIORITY = "Mittel";
// private static final String TEST_EMAIL_ADDRESS = "wawa.test@axonactive.vn";
//
// private static final String TEST_TASK_NAME = "AutoTest_Production";
// private static final String TEST_TASK_TYPE = "Produktion";
// private static final String TEST_ROLE = "Vorbereiter";
// private static final String TEST_DESCRIPTION = "This is task for autotest";
//
// private MainPage mainPage;
// private CustomerSearchPage customerSearchPage;
// private CustomerTaskListPage customerTaskListPage;
// private ContractCreatePage contractCreatePage;
// private WelcomeMailPage welcomeMailPage;
// private ContractExtendPage contractExtendPage;
// private PrepaymentPage prepaymentPage;
// private ManualConfirmationPage manualConfirmationPage;
// private TaskConfigurationPage taskConfigurationPage;
// private SpecialProductionTaskPage specialTaskPage;
// private ContractReleasePage contractReleasePage;
// private PeriodicSupportPage periodicSupportPage;
//
// /**
// * Note before running test:
// *
// * Select environment: test
// *
// * Set variables different with "0": PREPAYMENT_DELAY_INFORM_SALES, PREPAYMENT_DELAY_INFORM_LEADER
// */
// @Test
// public void testManualProlongContract() {
//
// mainPage = new MainPage();
//
// // Create New Contract
// openContractCreate();
// contractCreatePage.setContactPerson(TEST_CONTACT_PERSON).setContractPriority(TEST_CONTRACT_PRIORITY)
// .setSalesPerson(TEST_SALES_PERSON).setContactEmailAddress(TEST_EMAIL_ADDRESS)
// .addContractPosition(TEST_ARTICLE_TYPE, TEST_PRICE_TYPE, TEST_PROLONG_PRICE, TEST_DISCOUNTED_PERCENT).proceed();
//
// // Send confirmation email
// welcomeMailPage = new WelcomeMailPage();
// welcomeMailPage.proceed();
//
// // Extend Contract
// startTask(TaskName.EXTEND_CONTRACT);
// contractExtendPage = new ContractExtendPage();
// contractExtendPage.selectCreditworthiness(TEST_CREDITWORTHINESS).uploadFile().proceed();
//
// // Prepayment
// prepaymentPage = new PrepaymentPage();
// prepaymentPage.setPrepaymentAmmount(TEST_PREPAYMENT_AMOUNT).proceed();
//
// // Finish Manual confirmation
// startTask(TaskName.MANUAL_CONFIRMATION);
// manualConfirmationPage = new ManualConfirmationPage();
// manualConfirmationPage.clickOnConfirmationCheck().addNote(TEST_NOTE).proceed();
//
// // Config production task
// startTask(SpecificTaskName.CONFIG_PRODUCTION);
// taskConfigurationPage = new TaskConfigurationPage();
// taskConfigurationPage.addProductionTask(TEST_TASK_NAME, TEST_TASK_TYPE, TEST_PUBLICATION_TARGET, TEST_ROLE,
// TEST_DESCRIPTION).proceed();
//
// // Finish Production task
// startTask(SpecificTaskName.PRODUCTION);
// specialTaskPage = new SpecialProductionTaskPage();
// specialTaskPage.proceed();
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
// public static final String CONFIG_PRODUCTION = "Produktion anpassen - " + TEST_ARTICLE_TYPE;
// public static final String PRODUCTION = TEST_PUBLICATION_TARGET + " - " + TEST_TASK_NAME;
// public static final String DATA_RELEASE = "Auftragserf\u00FCllung kontrollieren: " + TEST_PUBLICATION_TARGET;
// public static final String PERIODIC_SUPPORT = "Periodische betreuung: " + TEST_PUBLICATION_TARGET;
// }
// }
