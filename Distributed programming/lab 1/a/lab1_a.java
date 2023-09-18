package a;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;

class CustomThread extends Thread {
    private final JSlider slider;
    private final int value;
    private int count;
    private static int COUNTER = 0;

    public CustomThread(JSlider slider, int value, int priority) {
        this.slider = slider;
        this.value = value;
        count = ++COUNTER;
        setPriority(priority);
    }

    @Override
    public void run() {
        while (!interrupted()) {
            ++count;
            if (count > 1000000) {
                slider.setValue((int) slider.getValue() + value);
                count = 0;
            }
        }
    }

    public JSpinner GetSpinner() {
        SpinnerModel sModel = new SpinnerNumberModel(getPriority(), Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1);
        JSpinner spinner = new JSpinner(sModel);
        spinner.setPreferredSize(new Dimension(90, 45));
        spinner.addChangeListener(e -> {
            setPriority((int) (spinner.getValue()));
        });
        return spinner;
    }

}

public class lab1_a {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 470);
        JSlider slide = new JSlider(0, 100);
        slide.setMajorTickSpacing(10);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.setBorder(new EmptyBorder(20, 15, 10, 15));

        CustomThread Thread1 = new CustomThread(slide, -1, Thread.NORM_PRIORITY);
        CustomThread Thread2 = new CustomThread(slide, +1, Thread.NORM_PRIORITY);

        JButton startBTTN = new JButton("Start!");
        startBTTN.setPreferredSize(new Dimension(200, 40));
        startBTTN.addActionListener(e -> {
            startBTTN.setEnabled(false);
            Thread1.start();
            Thread2.start();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(slide);
        panel.add(GetJPanel(Thread1, Thread2));

        JPanel jPanel = new JPanel();
        jPanel.add(startBTTN);
        panel.add(jPanel);

        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    public static JPanel GetJPanel(CustomThread Thread1, CustomThread Thread2) {
        JPanel panel = new JPanel();

        panel.add(Thread1.GetSpinner());
        panel.add(Box.createHorizontalStrut(50));
        panel.add(Thread2.GetSpinner());
        return panel;
    }
}