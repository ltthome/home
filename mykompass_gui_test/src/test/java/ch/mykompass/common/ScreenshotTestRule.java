package ch.mykompass.common;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ch.xpertline.base.client.Browser;

public class ScreenshotTestRule implements MethodRule {
  private static final String SCREENSHOT_FOLDER = "build" + File.separator + "test" + File.separator + "screenshot"
      + File.separator + "";

  @Override
  public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, Object arg2) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          statement.evaluate();
        } catch (Throwable t) {
          captureScreenshot(frameworkMethod.getName());
          throw t;
        }
      }

      public void captureScreenshot(String methodName) {
        try {
          Browser browser = BaseTest.getBrowser();
          WebDriver driver = browser.getDriver();
          new File(SCREENSHOT_FOLDER).mkdirs();
          File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
          File fileScreenShot = new File(SCREENSHOT_FOLDER + methodName + "_Failed.jpg");

          FileUtils.copyFile(screenshot, fileScreenShot);
          System.out.println("folder is at " + fileScreenShot.getAbsolutePath());
        } catch (Exception e) {
          System.out.println(e.toString());
        }
      }
    };
  }

}
