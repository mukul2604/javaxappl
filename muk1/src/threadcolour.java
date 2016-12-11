import java.awt.*;
import javax.swing.*;
import java.lang.Thread.*;

class mukt extends java.lang.Thread implements Runnable
{

JPanel p=new JPanel();
JPanel p2=new JPanel();
JPanel p3=new JPanel();
JPanel p4=new JPanel();
JPanel p1=new JPanel();

JFrame frame=new JFrame();
JLabel l=new JLabel("Welcome");
//JButton b=new JButton("Start");

	int x,y,z;

    @Override
	public void run()
	{
	try{
       
       
       
        frame.add(p,BorderLayout.CENTER);
        frame.add(p2,BorderLayout.EAST);
        frame.add(p3,BorderLayout.WEST);
        frame.add(p1,BorderLayout.SOUTH);
        frame.add(p4,BorderLayout.NORTH);
        

        frame.setSize(255, 255);
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	for(x=0;x<=255;x=x+30)
    {
        for(y=0;y<=255;y=y+20)
      {for(z=0;z<=255;z=z+10)
			{
				p.setBackground(new Color(x,y,z));
               
                p2.setBackground(new Color(z,y,x));
                p3.setBackground(new Color(y,z,x));
                p4.setBackground(new Color(x,z,y));
                p1.setBackground(new Color(z,x,y));
				sleep(50);
			}
       sleep(50);
      }
    sleep(50);
    }
}
	catch(Exception e)
	{
l.setText("Error!!");
	}


	}

}

public class threadcolour extends java.lang.Thread
{
    @SuppressWarnings("empty-statement")
	public static void main(String[] args) throws Exception
	{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());;
		Runnable r=new mukt();
		Thread t=new Thread(r);
        //hread t1=new Thread(r);
		t.start();
        //sleep(1000);
        //t1.start();
	}
}