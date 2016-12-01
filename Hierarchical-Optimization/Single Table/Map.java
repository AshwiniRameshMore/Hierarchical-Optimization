import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Map extends JFrame implements ActionListener
{
	JButton b[],path[];
	TextField t;
	String src,dest;
	int prev;

//	JFrame frame2 = new combo1();

	public Map(String title)
	{
		super(title);
		Container contentPane = getContentPane();

		HEPV9 h = new HEPV9();

		int n = h.getNodeCount();
		b = new JButton[n];

		int l = h.getLinkCount();
		path = new JButton[l];

		t = new TextField();

		setLayout(null);

		src = null;
		dest = null;
		prev = -1;

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");
			Statement st = con.createStatement();
			ResultSet rs = null;

			rs = st.executeQuery("select src_name from map");
			int c = 0,c1 = 0;

			while(rs.next())
			{
				b[c] = new JButton(rs.getString("src_name"));
				b[c].setFont(new Font("Arial Narrow",Font.PLAIN,11));
				b[c].setMargin(new Insets(0,0,0,0));
				b[c].addActionListener(this);
				contentPane.add(b[c++]);
				if(c1 < n-1)
				{
					path[c1] = new JButton("");
					path[c1].setFont(new Font("Arial Narrow",Font.BOLD,11));
					path[c1].setMargin(new Insets(-2,-2,-2,-2));
					path[c1].setEnabled(false);
					contentPane.add(path[c1++]);
				}
			}

			b[0].setLocation(130,60);		// Pune Station
			b[0].setSize(100,30);

			b[1].setLocation(70,150);		// Sancheti Hospital
			b[1].setSize(100,30);

			b[2].setLocation(70,225);		// Shivajinagar
			b[2].setSize(100,30);

			b[3].setLocation(240,200);		// Manapa
			b[3].setSize(100,30);

			b[4].setLocation(400,200);		// Shaniwarwada
			b[4].setSize(100,30);

			b[5].setLocation(665,120);		// Swargate
			b[5].setSize(100,30);

			b[6].setLocation(570,200);		// Hirabaug Bus stop
			b[6].setSize(100,30);

			b[7].setLocation(970,190);		// Bharati Vidyapith
			b[7].setSize(100,30);

			b[8].setLocation(750,200);		// Sarasbaug
			b[8].setSize(100,30);

			b[9].setLocation(995,300);		// Katraj
			b[9].setSize(100,30);

			b[10].setLocation(290,280);		// Balgandharva
			b[10].setSize(100,30);

			b[11].setLocation(70,350);		// Crompton Greaves
			b[11].setSize(100,30);

			b[12].setLocation(50,420);		// Aundh
			b[12].setSize(100,30);

			b[13].setLocation(238,350);		// FC
			b[13].setSize(100,30);

			b[14].setLocation(460,350);		// Garware Chowk
			b[14].setSize(100,30);

			b[15].setLocation(830,360);		// Dandekar Bridge
			b[15].setSize(100,30);

			b[16].setLocation(365,440);		// Nal Stop
			b[16].setSize(100,30);

			b[17].setLocation(560,450);		// Mhatre Bridge
			b[17].setSize(100,30);

			b[18].setLocation(325,520);		// Karnataka Highschool
			b[18].setSize(100,30);

			b[19].setLocation(500,590);		// Tathawade Udyan
			b[19].setSize(100,30);

			b[20].setLocation(670,525);		// Rajaram Bridge
			b[20].setSize(100,30);

			b[21].setLocation(900,490);		// Tukainagar
			b[21].setSize(100,30);

			b[22].setLocation(5,550);		// Kothrud Depot
			b[22].setSize(100,30);

			b[23].setLocation(160,550);		// Karve Putla
			b[23].setSize(100,30);

			b[24].setLocation(160,650);		// Kothrud Stand
			b[24].setSize(100,30);

			b[25].setLocation(325,720);		// Karvenagar
			b[25].setSize(100,30);

			b[26].setLocation(820,620);		// Meenakshipuram
			b[26].setSize(100,30);

			b[27].setLocation(1000,650);	// Sinhgad College
			b[27].setSize(100,30);

			b[28].setLocation(820,700);		// Vadgaon (Bk.)
			b[28].setSize(100,30);

			b[29].setLocation(620,720);		// Anandnagar
			b[29].setSize(100,30);



			path[0].setLocation(245,250);	// Pune Station to Sancheti Hospital	1 to 2
			path[0].setSize(8,70);
			path[0].setLabel("\\/");

		/*	path[1].setLocation(245,250);	// Pune Station to Manapa				1 to 4
			path[1].setSize(8,70);
			path[1].setLabel("\\/");

			path[2].setLocation(245,250);	// Pune Station to Swargate				1 to 6
			path[2].setSize(8,70);
			path[2].setLabel("\\/");

			path[3].setLocation(245,250);	// Sancheti Hospital to Pune Station	2 to 1
			path[3].setSize(8,70);
			path[3].setLabel("\\/");

			path[4].setLocation(245,250);	// Sancheti Hospital to Shivajinagar	2 to 3
			path[4].setSize(8,70);
			path[4].setLabel("\\/");

			path[5].setLocation(245,250);	// 3 to 2
			path[5].setSize(8,70);
			path[5].setLabel("\\/");

			path[6].setLocation(245,250);	// 3 to 4
			path[6].setSize(8,70);
			path[6].setLabel("\\/");

			path[7].setLocation(245,250);	// 4 to 1
			path[7].setSize(8,70);
			path[7].setLabel("\\/");

			path[8].setLocation(245,250);	// 4 to 3
			path[8].setSize(8,70);
			path[8].setLabel("\\/");

			path[9].setLocation(245,250);	// 4 to 5
			path[9].setSize(8,70);
			path[9].setLabel("\\/");

			path[10].setLocation(245,250);	// 4 to 11
			path[10].setSize(8,70);
			path[10].setLabel("\\/");

			path[11].setLocation(245,250);	// 5 to 4
			path[11].setSize(8,70);
			path[11].setLabel("\\/");

			path[12].setLocation(245,250);	// 5 to 7
			path[12].setSize(8,70);
			path[12].setLabel("\\/");

			path[13].setLocation(245,250);	// 6 to 1
			path[13].setSize(8,70);
			path[13].setLabel("\\/");

			path[14].setLocation(245,250);	// 6 to 8
			path[14].setSize(8,70);
			path[14].setLabel("\\/");

			path[15].setLocation(245,250);	// 6 to 9
			path[15].setSize(8,70);
			path[15].setLabel("\\/");

			path[16].setLocation(245,250);	// 7 to 5
			path[16].setSize(8,70);
			path[16].setLabel("\\/");

			path[17].setLocation(245,250);	// 7 to 6
			path[17].setSize(8,70);
			path[17].setLabel("\\/");

			path[18].setLocation(245,250);	// 7 to 9
			path[18].setSize(8,70);
			path[18].setLabel("\\/");

			path[19].setLocation(245,250);	// 8 to 6
			path[19].setSize(8,70);
			path[19].setLabel("\\/");

			path[20].setLocation(245,250);	// 8 to 10
			path[20].setSize(8,70);
			path[20].setLabel("\\/");

			path[21].setLocation(245,250);	// 9 to 7
			path[21].setSize(8,70);
			path[21].setLabel("\\/");

			path[22].setLocation(245,250);	// 9 to 16
			path[22].setSize(8,70);
			path[22].setLabel("\\/");

			path[23].setLocation(245,250);	// 10 to 8
			path[23].setSize(8,70);
			path[23].setLabel("\\/");

			path[24].setLocation(245,250);	// 10 to 28
			path[24].setSize(8,70);
			path[24].setLabel("\\/");

			path[25].setLocation(245,250);	// 11 to 4
			path[25].setSize(8,70);
			path[25].setLabel("\\/");

			path[26].setLocation(245,250);	// 11 to 15
			path[26].setSize(8,70);
			path[26].setLabel("\\/");

			path[27].setLocation(245,250);	// 12 to 3
			path[27].setSize(8,70);
			path[27].setLabel("\\/");

			path[28].setLocation(245,250);	// 12 to 13
			path[28].setSize(8,70);
			path[28].setLabel("\\/");

			path[29].setLocation(245,250);	// 13 to 12
			path[29].setSize(8,70);
			path[29].setLabel("\\/");

			path[30].setLocation(245,250);	// 14 to 12
			path[30].setSize(8,70);
			path[30].setLabel("\\/");

			path[31].setLocation(245,250);	// 15 to 14
			path[31].setSize(8,70);
			path[31].setLabel("\\/");

			path[32].setLocation(245,250);	//
			path[32].setSize(8,70);
			path[32].setLabel("\\/");

			path[33].setLocation(245,250);	//
			path[33].setSize(8,70);
			path[33].setLabel("\\/");

			path[34].setLocation(245,250);	//
			path[34].setSize(8,70);
			path[34].setLabel("\\/");

			path[35].setLocation(245,250);	//
			path[35].setSize(8,70);
			path[35].setLabel("\\/");

			path[36].setLocation(245,250);	//
			path[36].setSize(8,70);
			path[36].setLabel("\\/");

			path[37].setLocation(245,250);	//
			path[37].setSize(8,70);
			path[37].setLabel("\\/");

			path[38].setLocation(245,250);	//
			path[38].setSize(8,70);
			path[38].setLabel("\\/");

			path[39].setLocation(245,250);	//
			path[39].setSize(8,70);
			path[39].setLabel("\\/");

			path[40].setLocation(245,250);	//
			path[40].setSize(8,70);
			path[40].setLabel("\\/");

			path[41].setLocation(245,250);	//
			path[41].setSize(8,70);
			path[41].setLabel("\\/");

			path[42].setLocation(245,250);	//
			path[42].setSize(8,70);
			path[42].setLabel("\\/");

			path[43].setLocation(245,250);	//
			path[43].setSize(8,70);
			path[43].setLabel("\\/");

			path[43].setLocation(245,250);	//
			path[43].setSize(8,70);
			path[43].setLabel("\\/");

			path[44].setLocation(245,250);	//
			path[44].setSize(8,70);
			path[44].setLabel("\\/");

			path[45].setLocation(245,250);	//
			path[45].setSize(8,70);
			path[45].setLabel("\\/");

			path[46].setLocation(245,250);	//
			path[46].setSize(8,70);
			path[46].setLabel("\\/");

			path[47].setLocation(245,250);	//
			path[47].setSize(8,70);
			path[47].setLabel("\\/");

			path[48].setLocation(245,250);	//
			path[48].setSize(8,70);
			path[48].setLabel("\\/");

			path[49].setLocation(245,250);	//
			path[49].setSize(8,70);
			path[49].setLabel("\\/");

			path[50].setLocation(245,250);	//
			path[50].setSize(8,70);
			path[50].setLabel("\\/");

			path[51].setLocation(245,250);	//
			path[51].setSize(8,70);
			path[51].setLabel("\\/");

			path[52].setLocation(245,250);	//
			path[52].setSize(8,70);
			path[52].setLabel("\\/");

			path[53].setLocation(245,250);	//
			path[53].setSize(8,70);
			path[53].setLabel("\\/");

			path[54].setLocation(245,250);	//
			path[54].setSize(8,70);
			path[54].setLabel("\\/");

			path[55].setLocation(245,250);	//
			path[55].setSize(8,70);
			path[55].setLabel("\\/");

			path[56].setLocation(245,250);	//
			path[56].setSize(8,70);
			path[56].setLabel("\\/");

			path[57].setLocation(245,250);	//
			path[57].setSize(8,70);
			path[57].setLabel("\\/");

			path[58].setLocation(245,250);	//
			path[58].setSize(8,70);
			path[58].setLabel("\\/");

			path[59].setLocation(245,250);	//
			path[59].setSize(8,70);
			path[59].setLabel("\\/");

			path[60].setLocation(245,250);	//
			path[60].setSize(8,70);
			path[60].setLabel("\\/");

			path[61].setLocation(245,250);	//
			path[61].setSize(8,70);
			path[61].setLabel("\\/");

			path[62].setLocation(245,250);	//
			path[62].setSize(8,70);
			path[62].setLabel("\\/");

			path[63].setLocation(245,250);	//
			path[63].setSize(8,70);
			path[63].setLabel("\\/");

			path[64].setLocation(245,250);	//
			path[64].setSize(8,70);
			path[64].setLabel("\\/");

			path[65].setLocation(245,250);	//
			path[65].setSize(8,70);
			path[65].setLabel("\\/");

			path[66].setLocation(245,250);	//
			path[66].setSize(8,70);
			path[66].setLabel("\\/");

			path[67].setLocation(245,250);	//
			path[67].setSize(8,70);
			path[67].setLabel("\\/");

			path[68].setLocation(245,250);	//
			path[68].setSize(8,70);
			path[68].setLabel("\\/");

			path[69].setLocation(245,250);	//
			path[69].setSize(8,70);
			path[69].setLabel("\\/");

			path[70].setLocation(245,250);	//
			path[70].setSize(8,70);
			path[70].setLabel("\\/");

			path[71].setLocation(245,250);	//
			path[71].setSize(8,70);
			path[71].setLabel("\\/");

			path[72].setLocation(245,250);	//
			path[72].setSize(8,70);
			path[72].setLabel("\\/");

			path[73].setLocation(245,250);	//
			path[73].setSize(8,70);
			path[73].setLabel("\\/");

			path[74].setLocation(245,250);	//
			path[74].setSize(8,70);
			path[74].setLabel("\\/");

			path[75].setLocation(245,250);	//
			path[75].setSize(8,70);
			path[75].setLabel("\\/");

			path[76].setLocation(245,250);	//
			path[76].setSize(8,70);
			path[76].setLabel("\\/");

			path[77].setLocation(245,250);	//
			path[77].setSize(8,70);
			path[77].setLabel("\\/");

			path[78].setLocation(245,250);	//
			path[78].setSize(8,70);
			path[78].setLabel("\\/");

			path[79].setLocation(245,250);	//
			path[79].setSize(8,70);
			path[79].setLabel("\\/");

			path[80].setLocation(245,250);	//
			path[80].setSize(8,70);
			path[80].setLabel("\\/");

			path[81].setLocation(245,250);	//
			path[81].setSize(8,70);
			path[81].setLabel("\\/");

			path[82].setLocation(245,250);	//
			path[82].setSize(8,70);
			path[82].setLabel("\\/");*/


			t.setLocation(0,0);
			t.setSize(70,30);
			contentPane.add(t);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

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
		t.setText("");
		for(int i = 0;i < b.length;i++)
		{
			if(ae.getSource() == b[i])
			{
				if(src == null)
				{
					src = b[i].getLabel();
				}
				else
					dest = b[i].getLabel();
			}
		}
		if(dest != null)
		{
			HEPV9 h = new HEPV9();
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");
				Statement st = con.createStatement();
				ResultSet rs = null;

				rs = st.executeQuery("select src from map where src_name = '" + src + "'");
				if(rs.next())
					src = rs.getString("src");
				rs.close();

				rs = st.executeQuery("select src from map where src_name = '" + dest + "'");
				if(rs.next())
					dest = rs.getString("src");
				rs.close();

				st.close();
				con.close();

				prev = -1;
				t.setText(h.pathRetrieval(src,dest,this) + "");
				//	frame2.setVisible(true);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			src = null;
			dest = null;

		}
	}

	public void highlight(String node)
	{
		int i = Integer.parseInt(node) - 1;
		if(i == (Integer.parseInt(src)-1))
			b[i].setBackground(Color.RED);
		else if(i == (Integer.parseInt(dest) - 1))
			b[i].setBackground(Color.RED);
		else
			b[i].setBackground(Color.CYAN);
		if(prev >= 0)
		{
			// highlight line
			path[prev].setBackground(Color.CYAN);
			path[prev].setFont(new Font("Arial Narrow",Font.PLAIN,11));
			path[prev].setText("<html><font color=black>" + path[prev].getText() + "</font></html>");
			System.out.println("\nprev = " + prev);
		}
		prev = i;
	}

}