 import java.awt.*;
 import java.awt.event.*;
 import java.awt.geom.*;
 import java.util.*;
 import javax.swing.*;

 /**
8. This program demonstrates the various 2D
shapes.
9. */
 public class ShapeTest
 {
 public static void main(String[] args)
 {
     try
     {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     }
     catch(Exception r)
     {
         r.printStackTrace();
     }
 JFrame frame = new ShapeTestFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
21. This frame contains a combo box to select a shape
22. and a panel to draw it.
23. */
 class ShapeTestFrame extends JFrame
 {
 public ShapeTestFrame()
 {
 setTitle("ShapeTest");
 setSize(WIDTH, HEIGHT);

 Container contentPane = getContentPane();
 final ShapePanel panel = new ShapePanel();
 contentPane.add(panel,
BorderLayout.CENTER);
 final JComboBox comboBox = new JComboBox();
 comboBox.addItem(new LineMaker());
 comboBox.addItem(new RectangleMaker());
 comboBox.addItem(new
RoundRectangleMaker());
 comboBox.addItem(new EllipseMaker());
 comboBox.addItem(new ArcMaker());
 comboBox.addItem(new PolygonMaker());
 comboBox.addItem(new QuadCurveMaker());
 comboBox.addItem(new CubicCurveMaker());
 comboBox.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 ShapeMaker shapeMaker =

(ShapeMaker)comboBox.getSelectedItem();
 panel.setShapeMaker(shapeMaker);
 }
 });
 contentPane.add(comboBox,
BorderLayout.NORTH);
 }

 private static final int WIDTH = 300;

 private static final int HEIGHT = 300;
 }
 /**
62. This panel draws a shape and allows the user to
63. move the points that define it.
64. */
 class ShapePanel extends JPanel
 {
 public ShapePanel()
 {
 addMouseListener(new
 MouseAdapter()
 {
 public void mousePressed(MouseEvent
event)
 {
 Point p = event.getPoint();
 for (int i = 0; i < points.length;
i++)
 {
 double x = points[i].getX() - SIZE
/ 2;
 double y = points[i].getY() - SIZE
/ 2;
 Rectangle2D r
 = new Rectangle2D.Double(x, y,
SIZE, SIZE);
 if (r.contains(p))
 {
 current = i;
 return;
 }
 }
 }

 public void mouseReleased(MouseEvent event)
 {
 current = -1;
 }
 });
 addMouseMotionListener(new
 MouseMotionAdapter()
 {
 public void mouseDragged(MouseEvent event)
 {
 if (current == -1) return;
 points[current] = event.getPoint();
 repaint();
 }
 });
 current = -1;
 }
 /**
108. Set a shape maker and initialize it with a
random
109. point set.
110. @param aShapeMaker a shape maker that
defines a shape
111. from a point set
112. */
 public void setShapeMaker(ShapeMaker aShapeMaker)
 {
 shapeMaker = aShapeMaker;
 int n = shapeMaker.getPointCount();
 points = new Point2D[n];
 for (int i = 0; i < n; i++)
 {
 double x = generator.nextDouble() *
getWidth();
 double y = generator.nextDouble() *
getHeight();
 points[i] = new Point2D.Double(x, y);
 }
 repaint();
 }

 public void paintComponent(Graphics g)
 {
 super.paintComponent(g);
 if (points == null) return;
 Graphics2D g2 = (Graphics2D)g;
 for (int i = 0; i < points.length; i++)
 { double x = points[i].getX() - SIZE / 2;
 double y = points[i].getY() - SIZE / 2;
 g2.fill(new Rectangle2D.Double(x, y,
SIZE, SIZE));
 }

 g2.draw(shapeMaker.makeShape(points));
 }

 private Point2D[] points;
 private static Random generator = new Random();
 private static int SIZE = 10;
 private int current;
 private ShapeMaker shapeMaker;
 }
 /**
149. A shape maker can make a shape from a point set.
150. Concrete subclasses must return a shape in the
makeShape
151. method.
152. */
 abstract class ShapeMaker
 {
 /**
 Constructs a shape maker.
 @param aPointCount the number of points
needed to define
 this shape.
 */
 public ShapeMaker(int aPointCount)
 {
 pointCount = aPointCount;
 }
 /**
166. Gets the number of points needed to define
this shape.
167. @return the point count
168. */
 public int getPointCount()
 {
 return pointCount;
 }
 /**
175. Makes a shape out of the given point set.
176. @param p the points that define the shape
177. @return the shape defined by the points
178. */
 public abstract Shape makeShape(Point2D[] p);
 public String toString()
 {
 return getClass().getName();
 }
 private int pointCount;
 }
 /**
190. Makes a line that joins two given points.
191. */
 class LineMaker extends ShapeMaker
 {
 public LineMaker() { super(2); }
 public Shape makeShape(Point2D[] p)
 {
 return new Line2D.Double(p[0], p[1]);
 }
 }
 /**
203. Makes a line that joins two given corner points.
204. */
 class RectangleMaker extends ShapeMaker
 {
 public RectangleMaker() { super(2); }

 public Shape makeShape(Point2D[] p)
 {
 Rectangle2D s = new Rectangle2D.Double();
 s.setFrameFromDiagonal(p[0], p[1]);
 return s;
 }
 }

 /**

218. Makes a round rectangle that joins two given
corner points.
219. */
 class RoundRectangleMaker extends ShapeMaker
 {
 public RoundRectangleMaker() { super(2); }

 public Shape makeShape(Point2D[] p)
 {
 RoundRectangle2D s
 = new RoundRectangle2D.Double(0, 0, 0, 0,
20, 20);
 s.setFrameFromDiagonal(p[0], p[1]);
 return s;
 }
 }
 /**
234. Makes an ellipse contained in a bounding box
with two given
235. corner points.
236. */
 class EllipseMaker extends ShapeMaker
 {
 public EllipseMaker() { super(2); }

 public Shape makeShape(Point2D[] p)
 {
 Ellipse2D s = new Ellipse2D.Double();
 s.setFrameFromDiagonal(p[0], p[1]);
 return s;
 }
 }
 /**
250. Makes an arc contained in a bounding box with
two given
251. corner points, and with starting and ending
angles given
252. by lines emanating from the center of the
bounding box and
253. ending in two given points. To show the
correctness of
Core Java 2, Volume II Advanced Features
By ViTo @ RoR & BT
254. the angle computation, the returned shape
contains the arc,
255. the bounding box, and the lines.
256. */
 class ArcMaker extends ShapeMaker
 {
 public ArcMaker() { super(4); }

 public Shape makeShape(Point2D[] p)
 {
 double centerX = (p[0].getX() + p[1].getX())/ 2;
 double centerY = (p[0].getY() + p[1].getY())/ 2;
 double width = Math.abs(p[1].getX() -p[0].getX());
 double height = Math.abs(p[1].getY() -p[0].getY());
 double distortedStartAngle =Math.toDegrees(Math.atan2(-(p[2].getY() - centerY)* width, (p[2].getX() - centerX) *height));
 double distortedEndAngle=Math.toDegrees(Math.atan2(-(p[3].getY() - centerY)* width, (p[3].getX() - centerX) *height));
 double distortedAngleDifference= distortedEndAngle -distortedStartAngle;
 if (distortedStartAngle < 0)
 distortedStartAngle += 360;
 if (distortedAngleDifference < 0)
 distortedAngleDifference += 360;

 Arc2D s = new Arc2D.Double(0, 0, 0, 0,
 distortedStartAngle,
distortedAngleDifference,
 Arc2D.OPEN);
 s.setFrameFromDiagonal(p[0], p[1]);

 GeneralPath g = new GeneralPath();
 g.append(s, false);
 Rectangle2D r = new Rectangle2D.Double();
 r.setFrameFromDiagonal(p[0], p[1]);
 g.append(r, false);
 Point2D center = new Point2D.Double(centerX,
centerY);
 g.append(new Line2D.Double(center, p[2]),
false);
 g.append(new Line2D.Double(center, p[3]),
false);
 return g;
 }
 }
 /**
299. Makes a polygon defined by six corner points.
300. */
 class PolygonMaker extends ShapeMaker
 {
 public PolygonMaker() { super(6); }
 public Shape makeShape(Point2D[] p)
 {
 GeneralPath s = new GeneralPath();
 s.moveTo((float)p[0].getX(),
(float)p[0].getY());
 for (int i = 1; i < p.length; i++)
 s.lineTo((float)p[i].getX(),
(float)p[i].getY());
 s.closePath();
 return s;
 }
 }
 /**
317. Makes a quad curve defined by two end points
and a control
318. point.
319. */
 class QuadCurveMaker extends ShapeMaker
 {
 public QuadCurveMaker() { super(3); }
 public Shape makeShape(Point2D[] p)
 {
 return new QuadCurve2D.Double(p[0].getX(),
p[0].getY(),
 p[1].getX(), p[1].getY(), p[2].getX(),
p[2].getY());
 }
 }
 /**
332. Makes a cubic curve defined by two end points
and two control
333. points.
334. */
 class CubicCurveMaker extends ShapeMaker
 {
 public CubicCurveMaker() { super(4); }
 public Shape makeShape(Point2D[] p)
 {
 return new CubicCurve2D.Double(p[0].getX(),
p[0].getY(),
 p[1].getX(), p[1].getY(), p[2].getX(),
p[2].getY(),
 p[3].getX(), p[3].getY());
 }
 }