import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Seaport_CLICKER {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Rectangle screenRectangle = new Rectangle(screenSize);
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    static int mask = InputEvent.BUTTON1_DOWN_MASK;
    static Robot clicker;
    static int clr;
    static int red = 0;
    static int green = 0;
    static int blue = 0;
    static boolean city = true;
    ArrayList<Location> location = new ArrayList<>();

    public static void takeBarrel(BufferedImage im, int from_x, int from_y, int to_x, int to_y){
        outerloop:
        for (int x = from_x; x < to_x; x++) {
            for (int y = from_y; y < to_y; y++) {
                clr = im.getRGB(x, y);
                red = (clr & 0xff0000) >> 16;
                green = (clr & 0x00ff00) >> 8;
                blue = clr & 0x0000ff;
                if (red == 255 && green == 183 && blue == 0) {
                    clicker.mouseMove(x, y);
                    clicker.mousePress(mask);
                    clicker.mouseRelease(mask);
                    clicker.mousePress(mask);
                    clicker.mouseRelease(mask);
                    break outerloop;
                }
            }
        }
    }

    public static void changeScreen(){
        clicker.mouseMove(1808,850);
        clicker.mousePress(mask);
        clicker.mouseRelease(mask);
        city ^= true;
    }

    public static void moveScreen(int safe_x, int safe_y, int value_x, int value_y) throws InterruptedException {
        clicker.mouseMove(safe_x,safe_y);
        clicker.mousePress(mask);
        clicker.mouseRelease(mask);
        clicker.mousePress(mask);
        clicker.mouseMove(safe_x + value_x,safe_y + value_y);
        TimeUnit.SECONDS.sleep(1);
        clicker.mouseMove((safe_x + value_x)-1,(safe_y + value_y)-1);
        clicker.mouseRelease(mask);
        System.out.println(city);
    }

    public static void pixReader(){
        BufferedImage image = clicker.createScreenCapture(screenRectangle);
        Point b = MouseInfo.getPointerInfo().getLocation();
        int x_read = (int) b.getX();
        int y_read = (int) b.getY();
        System.out.println(x_read);
        System.out.println(y_read);
        clr =  image.getRGB(x_read,y_read);
        red   = (clr & 0x00ff0000) >> 16;
        green = (clr & 0x0000ff00) >> 8;
        blue  =  clr & 0x000000ff;
        System.out.println("Red Color value = " + red);
        System.out.println("Green Color value = " + green);
        System.out.println("Blue Color value = " + blue);
        System.out.println(" ");
    }

    public static void sendShip(){


    }

    public static void main(String[] args) throws AWTException, InterruptedException {
        clicker = new Robot();
        BufferedImage image;
        changeScreen();
        moveScreen(722,790,0,90);
        while (true) {
            pixReader();
            image = clicker.createScreenCapture(screenRectangle);
            if(city) {
                takeBarrel(image,0, 250 ,1300, 1000);
            }else{

            }
            TimeUnit.SECONDS.sleep(4);
        }
    }




}
