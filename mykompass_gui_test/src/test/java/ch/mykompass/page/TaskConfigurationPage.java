package ch.mykompass.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TaskConfigurationPage extends MainPage {

  @Override
  protected String getLoadedLocator() {
    return "id('tab-container:j_id_30:j_id_3v')";
  }

  private void setTaskName(String taskName) {
    waitAjaxIndicatorDisappear();
    findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:taskNameInput").sendKeys(taskName);
  }

  private void setTaskType(String taskType) {
    findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:taskTypeDropDown_label").click();
    WebElement taskTypeList =
        findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:taskTypeDropDown_panel");
    List<WebElement> taskTypes = taskTypeList.findElements(By.xpath("//ul/li"));

    for (WebElement eachTaskType : taskTypes) {
      if (eachTaskType.getText().equals(taskType)) {
        eachTaskType.click();
        return;
      }
    }
  }

  private void setPubTarget(String pubTarget) {
    if (!pubTarget.equals("")) {
      findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:publicationTargetType_label").click();
      WebElement pubTargetList =
          findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:publicationTargetType_panel");
      List<WebElement> pubTargets = pubTargetList.findElements(By.xpath("//ul/li"));

      for (WebElement eachpubTarget : pubTargets) {
        if (eachpubTarget.getText().equals(pubTarget)) {
          eachpubTarget.click();
          return;
        }
      }
    }
  }

  private void setRole(String role) {
    findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:roleAutoComplete_input").sendKeys(role);
  }

  private void setDescription(String description) {
    findElementById("tab-container:j_id_30:dataGridContainer:0:j_id_41:commentInput").sendKeys(description);
  }

  private void saveTask() {
    findElementByXpath("//button[@title='Save']").click();
    waitAjaxIndicatorDisappear();
  }

  public TaskConfigurationPage addProductionTask(String taskName, String taskType, String pubTarget, String role,
      String description) {
    findElementById("tab-container:j_id_30:j_id_3v").click();
    waitAjaxIndicatorDisappear();
    setTaskName(taskName);
    setTaskType(taskType);
    setPubTarget(pubTarget);
    setRole(role);
    setDescription(description);
    saveTask();
    return this;
  }

}
