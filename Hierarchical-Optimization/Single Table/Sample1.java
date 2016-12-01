import java.awt.*;
import javax.swing.*;

class Sample1 extends JFrame
{
	JButton b;
	public Sample1(String title)
	{
			super(title);
		try{


		Container contentPane = getContentPane();
		b = new JButton("ok");
		b.setLocation(50,50);
		b.setSize(100,100);
		contentPane.add(b);
		setLayout(null);
		}
		catch(Exception e)
		{
		}
	}
}