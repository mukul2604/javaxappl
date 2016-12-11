import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;

 public class RadioButtonTest
 {

 public static void main(String[] args)
 {
 EventQueue.invokeLater(new Runnable()
 {
 public void run()
 {
 RadioButtonFrame frame = new RadioButtonFrame();
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setVisible(true);
 }
 });
 }
 }

 class RadioButtonFrame extends JFrame
 {
 public RadioButtonFrame()
 {
 setTitle("RadioButtonTest");
 setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
 label = new JLabel("The quick brown fox jumps over the lazy dog.");
 label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
 add(label, BorderLayout.CENTER);

 buttonPanel = new JPanel();
 group = new ButtonGroup();
 addRadioButton("Small", 8);
 addRadioButton("Medium", 12);
 addRadioButton("Large", 18);
 addRadioButton("Extra large", 36);

 add(buttonPanel, BorderLayout.SOUTH);
 }

 public void addRadioButton(String name, final int size)
 {
 boolean selected = size == DEFAULT_SIZE;
 JRadioButton button = new JRadioButton(name, selected);
 group.add(button);
 buttonPanel.add(button);
 ActionListener listener = new ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 label.setFont(new Font("Serif", Font.PLAIN, size));
 }
 };

 button.addActionListener(listener);
 }
 public static final int DEFAULT_WIDTH = 400;
 public static final int DEFAULT_HEIGHT = 200;
 private JPanel buttonPanel;
 private ButtonGroup group;
 private JLabel label;
 private static final int DEFAULT_SIZE = 12;
 }