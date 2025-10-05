import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DrawPanel extends JPanel implements ActionListener {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private int count = 1;
    private JLabel label = new JLabel();
    final private int width = 440;
    final private int height = 360;
    Graphics2D gr;
    int rgb;
    int r;
    int g;
    int b;
    int gray;
    BufferedImage imge = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    public DrawPanel(final int width, final int height, final int timerDelay) {
        add(label);
        label.setSize(1000, 1000);
        setVisible(true);
        scheduler.scheduleWithFixedDelay(task, 0, 33, TimeUnit.MILLISECONDS);
    }
    Runnable task = () -> {
        StringBuilder frame_name = new StringBuilder();
        frame_name.append("ForApple/frame_");
        BufferedImage img = null;
        try {
            int zero_count = ("" + count).length();
            if (zero_count < 4){
                frame_name.append("0".repeat(4 - zero_count));
            }
            String no = count + ".png";
            no = frame_name.append(no).toString();
            img = ImageIO.read(new File(no));
            boolean[][] mask = new boolean[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    rgb = img.getRGB(x, y);
                    r = (rgb >> 16) & 0xFF;
                    g = (rgb >> 8) & 0xFF;
                    b = rgb & 0xFF;
                    gray = (r + g + b) / 3;
                    mask[y][x] = gray < 128;
                }
            }
            gr = imge.createGraphics();
            paint(gr);
            gr.setColor(Color.black);
            gr.fillRect(0, 0, 480, 360);

            label.setIcon(new ImageIcon(imge));
            gr.setColor(Color.lightGray);
            for (int y = 0; y < height; y += 12) {
                for (int x = 0; x < width; x += 4) {
                    if (mask[y][x]) {
                        gr.fillRect(x, y, 1, 1);
                    }

                }
            }
            repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        count++;
        if(count % 100 == 0){
            gr.dispose();
        }

    };
    public void paint(final Graphics gr) {
        super.paint(gr);
    }
    @Override
    public void actionPerformed(final ActionEvent e) {
    }
}