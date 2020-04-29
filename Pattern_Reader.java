import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Pattern_Reader {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Rectangle screenRectangle = new Rectangle(screenSize);
    static int mask = InputEvent.BUTTON1_DOWN_MASK;
    static Robot clicker;
    static int clr;
    static int red = 0;
    static int green = 0;
    static int blue = 0;
    static boolean city = true;
    static int fishShips[][] = {{761, 698}, {697, 659}, {637, 615}, {566, 583}};
    static int patterns = 15;
    static int x_axis = 0;
    static int y_axis = 0;
    static int compare_size = 3;
    static int square_size = compare_size * compare_size;
    static int x_screen_start = 0;
    static int y_screen_start = 0;
    static int x_screen_end = 1800;
    static int y_screen_end = 900;
    static boolean drevo_nalezeno;
    static boolean lod_vybrana = false;
    static boolean lod_vyzvednuta = false;
    static boolean kamen_nalezeno;

    static BufferedImage image;
    static BufferedImage image1;

    public static int pixReader(int x, int y, int x_plus, int y_plus, int c, BufferedImage i) {
        int x_read = x + x_plus;
        x_axis = x_read;
        int y_read = y + y_plus;
        y_axis = y_read;
        clr = i.getRGB(x_read, y_read);
        red = (clr & 0x00ff0000) >> 16;
        green = (clr & 0x0000ff00) >> 8;
        blue = clr & 0x000000ff;
        int[] rgb = new int[]{red, green, blue};
        return rgb[c];
    }

    static BufferedReader reader;
    static String name;
    static String[] lines = new String[square_size];
    static boolean new_pattern = true;

    public static void pattern_read(int colors[][]) {
        try {
            int total_difference = 0;
            int a[] = new int[3];
            if (new_pattern) {
                name = reader.readLine();
                System.out.println(name + "nacteno");
                for (int q = 0; q < square_size; q++) {
                    lines[q] = reader.readLine();
                }
                new_pattern = false;
            }
            outerloop:
            for (int j = 0; j < square_size; j++) {
                String[] strs = lines[j].trim().split("\\s+");
                for (int i = 0; i < 3; i++) {
                    a[i] = Integer.parseInt(strs[i]);
                }
                int x = pixel_comparison(colors, a, j);
                if (x > 40) {
                    total_difference = Integer.MAX_VALUE;
                    break outerloop;
                } else {
                    total_difference += x;
                }
            }
            if (total_difference < Integer.MAX_VALUE && name.contains("barel") && city == true) {
                take_thing(x_axis, y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
            } else if (total_difference == 0 && name.contains("rybar") && city == true) {
                take_thing(x_axis, y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
            } else if (total_difference < 150 && name.contains("drevo") && city == true && drevo_nalezeno == false) {
                take_thing(x_axis, y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
                nova_vyroba(x_axis, y_axis, name);
                System.out.println(name + " vyroba " + x_axis + " " + y_axis + " " + total_difference);
                drevo_nalezeno = true;
            } else if (total_difference <= 120 && name.contains("zlato") && city == true) {
                take_thing(x_axis, y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
            } else if (total_difference < 100 && name.contains("zelezo") && city == true) {
                take_thing(x_axis, y_axis);
                nova_vyroba(x_axis, y_axis, name);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
            } else if (total_difference < 20 && name.contains("pila") && city == true) {
                //take_thing(x_axis,y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
            } else if (total_difference <= 30 && name.contains("mapa") && city == true) {
                take_thing(x_axis, y_axis);
                city = false;
            } else if (total_difference <= 30 && name.contains("kotva") && city == false) {
                take_thing(x_axis, y_axis);
                city = true;
            } else if (total_difference <= 10 && name.contains("krizek")) {
                take_thing(x_axis, y_axis);
            } else if (total_difference < 100 && name.contains("kamen") && city == true&&kamen_nalezeno == false) {
                take_thing(x_axis, y_axis);
                System.out.println(name + " shoda " + x_axis + " " + y_axis + " " + total_difference);
                nova_vyroba(x_axis, y_axis, name);
                kamen_nalezeno = true;
            } else if (total_difference < 130 && name.contains("kapitan") && name.contains("Vyplout") && city == false&& lod_vybrana == false) {
                //posli lod
                lod_vybrana = false;
            } else if (total_difference < 130 && name.contains("kapitan") && name.contains("Vyzvednout") && city == false&& lod_vyzvednuta == false) {
                take_thing(x_axis - 80, y_axis + 20);
                lod_vyzvednuta = true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void nova_vyroba(int x, int y, String material) throws InterruptedException {
        clicker.mouseMove(x, y + 40);
        TimeUnit.SECONDS.sleep(1);
        clicker.mousePress(mask);
        clicker.mouseRelease(mask);
        TimeUnit.SECONDS.sleep(1);
        clicker.mouseMove(x, y + 110);
        clicker.mousePress(mask);
        clicker.mouseRelease(mask);
        if (material.contains("drevo")) {
            int[][] col_d = {{0, 0, 0}};
            int[] patt_d = {231, 161, 59};
            for (int i = x - 40; i < x + 40; ++i) {
                for (int j = y - 40; j < y + 40; ++j) {
                    int cc[] = {pixReader(0, 0, i, j, 0, image), pixReader(0, 0, i, j, 1, image), pixReader(0, 0, i, j, 2, image)};
                    col_d[0] = cc;
                    if (pixel_comparison(col_d, patt_d, 0) == 0) {
                        clicker.mouseMove(i, j);
                        System.out.println(i + " " + j + col_d + patt_d);
                        clicker.mousePress(mask);
                        clicker.mouseRelease(mask);
                    }
                }
            }
        }
        int[][] col = {{0, 0, 0}};
        int[] patt = {0, 117, 239};
        TimeUnit.SECONDS.sleep(1);
        boolean vyrobit = false;
        image1 = clicker.createScreenCapture(screenRectangle);
        first_loop:
        for (int i = x_screen_start; i < x_screen_end; ++i) {
            for (int j = y_screen_start; j < y_screen_end; ++j) {
                int cc[] = {pixReader(0, 0, i, j, 0, image1), pixReader(0, 0, i, j, 1, image1), pixReader(0, 0, i, j, 2, image1)};
                col[0] = cc;
                if (pixel_comparison(col, patt, 0) == 0) {
                    clicker.mouseMove(i + 10, j + 10);
                    System.out.println(i + " " + j + col + patt);
                    clicker.mousePress(mask);
                    clicker.mouseRelease(mask);
                    vyrobit = true;
                    break first_loop;
                }
            }
        }
        if (!vyrobit) {
            int[][] col_k = {{0, 0, 0}};
            int[] patt_k = {206, 0, 44};
            for (int i = x_screen_start; i < x_screen_end; ++i) {
                for (int j = y_screen_start; j < y_screen_end; ++j) {
                    int cc[] = {pixReader(0, 0, i, j, 0, image1), pixReader(0, 0, i, j, 1, image1), pixReader(0, 0, i, j, 2, image1)};
                    col[0] = cc;
                    if (pixel_comparison(col_k, patt_k, 0) < 10) {
                        clicker.mouseMove(i, j);
                        System.out.println(i + " " + j + col_k + " " + patt_k);
                        clicker.mousePress(mask);
                        clicker.mouseRelease(mask);
                        System.out.println("nenalezeno, zavírám");
                        return;
                    }
                }
            }
        }
        TimeUnit.SECONDS.sleep(1);
    }

    public static int pixel_comparison(int[][] new_pix, int[] pattern_pix, int pix_number) {
        int value = 0;
        for (int i = 0; i < 3; i++) {
            //System.out.print(new_pix[pix_number][i] + " ");
            //System.out.println(pattern_pix[i]);
            value += Math.abs(new_pix[pix_number][i] - pattern_pix[i]);
        }
        //System.out.println(value);
        return value;
    }


    public static void take_thing(int x_coor, int y_coor) {
        clicker.mouseMove(x_coor, y_coor);
        clicker.mousePress(mask);
        clicker.mouseRelease(mask);
    }

    public static void move_screen(int x_center, int y_center, int x_move, int y_move) throws InterruptedException {
        clicker.mouseMove(x_center, y_center);
        clicker.mousePress(mask);
        clicker.mouseMove(x_center + x_move, y_center + y_move);
        TimeUnit.SECONDS.sleep(1L);
        clicker.mouseMove(x_center + x_move + 1, y_center + y_move + 1);
        clicker.mouseRelease(mask);
    }

    public static void screen_check() throws AWTException, IOException {
        reader = new BufferedReader(new FileReader("Patterns.txt"));
        image = clicker.createScreenCapture(screenRectangle);
        int colorss[][] = new int[square_size][3];
        int vysledek = 0;
        for (int q = 0; q < patterns; q++) {
            drevo_nalezeno = false;
            lod_vyzvednuta = false;
            lod_vybrana = false;
            kamen_nalezeno = false;
            for (int x = x_screen_start; x < x_screen_end; ++x) {
                for (int y = y_screen_start; y < y_screen_end; ++y) {
                    for (int i = 0; i < compare_size; i++) {//x axis move
                        for (int j = 0; j < compare_size; j++) {//y axis move
                            int cc[] = {pixReader(x, y, i, j, 0, image), pixReader(x, y, i, j, 1, image), pixReader(x, y, i, j, 2, image)};
                            colorss[i * compare_size + j] = cc;
                        }
                    }
                    pattern_read(colorss);
                    //if something found
                    if (name.contains("barel")) {
                        vysledek++;
                    } else if (name.contains("rybar")) {

                    } else if (name.contains("Plavidlo")) {
                        //lod k vyslani
                    } else if (name.contains("Vylepseni")) {

                    }
                }
            }
            new_pattern = true;
        }
        reader.close();
    }

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        clicker = new Robot();
        boolean[] screen_checked = {false};
        JFrame f = new JFrame("Seaport_Clicker");
        f.setSize(400, 300);
        f.setLocation(300, 200);
        JTextField textArea = new JTextField();
        f.getContentPane().add(BorderLayout.AFTER_LAST_LINE, textArea);
        final boolean[] start = {true};
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    Label label = new Label();
                    Label label1 = new Label();
                    System.out.println("aktivni");
                    if (!start[0]) {
                        x_screen_end = (int) b.getX();
                        y_screen_end = (int) b.getY();
                        label1.setText("A end coordinate: " + x_screen_end + " B end coordinate: " + y_screen_end);
                        System.out.println("A end coordinate: " + x_screen_end + " B end coordinate: " + y_screen_end);
                        screen_checked[0] = true;
                        System.out.println(screen_checked[0]);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (start[0]) {
                        x_screen_start = (int) b.getX();
                        y_screen_start = (int) b.getY();
                        label.setText("A start coordinate: " + x_screen_start + " B start coordinate: " + y_screen_start);
                        System.out.println("A start coordinate: " + x_screen_start + " B start coordinate: " + y_screen_start);
                        start[0] = false;
                    }
                    f.getContentPane().add(label);
                    f.getContentPane().add(label1);
                }
            }
        };
        f.setVisible(true);
        textArea.addActionListener(action);
        while (true) {
            if (screen_checked[0]) {
                screen_check();
                TimeUnit.SECONDS.sleep(2);
                System.out.println("kontroluji");
            }
            System.out.println("chyba");
        }
    }
}