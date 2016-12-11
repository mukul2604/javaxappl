import java.awt.*;
 import java.awt.event.*;
 import java.awt.geom.*;
 import java.awt.image.*;
 import java.io.*;
 import javax.imageio.*;
 import javax.swing.*;
 /**
11. This program demonstrates the various paint
modes.
12. */
 public class PaintTest
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
 JFrame frame = new PaintTestFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
24. This frame contains radio buttons to choose the
paint mode
25. and a panel that draws a circle in the selected
paint mode.
26. */
 class PaintTestFrame extends JFrame
 {
 public PaintTestFrame()
 {
 setTitle("PaintTest");
 setSize(WIDTH, HEIGHT);
 Container contentPane = getContentPane();
 canvas = new PaintPanel();
 contentPane.add(canvas,
BorderLayout.CENTER);
 JPanel buttonPanel = new JPanel();
 ButtonGroup group = new ButtonGroup();
 JRadioButton colorButton = new
JRadioButton("Color", true);
 buttonPanel.add(colorButton);
 group.add(colorButton);
 colorButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 canvas.setColor();
 }
 });

 JRadioButton gradientPaintButton
 = new JRadioButton("Gradient Paint",
false);
 buttonPanel.add(gradientPaintButton);
 group.add(gradientPaintButton);
 gradientPaintButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 canvas.setGradientPaint();
 }
 });
 JRadioButton texturePaintButton
 = new JRadioButton("Texture Paint",
false);
 buttonPanel.add(texturePaintButton);
 group.add(texturePaintButton);
 texturePaintButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 canvas.setTexturePaint();
 }
 });
 contentPane.add(buttonPanel,
BorderLayout.NORTH);
 }

 private PaintPanel canvas;
 private static final int WIDTH = 400;
 private static final int HEIGHT = 400;
 }

 /**
88. This panel paints a circle in various paint
modes.
89. */
 class PaintPanel extends JPanel
 {
 public PaintPanel()
 {
 try
 {
 bufferedImage = ImageIO.read(new
File("blue-ball.gif"));
 }
 catch (IOException exception)
 {
 exception.printStackTrace();
 }
 setColor();
 }
 public void paintComponent(Graphics g)
 {
 super.paintComponent(g);
 Graphics2D g2 = (Graphics2D)g;
 g2.setPaint(paint);
 Ellipse2D circle
 = new Ellipse2D.Double(0, 0, getWidth(),
getHeight());
 g2.fill(circle);
 }
 /**
116. Paints in a plain color.
117. */
 public void setColor()
 {
 paint = Color.red; // Color implements Paint
 repaint();
 }
 /**
Core Java 2, Volume II Advanced Features
By ViTo @ RoR & BT
125. Sets the paint mode to gradient paint.
126. */
 public void setGradientPaint()
 {
 paint = new GradientPaint(0, 0, Color.red,
 (float)getWidth(), (float)getHeight(),
Color.blue);
 repaint();
 }
 /**
135. Sets the paint mode to texture paint.
136. */
 public void setTexturePaint()
 {
 Rectangle2D anchor = new
Rectangle2D.Double(0, 0,
 2 * bufferedImage.getWidth(),
 2 * bufferedImage.getHeight());
 paint = new TexturePaint(bufferedImage,
anchor);
 repaint();
 }
 private Paint paint;
 private BufferedImage bufferedImage;
 }
