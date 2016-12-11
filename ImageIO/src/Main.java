 import java.awt.event.*;
 import java.awt.image.*;
 import java.io.*;
 import java.util.*;
 import java.util.List;
 import javax.imageio.*;
 import javax.imageio.stream.*;
 import javax.swing.*;


 public class Main
 {
 public static void main(String[] args) throws Exception
 {
     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
 JFrame frame = new ImageIOFrame();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE
);
 frame.show();
 }
 }
 /**
27. This frame displays the loaded images. The menu
has items
28. for loading and saving files.
29. */
 class ImageIOFrame extends JFrame
 {
 public ImageIOFrame()
 {
 setTitle("ImageIOTest");
 setSize(WIDTH, HEIGHT);

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
 JMenu saveMenu = new JMenu("Save");
 fileMenu.add(saveMenu);
 Iterator iter = writerFormats.iterator();
 while (iter.hasNext())
 {
 final String formatName =
(String)iter.next();
 JMenuItem formatItem = new
JMenuItem(formatName);
 saveMenu.add(formatItem);
 formatItem.addActionListener(new
 ActionListener()
 {
 public void
actionPerformed(ActionEvent event)
 {
 saveFile(formatName);
 }
 });
 }

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
 JMenuBar menuBar = new JMenuBar();
 menuBar.add(fileMenu);
 setJMenuBar(menuBar);
 }
 /**
85. Open a file and load the images.
86. */
 public void openFile()
 {

 JFileChooser chooser = new JFileChooser();
 chooser.setCurrentDirectory(new
File("."));

 chooser.setFileFilter(new
 javax.swing.filechooser.FileFilter()
 {
 public boolean accept(File f)
 {
if (f.isDirectory()) return true;
 String name = f.getName();
 int p = name.lastIndexOf('.');
 if (p == -1) return false;
 String suffix
 = name.substring(p +
1).toLowerCase();
 return
readerSuffixes.contains(suffix);
 }
 public String getDescription()
 {
 return "Image files";
 }
 });
 int r = chooser.showOpenDialog(this);
 if (r != JFileChooser.APPROVE_OPTION)
return;
 File f = chooser.getSelectedFile();
 Box box = Box.createVerticalBox();
 try
 {
 String name = f.getName();
 String suffix
 = name.substring(name.lastIndexOf('.')
+ 1);
 Iterator iter
 =
ImageIO.getImageReadersByFormatName(suffix);
 ImageReader reader =
(ImageReader)iter.next();
 ImageInputStream imageIn
 = ImageIO.createImageInputStream(f);
 reader.setInput(imageIn);
 int count = reader.getNumImages(true);
 images = new BufferedImage[count];
 for (int i = 0; i < count; i++)
 {
 images[i] = reader.read(i);
 box.add(new JLabel(new
ImageIcon(images[i])));
 }
 }
 catch (IOException exception)
 {
 JOptionPane.showMessageDialog(this,
exception);
 }
 setContentPane(new JScrollPane(box));
 validate();
 }
 /**
142. Save the current image in a file
143. @param formatName the file format
144. */
 public void saveFile(final String formatName)
 {
 if (images == null) return;
 Iterator iter =
ImageIO.getImageWritersByFormatName(
 formatName);
ImageWriter writer =
(ImageWriter)iter.next();
 final List writerSuffixes = Arrays.asList(

writer.getOriginatingProvider().getFileSuffixes());
 JFileChooser chooser = new JFileChooser();
 chooser.setCurrentDirectory(new
File("."));
chooser.setFileFilter(new
 javax.swing.filechooser.FileFilter()
 {
 public boolean accept(File f)
 {
 if (f.isDirectory()) return true;
 String name = f.getName();
 int p = name.lastIndexOf('.');
 if (p == -1) return false;
 String suffix
 = name.substring(p +
1).toLowerCase();
 return
writerSuffixes.contains(suffix);
 }
 public String getDescription()
 {
 return formatName + " files";
 }
 });
 int r = chooser.showSaveDialog(this);
 if (r != JFileChooser.APPROVE_OPTION)
return;
 File f = chooser.getSelectedFile();
 try
 {
 ImageOutputStream imageOut
 = ImageIO.createImageOutputStream(f);
 writer.setOutput(imageOut);
 writer.write(new IIOImage(images[0],
null, null));
 for (int i = 1; i < images.length; i++)
 {
 IIOImage iioImage
 = new IIOImage(images[i], null,
null);
 if (writer.canInsertImage(i))
 writer.writeInsert(i, iioImage,
null);
 }
 }
 catch (IOException exception)
 {
 JOptionPane.showMessageDialog(this,
exception);
 }
 }
 /**
Core Java 2, Volume II Advanced Features
By ViTo @ RoR & BT
200. Gets a set of all file suffixes that are
recognized
201. by image readers.
202. @return the file suffix set
203. */
 public static Set getReaderSuffixes()
 {
 TreeSet readerSuffixes = new TreeSet();
 String[] informalNames =
ImageIO.getReaderFormatNames();
 for (int i = 0; i < informalNames.length; i++)
 {
 Iterator iter =
ImageIO.getImageReadersByFormatName(
 informalNames[i]);
 while (iter.hasNext())
 {
 ImageReader reader =
(ImageReader)iter.next();
 String[] s =
reader.getOriginatingProvider()
 .getFileSuffixes();

readerSuffixes.addAll(Arrays.asList(s));
 }
 }
 return readerSuffixes;
}
 /**
224. Gets a set of "preferred" format names of all
225. image writers. The preferred format name is
the first
226. format name that a writer specifies.
227. @return the format name set
228. */
 public static Set getWriterFormats()
 {
 TreeSet writerFormats = new TreeSet();
 TreeSet formatNames = new
TreeSet(Arrays.asList(
 ImageIO.getWriterFormatNames()));
 while (formatNames.size() > 0)
 {
 String name =
(String)formatNames.iterator().next();
 Iterator iter =
ImageIO.getImageWritersByFormatName(
 name);
 ImageWriter writer =
(ImageWriter)iter.next();
 String[] names =
writer.getOriginatingProvider()
 .getFormatNames();
 writerFormats.add(names[0]);

formatNames.removeAll(Arrays.asList(names));
 }
 return writerFormats;
 }
 private BufferedImage[] images;
 private static Set readerSuffixes =
getReaderSuffixes();
 private static Set writerFormats =
getWriterFormats();
 private static final int WIDTH = 400;
 private static final int HEIGHT = 400;
 }