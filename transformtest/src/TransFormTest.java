import java.awt.*;
 import java.awt.event.*;
 import java.awt.geom.*;
 import javax.swing.*;
 /**
8. This program displays the effects of various
transformations.
Core Java 2, Volume II Advanced Features
By ViTo @ RoR & BT
9. */
 public class TransFormTest
 {
 public static void main(String[] args)
 {
     try{
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     }
     catch(Exception e)
     {
         e.printStackTrace();
     }
 JFrame frame = new TransformTestFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
21. This frame contains radio buttons to choose a
transformations
22. and a panel to display the effect of the chosen
23. transformation.
24. */
 class TransformTestFrame extends JFrame
 {
public TransformTestFrame()
 {
 setTitle("TransformTest");
 setSize(WIDTH, HEIGHT);
 Container contentPane = getContentPane();
 canvas = new TransformPanel();
 contentPane.add(canvas,
BorderLayout.CENTER);
 JPanel buttonPanel = new JPanel();
 ButtonGroup group = new ButtonGroup();
 JRadioButton rotateButton
 = new JRadioButton("Rotate", true);
 buttonPanel.add(rotateButton);
 group.add(rotateButton);
 rotateButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 canvas.setRotate();
 }
 });

 JRadioButton translateButton
 = new JRadioButton("Translate", false);
 buttonPanel.add(translateButton);
 group.add(translateButton);
 translateButton.addActionListener(new
 ActionListener()
 {
 public void
 actionPerformed(ActionEvent event)
 {
 canvas.setTranslate();
 }
 });
 JRadioButton scaleButton
 = new JRadioButton("Scale", false);
 buttonPanel.add(scaleButton);
 group.add(scaleButton);
 scaleButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 canvas.setScale();
 }
 });
 JRadioButton shearButton
 = new JRadioButton("Shear", false);
 buttonPanel.add(shearButton);
 group.add(shearButton);
 shearButton.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 canvas.setShear();
 }
 });
 contentPane.add(buttonPanel,
BorderLayout.NORTH);
 }

 private TransformPanel canvas;
 private static final int WIDTH = 300;
 private static final int HEIGHT = 300;
 }

 /**
100. This panel displays a square and its transformed
image
101. under a transformation.
102. */
 class TransformPanel extends JPanel
 {
 public TransformPanel()
 {
 square = new Rectangle2D.Double(-50, -50, 100, 100);
 t = new AffineTransform();
 setRotate();
 }
 public void paintComponent(Graphics g)
 {
 super.paintComponent(g);
 Graphics2D g2 = (Graphics2D)g;
 g2.translate(getWidth() / 2, getHeight() /
2);
 g2.setPaint(Color.gray);
 g2.draw(square);
 g2.transform(t);
 /* we don't use setTransform because we
want
121. to compose with the current translation
122. */
 g2.setPaint(Color.black);
 g2.draw(square);
 }
 /**
Core Java 2, Volume II Advanced Features
By ViTo @ RoR & BT
128. Set the transformation to a rotation.
129. */
 public void setRotate()
 {
 t.setToRotation(Math.toRadians(30));
 repaint();
 }

/**
137. Set the transformation to a translation.
138. */
 public void setTranslate()
 {
 t.setToTranslation(20, 15);
 repaint();
 }
 /**
146. Set the transformation to a scale
transformation.
147. */
 public void setScale()
 {
 t.setToScale(2.0, 1.5);
 repaint();
 }
 /**
155. Set the transformation to a shear
transformation.
156. */
 public void setShear()
 {
 t.setToShear(-0.2, 0);
repaint();
 }
private Rectangle2D square;
 private AffineTransform t;
 }
