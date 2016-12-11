  import java.awt.*;
 import java.awt.event.*;
 import java.util.*;
 import javax.swing.*;
 import javax.swing.event.*;
 import javax.swing.Timer;
 /**
9. This program demonstrates the use of a progress
bar
10. to monitor the progress of a thread.
11. */
 public class ProgressBarTest
 {
 public static void main(String[] args)
 {
     try{
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

     }
     catch(Exception r)
     {
         r.printStackTrace();
     }
 ProgressBarFrame frame = new
ProgressBarFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
/**
23. A frame that contains a button to launch a
simulated activity,
24. a progress bar, and a text area for the activity
output.
25. */
 class ProgressBarFrame extends JFrame
 {
 public ProgressBarFrame()
 {
 setTitle("ProgressBarTest");
 setSize(WIDTH, HEIGHT);

 Container contentPane = getContentPane();
 // this text area holds the activity output
 textArea = new JTextArea();
 JPanel panel = new JPanel();
 startButton = new JButton("Start");
 progressBar = new JProgressBar();
 progressBar.setStringPainted(true);
 panel.add(startButton);
 panel.add(progressBar);
 contentPane.add(new JScrollPane(textArea),
 BorderLayout.CENTER);
 contentPane.add(panel,
BorderLayout.SOUTH);

 startButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 progressBar.setMaximum(100);
 activity = new SimulatedActivity(100);
 activity.start();
 activityMonitor.start();
 startButton.setEnabled(false);
 }
 });

 activityMonitor = new Timer(500, new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 int current =
activity.getCurrent();

 textArea.append(current + "\n");
 progressBar.setValue(current);
 if (current ==
activity.getTarget())
 {
 activityMonitor.stop();
 startButton.setEnabled(true);
 }
 }
 });
 }

 private Timer activityMonitor;
 private JButton startButton;
 private JProgressBar progressBar;
 private JTextArea textArea;
 private SimulatedActivity activity;

 public static final int WIDTH = 300;
 public static final int HEIGHT = 200;
 }
 /**
100. A simulated activity thread.
101. */
 class SimulatedActivity extends Thread
 {
 /**
105. Constructs the simulated activity thread
object. The
106. thread increments a counter from 0 to a given
target.
107. @param t the target value of the counter.
108. */
 public SimulatedActivity(int t)
 {
 current = 0;
 target = t;
 }
 public int getTarget()
 {

 return target;
 }
 public int getCurrent()
 {
 return current;
 }

 public void run()
 {
 try
 {
 while (current < target && !interrupted())
 {
 sleep(100);
 current++;
 }
 }
 catch(InterruptedException e)
 {
 }
 }
 private int current;
 private int target;
 }