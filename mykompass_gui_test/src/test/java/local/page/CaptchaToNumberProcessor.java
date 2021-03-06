package local.page;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class CaptchaToNumberProcessor {
  private static final String[] NUMBER =
      {
          "27,27,24,179,179,164,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,181,181,166,26,26,23,255,255,235,6,6,5,217,217,199,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,218,218,200,5,5,4,255,255,235,39,39,35,157,157,144,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,158,158,145,38,38,35,255,255,235,",
          "252,252,232,2,2,1,215,215,198,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,239,239,220,0,0,0,255,255,235,133,133,122,0,0,0,161,161,148,191,191,176,191,191,176,191,191,176,191,191,176,191,191,176,179,179,164,0,0,0,255,255,235,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,235,",
          "40,40,36,165,165,152,255,255,235,255,255,235,255,255,235,255,255,235,244,244,224,49,49,45,78,78,71,0,0,0,255,255,235,5,5,4,215,215,198,255,255,235,255,255,235,255,255,235,249,249,229,71,71,65,71,71,65,219,219,201,0,0,0,255,255,235,16,16,14,192,192,176,255,255,235,255,255,235,245,245,225,75,75,69,53,53,48,242,242,223,223,223,205,0,0,0,255,255,235,",
          "38,38,35,223,223,205,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,255,255,235,184,184,169,35,35,32,255,255,235,5,5,4,223,223,205,255,255,235,255,255,235,4,4,3,215,215,198,255,255,235,255,255,235,218,218,200,4,4,3,255,255,235,13,13,11,223,223,205,255,255,235,218,218,200,0,0,0,194,194,178,255,255,235,255,255,235,177,177,163,21,21,19,255,255,235,",
          "255,255,235,122,122,112,37,37,34,220,220,202,255,255,235,255,255,235,7,7,6,215,215,198,255,255,235,255,255,235,255,255,235,95,95,87,20,20,18,171,171,157,191,191,176,191,191,176,191,191,176,5,5,4,161,161,148,191,191,176,191,191,176,255,255,235,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,235,",
          "0,0,0,206,206,189,235,235,216,10,10,9,210,210,193,255,255,235,255,255,235,255,255,235,202,202,186,20,20,18,255,255,235,0,0,0,223,223,205,255,255,235,11,11,10,205,205,188,255,255,235,255,255,235,255,255,235,217,217,199,3,3,2,255,255,235,0,0,0,223,223,205,255,255,235,40,40,36,145,145,133,255,255,235,255,255,235,255,255,235,150,150,138,34,34,31,255,255,235,",
          "102,153,0,102,153,0,102,153,0,255,255,255,149,191,91,102,153,0,102,153,0,102,153,0,102,153,0,102,153,0,102,153,0,",
          "102,153,0,149,191,91,255,255,255,250,255,250,255,255,255,213,229,187,121,165,31,102,153,0,102,153,0,102,153,0,102,153,0,",
          "229,255,243,102,153,0,102,153,0,102,153,0,229,255,243,255,255,255,102,153,0,102,153,0,102,153,0,255,255,255,255,255,255,",
          "255,255,255,102,153,0,102,153,0,102,153,0,255,255,255,255,255,255,255,255,255,250,255,250,102,153,0,197,216,159,255,255,255,"};
  //102,153,0

  public static void main(String args[]) throws IOException, URISyntaxException {
    System.out.println(new Date());
    String urlString = "file:\\\\\\D:\\TUNG\\source\\home\\mykompass_gui_test\\src\\test\\resources\\image\\number\\5.png";

    
    String phone = getNumber(urlString);
    System.out.println(phone);
    System.out.println(new Date());
  }

  public static String getNumber(String urlString) throws IOException {
    URL url = new URL(urlString);
    BufferedImage image = ImageIO.read(url);
    StringBuilder imageBuilder = new StringBuilder();
//    StringBuilder colorBuilder = new StringBuilder();
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        int color = image.getRGB(x, y);
        int red = (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = color & 0x000000ff;
        if (red == 255 && green == 255 && blue == 255) {
        	imageBuilder.append(0 + ",");
        } else {
        	imageBuilder.append(1 + ",");
        }
      }
      System.out.println(x + "=" + imageBuilder);
      imageBuilder = new StringBuilder();
    }

    SortedMap<Integer, Integer> indexToNumber = new TreeMap<>();
    String imageAsString = imageBuilder.toString();
//    System.out.println(imageAsString);
    int startPosition = 0;
    for (int i = 0; i < NUMBER.length; i++) {
      int index = imageAsString.indexOf(NUMBER[i]);
      while (index > -1) {
        indexToNumber.put(index, i);
        startPosition = index + 1;
        index = imageAsString.indexOf(NUMBER[i], startPosition);
      }
    }

    String phone = "";
    for (int number : indexToNumber.values()) {
      phone += number;
    };
    return phone;
  }
}
