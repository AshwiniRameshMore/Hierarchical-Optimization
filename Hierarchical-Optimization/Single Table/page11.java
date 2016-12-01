import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class page11 extends JFrame implements ActionListener
{
	JButton blog,bnew,bexit;
	JTextField tuser,tdist;
	JPasswordField tpass;
	JLabel luser,lpass,ldist,lclick;
	HEPV9 h;

	public static void main(String args[])
	{
		new page11();
	}

	public page11()
	{
		super("LOGIN");
			h = new HEPV9();

		Container content=getContentPane();
		content.setBackground(Color.pink);
		content.setLayout(new FlowLayout());

		luser=new JLabel("Enter Username");
		luser.setForeground (Color.black);
		content.add(luser);

     	tuser=new JTextField(10);
		content.add(tuser);

		lpass=new JLabel("Enter Password ");
		lpass.setForeground (Color.black);
		content.add(lpass);

		tpass=new JPasswordField(10);
		content.add(tpass);

		bnew=new JButton("New User");
		content.add(bnew);
		bnew.addActionListener(this);

	    blog=new JButton("Login");
	  	content.add(blog);
		blog.addActionListener(this);

		bexit=new JButton("Exit");
		content.add(bexit);
		bexit.addActionListener(this);


		setSize(300,130);
		setVisible(true);
		setLocationRelativeTo(null);
	}

 		public void actionPerformed(ActionEvent e)
 		{
			String value1=tuser.getText();
			String value2=tpass.getText();

    	 	if(e.getSource()==blog)
			{
					if(value1 !="b" && value2 != "b")
					 {
						try{

								Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
								Connection con = null;
								ResultSet rs = null;
								con = DriverManager.getConnection("jdbc:odbc:flat","system","system");
								Statement st = con.createStatement();
								rs = st.executeQuery("select * from Log");
								while(rs.next())
								{
										String v1 = rs.getString(1);
										String v2 = rs.getString(2);
										if(value1.equals(v1) && value2.equals(v2))
										{
												System.out.println(value1);
												this.setVisible(false);
												//call map form
												JFrame frame2 = new Map11();
												frame2.repaint();
												frame2.setVisible(true);
												//frame2.setBackground(Color.pink);
												frame2.setExtendedState(frame2.getExtendedState()|JFrame.MAXIMIZED_BOTH);
										}
								}
								rs.close();
								st.close();
								con.close();
							}
							catch(Exception aer)
							{
							}
			}
			 if(value1.equals("b") && value2.equals("b"))
			{
				    this.setVisible(false);
					// call admin form(encode button,update form,map form)
					JFrame frame = new Encode("Encode");
					frame.setVisible(true);
					//frame.setSize(700,400);
					//frame.setBackground(Color.pink);
				frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			}
		//	else
		//	{
		//			JOptionPane.showMessageDialog(this,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE);
		//	}
		}
			if(e.getSource()==bexit)
			{
				System.exit(1);
			}

		    if(e.getSource()==bnew)
		    {
				this.setVisible(false);
			    new Frame1();
    		}
 		}

 public class Frame1 extends JFrame implements ActionListener
{
	JButton blog1,bexit1;
	JTextField tuser1;
	JPasswordField tpass1;
	JLabel luser1,lpass1;

     public Frame1()
        		{
        				super("WELCOME");

								Container content1=getContentPane();
								content1.setBackground(Color.pink);
								content1.setLayout(new FlowLayout());

								luser1=new JLabel("Enter Username");
		luser1.setForeground (Color.black);
		content1.add(luser1);

     	tuser1=new JTextField(10);
		content1.add(tuser1);

		lpass1=new JLabel("Enter Password ");
		lpass1.setForeground (Color.black);
		content1.add(lpass1);

		tpass1=new JPasswordField(10);
		content1.add(tpass1);

	    blog1=new JButton("Create");
	  	content1.add(blog1);
		blog1.addActionListener(this);

		bexit1=new JButton("Exit");
		content1.add(bexit1);
		bexit1.addActionListener(this);

		setSize(300,130);
		setVisible(true);
		setLocationRelativeTo(null);

       }

       public void actionPerformed(ActionEvent e1)
        {
        	try{

        	if(e1.getSource()==bexit1)
			{			System.exit(1);
			}

			if(e1.getSource()==blog1)
			{
					String val1,val2;
					val1 = tuser1.getText();

					val2 = tpass1.getText();

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;

					con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

					Statement st = con.createStatement();
					rs = st.executeQuery("insert into Log values('"+val1+"','"+val2+"')");

					rs.close();
					st.close();
					con.close();
					JOptionPane.showMessageDialog(null, "I am happy.");

			}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e);
        	}
        }
}



}




