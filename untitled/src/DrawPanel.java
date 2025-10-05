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
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TIMER_DELAY;
    private Timer timer;
    private int ticksFromStart = 0;
    private int count = 300;
    private JLabel label = new JLabel();

    public DrawPanel(final int width, final int height, final int timerDelay) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.TIMER_DELAY = timerDelay;
        timer = new Timer(timerDelay, this);
        timer.start();
        add(label);
        setVisible(true);
        scheduler.scheduleWithFixedDelay(task, 0, 30, TimeUnit.MILLISECONDS);
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
            label.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        count++;
    };
    @Override
    public void paint(final Graphics gr) {
        super.paint(gr);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
    }
}