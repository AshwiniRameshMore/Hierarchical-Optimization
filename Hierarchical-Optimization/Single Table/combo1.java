import java.util.*;
import java.io.*;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


public class combo1 extends JFrame implements ItemListener, ActionListener
{
		JComboBox ComboBox,ComboBox1;
		JTextField txt,txt1,txt2,txt3;
		JLabel label1,label2,label3,label4;
		JButton b1,b2,b3;
		static int  cntr = 0;

			Next [] obj = new Next[2];
		public static void main(String args[])
	{
			new combo1();
	}

		public combo1()
		{
				String course;
				JFrame f = new JFrame("Updation of Link Weights");
        		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 				f.setVisible(true);
				f.setLayout(new GridLayout(6,2));

  				label1=new JLabel("Select Source ");
				label1.setForeground (Color.black);
				f.add(label1);

  				ComboBox = new JComboBox();
				f.add(ComboBox);
  				ComboBox.addItemListener(this);

  				label2=new JLabel("Select Destination ");
				label2.setForeground (Color.black);
				f.add(label2);

  				ComboBox1 = new JComboBox();
  				f.add(ComboBox1);
  				ComboBox1.addItemListener(this);

				label3=new JLabel("Link Weight");
				label3.setForeground (Color.black);
				f.add(label3);

				txt1 = new JTextField(10);
				txt1.setForeground (Color.red);
				txt1.setEnabled(false);
				txt1.setText("");
				f.add(txt1);

				label4=new JLabel("Enter link weight");
				label4.setForeground (Color.black);
				f.add(label4);

				txt2 = new JTextField(10);
				f.add(txt2);

				b3=new JButton("Next");
				f.add(b3);
				b3.addActionListener(this);

				b1=new JButton("Update");
				f.add(b1);
				b1.addActionListener(this);

				b2=new JButton("exit");
				f.add(b2);
				b2.addActionListener(this);

  				ComboBox.setBackground(Color.white);
  				ComboBox.setForeground(Color.black);

  				ComboBox1.setBackground(Color.white);
  				ComboBox1.setForeground(Color.black);

  				txt = new JTextField(10);
  				txt.setVisible(false);

  				f.setSize(550,400);
  				f.setVisible(true);
  				setLocationRelativeTo(null);
		try{

						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

						Connection con = null;
						ResultSet rs = null;

						con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

						Statement st = con.createStatement();
						rs = st.executeQuery("select src_name from map order by src_name asc");

						while(rs.next())
						{
								 course = rs.getString("src_name");
		   			   		   	ComboBox.addItem(course);
						}

						rs.close();
						st.close();
						con.close();
					}
					catch(Exception e)
					{
							System.out.println(e);
					}
}

	public void itemStateChanged(ItemEvent ie)
	{
				String value1=null,value2=null,type=null;
  						float weight=0;
  				try
  				{
  					if(ie.getSource()== ComboBox){


  						String course1=null,selectd=null;

  						String str = (String)ComboBox.getSelectedItem();
  						txt.setText(str);
  						String value = txt.getText();

  						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

						Connection con = null;
						ResultSet rs1= null;
						ResultSet rs2 = null;

						con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

						Statement st = con.createStatement();

						rs2= st.executeQuery("select src from map where src_name = '" + value+"'" );
						if (rs2.next()){
						selectd = rs2.getString("src");
						}

						rs1 = st.executeQuery("select src_name from map where src IN (select dest from flat_graph where src ='" +selectd+"')");
						ComboBox1.removeAllItems();
						while(rs1.next())
						{

						   course1 = rs1.getString("src_name");

						   System.out.println(course1);
						   ComboBox1.addItem(course1);

						}
						rs1.close();
						rs2.close();
						st.close();
						con.close();
  					}

  					else
  					{
						if(ie.getSource()==ComboBox1)
  					{
							String str1 = (String)ComboBox.getSelectedItem();

							String str2 = (String)ComboBox1.getSelectedItem();

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

						Connection con1 = null;
						ResultSet rs3= null;
						ResultSet rs4 = null;
						ResultSet rs5 = null;

					con1 = DriverManager.getConnection("jdbc:odbc:flat","system","system");

						Statement st1 = con1.createStatement();
						rs3=st1.executeQuery("select src from map where src_name = '"+str1+"'" );
							if (rs3.next()){
							value1 = rs3.getString("src");
							}

						rs4=st1.executeQuery("select src from map where src_name = '"+str2+"'" );

						if (rs4.next()){
							 value2 = rs4.getString("src");
							 }

						rs5= st1.executeQuery("select wt from flat_graph where src='"+value1+"' and dest='"+value2+"'");
						if(rs5.next()){
							weight = rs5.getFloat("wt");

						}

						 type = String.valueOf(weight);

						txt1.setText(type);

						rs3.close();
						rs4.close();
						rs5.close();
						st1.close();
						con1.close();
  					}
  			}

}
  				catch(Exception e)
  				{
  					System.out.println(e);
  				}

  		}
  		public void actionPerformed(ActionEvent e)
  		{
  			int  i = 0;
  			float wts=0;

  			if(e.getSource() == b3)
  			{

  				String src,dests;
				float wt = 0.0f;
				src = null;
				dests= null;
  				System.out.println("b3....");
  			String source=(String)ComboBox.getSelectedItem();

  				String dest=(String)ComboBox1.getSelectedItem();
	  			wts=Float.valueOf(txt2.getText());

		//	for(i=0;i<obj.length;i++)
  		//	{
  				System.out.println("ashu");
					obj[cntr]=new Next(source,dest,wts);
  					//System.out.println("xxx==" +obj[i].src);

  						try
				{

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con = DriverManager.getConnection("jdbc:odbc:flat","system","system");
					Statement st = con.createStatement();
					ResultSet rs = null;

				//	String source=(String)ComboBox.getSelectedItem();
					rs = st.executeQuery("select src from map where src_name = '" + source+ "'");
					if(rs.next())
						src = rs.getString("src");
					rs.close();

  				//	String dest=(String)ComboBox1.getSelectedItem();
  					rs = st.executeQuery("select src from map where src_name = '" + dest + "'");
					if(rs.next())
						dests = rs.getString("src");
					rs.close();

	  				wt=Float.valueOf(txt2.getText());
   					obj[cntr].src=src;
   				//	System.out.println("xxx==" +obj[i].src);
  					obj[cntr].dest = dests;
  					obj[cntr].wt = wt;
			//		i++;
					cntr++;
  					src=null;
  					dests=null;
  					wt=0.0f;
  			}
  			catch(Exception ei){

  			}
  			}



		 if(e.getSource()==b1)
		{
				HEPV9  h = new HEPV9();
				h.HEPVUpdate(obj);
				/*	source=null;
  					dest=null;
  					wts=0;*/
					JOptionPane.showMessageDialog(null, "SUCCESSFULLY updated..", "update COMPLETE", 1);
					this.setVisible(true);
			}


  				if(e.getSource()==b2)
  				{	this.setVisible(false);
  					Encode myObj=new Encode("encode");
  						myObj.show();
  				}


  		}

	}