 import java.awt.*;
 import java.awt.event.*;
 import java.awt.geom.*;
 import java.awt.image.*;
 import java.io.*;
 import javax.imageio.*;
 import javax.swing.*;
 /**
10. This program demonstrates various image
processing operations.
11. */
 public class ImageProcessingTest
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
 JFrame frame = new ImageProcessingFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
23. This frame has a menu to load an image and to
specify
24. various transformations, and a panel to show the
resulting
25. image.
26. */
 class ImageProcessingFrame extends JFrame
 {
 public ImageProcessingFrame()
 {
 setTitle("ImageProcessingTest");
 setSize(WIDTH, HEIGHT);

 JPanel panel = new
 JPanel()
 {
 public void paintComponent(Graphics g)
 {
 super.paintComponent(g);
 if (image != null)
 g.drawImage(image, 0, 0, null);
 }
 };

 getContentPane().add(panel,
BorderLayout.CENTER);

 JMenu fileMenu = new JMenu("File");
 JMenuItem openItem = new JMenuItem("Open");
 openItem.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 openFile();
 }
 });
 fileMenu.add(openItem);
 JMenuItem exitItem = new JMenuItem("Exit");
 exitItem.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 System.exit(0);
 }
 });
 fileMenu.add(exitItem);

 JMenu editMenu = new JMenu("Edit");
 JMenuItem blurItem = new JMenuItem("Blur");
 blurItem.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 float weight = (float) (1/9);
 float[] elements = new float[9];
 for (int i = 0; i < 9; i++)
 elements[i] = weight;
 convolve(elements);
 }
 });
 editMenu.add(blurItem);

 JMenuItem sharpenItem = new
JMenuItem("Sharpen");
 sharpenItem.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 float[] elements =
 { 0.0f, -1.0f, 0.0f,
 -1.0f, 5.f, -1.0f,
 0.0f, -1.0f, 0.0f
 };
 convolve(elements);
 }
 });
 editMenu.add(sharpenItem);
 JMenuItem brightenItem = new
JMenuItem("Brighten");
 brightenItem.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 float a = (float) 1.5;
 float b = -20.0f;
 RescaleOp op = new RescaleOp(a, b,null);
 filter(op);
 }
 });
 editMenu.add(brightenItem);

 JMenuItem edgeDetectItem = new
JMenuItem("Edge detect");
 edgeDetectItem.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 float[] elements =
 { 0.0f, -1.0f, 0.0f,
 -1.0f, 4.f, -1.0f,
 0.0f, -1.0f, 0.0f
 };
 convolve(elements);
 }
 });
 editMenu.add(edgeDetectItem);
 JMenuItem negativeItem = new
JMenuItem("Negative");
 negativeItem.addActionListener(new
 ActionListener()
 {
 public void actionPerformed(ActionEvent event)

 {
 byte negative[] = new byte[256];
 for (int i = 0; i < 256; i++)
 negative[i] = (byte)(255 - i);
 ByteLookupTable table
 = new ByteLookupTable(0,
negative);
 LookupOp op = new LookupOp(table,
null);
 filter(op);
 }
 });
 editMenu.add(negativeItem);
 JMenuItem rotateItem = new
JMenuItem("Rotate");
 rotateItem.addActionListener(new ActionListener()
 {
 public void actionPerformed(ActionEvent event)
 {
 AffineTransform transform=AffineTransform.getRotateInstance(Math.toRadians(5),image.getWidth() / 2,image.getHeight() / 2);
 AffineTransformOp op= new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
 filter(op);
 }
 });
 editMenu.add(rotateItem);

 JMenuBar menuBar = new JMenuBar();
 menuBar.add(fileMenu);
 menuBar.add(editMenu);
 setJMenuBar(menuBar);
 }
 /**
175. Open a file and load the image.
176. */
 public void openFile()
 {
 JFileChooser chooser = new JFileChooser();
 chooser.setCurrentDirectory(new File("."));

 chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
 {
 public boolean accept(File f)
 {
 String name =f.getName().toLowerCase();
 return name.endsWith(".gif")|| name.endsWith(".jpg") || name.endsWith(".jpeg")|| f.isDirectory();
 }
 public String getDescription()
 {
 return "Image files";
 }
 });
 int r = chooser.showOpenDialog(this);
 if(r != JFileChooser.APPROVE_OPTION)
return;

 try
 {
 image =ImageIO.read(chooser.getSelectedFile());
 }
 catch (IOException exception)
 {
 JOptionPane.showMessageDialog(this,exception);
 }
 repaint();
 }

 /**
214. Apply a filter and repaint.
215. @param op the image operation to apply
216. */
 private void filter(BufferedImageOp op)
 {
 BufferedImage filteredImage = new BufferedImage(image.getWidth(),
image.getHeight(),
 image.getType());
 op.filter(image, filteredImage);
 image = filteredImage;
 repaint();
 }
 /**
228. Apply a convolution and repaint.
229. @param elements the convolution kernel (an
array of
230. 9 matrix elements)
231. */
 private void convolve(float[] elements)
 {
 Kernel kernel = new Kernel(3, 3, elements);
 ConvolveOp op = new ConvolveOp(kernel);
 filter(op);
 }
 private BufferedImage image;
 private static final int WIDTH = 400;
 private static final int HEIGHT = 400;
}