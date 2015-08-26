package ch.mykompass.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtils {

  private static final String SERVER_ADDRESS = "server.address";
  private static final String APPLICATION_NAME = "application.name";
  private static final String IVY_ENGINE_PORT = "ivy.engine.port";
  private static final String CONFIG_WINDOWS_SERVER = "/config_windows_server.properties";
  private static final String CONFIG_DESIGNER = "/config_designer.properties";
  private static Properties props = new Properties();

  static {
    String propertyFile;
    String osName = System.getProperty("os.name");

    if (osName.toLowerCase().indexOf("server") != -1) {
      propertyFile = CONFIG_WINDOWS_SERVER;
    } else {
      propertyFile = CONFIG_DESIGNER;
    }

    InputStream in = PropsUtils.class.getResourceAsStream(propertyFile);
    try {
      props.load(in);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getApplicationName() {
    return props.getProperty(APPLICATION_NAME);
  }

  public static String getIvyEnginePort() {
    return props.getProperty(IVY_ENGINE_PORT);
  }

  public static String getServerName() {
    return props.getProperty(SERVER_ADDRESS);
  }

}
