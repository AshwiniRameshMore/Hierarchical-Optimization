import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Encode extends JFrame implements ActionListener
{
	JButton b[];
	JLabel lb;
	int n=3;

	public Encode(String title)
	{

		super(title);
		Container contentPane = getContentPane();
			contentPane.setBackground(Color.pink);
			contentPane.setSize(700,100);
			b = new JButton[n];
		b[0] = new JButton("encode supergraph");
		setLayout(null);
		b[0].setLocation(120,120);
		b[0].setSize(150,30);
		contentPane.add(b[0]);
		b[0].addActionListener(this);

		b[1] = new JButton("Updation");
		setLayout(null);
		b[1].setLocation(520,120);
		b[1].setSize(150,30);
		contentPane.add(b[1]);
		b[1].addActionListener(this);


		 	b[2] = new JButton("Logout");
		setLayout(null);
		b[2].setLocation(920,120);
		b[2].setSize(150,30);
		contentPane.add(b[2]);
		b[2].addActionListener(this);



		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent ae)
	{
		HEPV9 h = new HEPV9();
		h.createHEPV();
		if(ae.getSource()==b[0])
		{

		JOptionPane.showMessageDialog(null, "SUCCESSFULLY ENCODED..", "ENCODE COMPLETE", 1);
		this.setVisible(true);
		}
		if(ae.getSource()==b[1])
		{
			this.setVisible(false);
			JFrame frame1 = new combo1();
		//	frame1.setVisble(true);

		}
		if(ae.getSource()==b[2])
		{
			this.setVisible(false);
			page11 myObj = new page11();
			myObj.show();
		}
	}
}