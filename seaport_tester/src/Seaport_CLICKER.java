import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;


import javax.swing.*;

public class Seaport_CLICKER {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Rectangle screenRectangle = new Rectangle(screenSize);
    static int mask = InputEvent.BUTTON1_DOWN_MASK;
    static Robot clicker;
    static int clr;
    static int red = 0;
    static int green = 0;
    static int blue = 0;
    static boolean city = true;
    static int fishShips[][] = {{761,698},{697,659},{637,615},{566,583}};


    static int colors[] = new int[3];
    static BufferedImage image;
    //variable c ... 0=red,1=green,2=blue
public static int pixReader(int x_plus, int y_plus, int c){
        Point b = MouseInfo.getPointerInfo().getLocation();
        int x_read = (int) 560 + x_plus;
        int y_read = (int) 555 + y_plus;
        clr =  image.getRGB(x_read,y_read);
        red   = (clr & 0x00ff0000) >> 16;
        green = (clr & 0x0000ff00) >> 8;
        blue  =  clr & 0x000000ff;
        colors = new int[]{red, green, blue};
        return colors[c];
}

static int compare_size = 3;
public static void pictureSave(){
                    JFrame f = new JFrame("New Patterns");
                    f.setSize(350, 350);
                    f.setLocation(300,200);
                    final Label label = new Label();
                    label.setText("Write name of pattern here:");
                    final JTextField textArea = new JTextField();
                    final JButton button = new JButton("Save");
                    f.getContentPane().add(BorderLayout.BEFORE_FIRST_LINE, label);
                    f.getContentPane().add(BorderLayout.CENTER, textArea);
                        Action action = new AbstractAction()
                        {
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {{
                                try{
                                    image = clicker.createScreenCapture(screenRectangle);
                                    BufferedWriter writer = new BufferedWriter(new FileWriter("Patterns.txt",true));
                                    BufferedReader reader = new BufferedReader(new FileReader("Patterns.txt"));
                                    String name = textArea.getText() + "\n";
                                    writer.write(name.substring(0, name.indexOf("\n")));
                                    writer.newLine();
                                    for (int i = 0; i<compare_size; i++){//x axis move
                                        for (int j = 0; j<compare_size; j++){//y axis move
                                            writer.write(String.valueOf(pixReader(i,j,0)) + " ");//red
                                            writer.write(String.valueOf(pixReader(i,j,1)) + " ");//green
                                            writer.write(String.valueOf(pixReader(i,j,2)) + " ");//blue
                                            writer.newLine();
                                        }
                                    }
                                    writer.close();
                                    System.out.println(reader.readLine());
                                    int a[] = new int[3];
                                    String line = reader.readLine(); // to read multiple integers line
                                    String[] strs = line.trim().split("\\s+");
                                    for (int i = 0; i < 3; i++) {
                                        a[i] = Integer.parseInt(strs[i]);
                                    }
                                    System.out.println(a[0]);
                                    System.out.println(a[1]);
                                    System.out.println(a[2]);
                                    reader.close();
                                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            }
                        };
                        textArea.addActionListener(action);
                        f.setVisible(true);
                }


    public static void main(String[] args) throws AWTException, InterruptedException {
        clicker = new Robot();
        pictureSave();
        /*PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.println(x+ " " + y);*/
    }
        }
