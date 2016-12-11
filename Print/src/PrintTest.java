import java.awt.*;
 import java.awt.event.*;
 import java.awt.font.*;
 import java.awt.geom.*;
 import java.awt.print.*;
 import javax.print.attribute.*;
 import javax.swing.*;
 import javax.swing.UIManager;
 /**
12. This program demonstrates how to print 2D
graphics
13. */
 public class PrintTest
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
 JFrame frame = new PrintTestFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
25. This frame shows a panel with 2D graphics and
buttons
26. to print the graphics and to set up the page
format.
27. */
 class PrintTestFrame extends JFrame
 {
 public PrintTestFrame()
 {
 setTitle("PrintTest");
 setSize(WIDTH, HEIGHT);

 Container contentPane = getContentPane();
 canvas = new PrintPanel();
 contentPane.add(canvas,
BorderLayout.CENTER);
 attributes = new
HashPrintRequestAttributeSet();

 JPanel buttonPanel = new JPanel();
 JButton printButton = new JButton("Print");
 buttonPanel.add(printButton);
 printButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 try
 {
 PrinterJob job =
PrinterJob.getPrinterJob();
 job.setPrintable(canvas);
 if (job.printDialog(attributes))
 {

 job.print(attributes);
 }
 }
 catch (PrinterException exception)
 {
 JOptionPane.showMessageDialog(
 PrintTestFrame.this,
exception);
 }
 }
 });

 JButton pageSetupButton = new JButton("Page setup");
 buttonPanel.add(pageSetupButton);
 pageSetupButton.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 PrinterJob job =
PrinterJob.getPrinterJob();
 job.pageDialog(attributes);
 }
 });

 contentPane.add(buttonPanel,
BorderLayout.NORTH);
 }

 private PrintPanel canvas;
 private PrintRequestAttributeSet attributes;

 private static final int WIDTH = 300;
 private static final int HEIGHT = 300;
 }

 /**
89. This panel generates a 2D graphics image for
screen display
90. and printing.
91. */

 class PrintPanel extends JPanel implements
Printable
 {
 public void paintComponent(Graphics g)
 {
 super.paintComponent(g);
 Graphics2D g2 = (Graphics2D)g;
 drawPage(g2);
 }

 public int print(Graphics g, PageFormat pf, int
page)
 throws PrinterException
 {
 if (page >= 1) return
Printable.NO_SUCH_PAGE;
 Graphics2D g2 = (Graphics2D)g;
 g2.translate(pf.getImageableX(),
pf.getImageableY());
 g2.draw(new Rectangle2D.Double(0, 0,
 pf.getImageableWidth(),
pf.getImageableHeight()));

 drawPage(g2);
 return Printable.PAGE_EXISTS;
 }
/**
115. This method draws the page both on the screen
and the
116. printer graphics context.
117. @param g2 the graphics context
118. */
 public void drawPage(Graphics2D g2)
 {
 FontRenderContext context = g2.
getFontRenderContext();
 Font f = new Font("Serif", Font.PLAIN, 72);
 GeneralPath clipShape = new GeneralPath();

 TextLayout layout = new TextLayout("Hello",
f, context);
 AffineTransform transform = null;

AffineTransform.getTranslateInstance(0, 72);
 Shape outline =
layout.getOutline(transform);
 clipShape.append(outline, false);
 layout = new TextLayout("World", f,
context);
 transform
 =
AffineTransform.getTranslateInstance(0, 144);
 outline = layout.getOutline(transform);
 clipShape.append(outline, false);
 g2.draw(clipShape);
 g2.clip(clipShape);

 final int NLINES =50;
 Point2D p = new Point2D.Double(0, 0);
 for (int i = 0; i < NLINES; i++)
 {
 double x = (2 * getWidth() * i) / NLINES;
 double y = (2 * getHeight() * (NLINES -1 - i))/ NLINES;
 Point2D q = new Point2D.Double(x, y);
 g2. draw(new Line2D.Double(p, q));
 }
 }
 }