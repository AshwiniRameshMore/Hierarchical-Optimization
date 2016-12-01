import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Map1 extends JFrame implements ActionListener
{
	JButton b[];
	TextField t;
	String src,dest;
	int path[],indx;
	HEPV9 h;

	public Map1(String title)
	{
		super(title);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		Container contentPane = getContentPane();

		h = new HEPV9();

		int n = h.getNodeCount();
		b = new JButton[n];

		int l = h.getLinkCount();
		path = new int[l];
		indx = 0;

		t = new TextField();

		src = null;
		dest = null;

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");
			Statement st = con.createStatement();
			ResultSet rs = null;

			rs = st.executeQuery("select src_name from map");
			int c = 0;

			while(rs.next())
			{
				b[c] = new JButton(rs.getString("src_name"));
				b[c].setFont(new Font("Arial Narrow",Font.PLAIN,11));
				b[c].setMargin(new Insets(0,0,0,0));
				b[c].addActionListener(this);
				panel.add(b[c++]);
			}

			b[0].setLocation(190,60);		// Pune Station
			b[0].setSize(100,30);


			b[1].setLocation(130,150);		// Sancheti Hospital
			b[1].setSize(100,30);

			b[2].setLocation(130,225);		// Shivajinagar
			b[2].setSize(100,30);

			b[3].setLocation(300,200);		// Manapa
			b[3].setSize(100,30);

			b[4].setLocation(460,200);		// Shaniwarwada
			b[4].setSize(100,30);

			b[5].setLocation(745,120);		// Swargate
			b[5].setSize(100,30);

			b[6].setLocation(645,200);		// Hirabaug Bus stop
			b[6].setSize(100,30);

			b[7].setLocation(1070,190);		// Bharati Vidyapeeth
			b[7].setSize(100,30);

			b[8].setLocation(825,200);		// Sarasbaug
			b[8].setSize(100,30);

			b[9].setLocation(1060,300);		// Katraj
			b[9].setSize(100,30);

			b[10].setLocation(350,280);		// Balgandharva
			b[10].setSize(100,30);

			b[11].setLocation(130,350);		// Crompton Greaves
			b[11].setSize(100,30);

			b[12].setLocation(110,420);		// Aundh
			b[12].setSize(100,30);

			b[13].setLocation(298,350);		// FC
			b[13].setSize(100,30);

			b[14].setLocation(520,350);		// Garware Chowk
			b[14].setSize(100,30);

			b[15].setLocation(900,360);		// Dandekar Bridge
			b[15].setSize(100,30);

			b[16].setLocation(425,440);		// Nal Stop
			b[16].setSize(100,30);

			b[17].setLocation(640,450);		// Mhatre Bridge
			b[17].setSize(100,30);

			b[18].setLocation(385,520);		// Karnataka Highschool
			b[18].setSize(100,30);

			b[19].setLocation(560,590);		// Tathawade Udyan
			b[19].setSize(100,30);

			b[20].setLocation(750,525);		// Rajaram Bridge
			b[20].setSize(100,30);

			b[21].setLocation(1050,490);	// Tukainagar
			b[21].setSize(100,30);

			b[22].setLocation(65,550);		// Kothrud Depot
			b[22].setSize(100,30);

			b[23].setLocation(220,550);		// Karve Putla
			b[23].setSize(100,30);

			b[24].setLocation(220,650);		// Kothrud Stand
			b[24].setSize(100,30);

			b[25].setLocation(385,700);		// Karvenagar
			b[25].setSize(100,30);

			b[26].setLocation(890,620);		// Meenakshipuram
			b[26].setSize(100,30);

			b[27].setLocation(1070,650);	// Sinhgad College
			b[27].setSize(100,30);

			b[28].setLocation(900,700);		// Vadgaon (Bk.)
			b[28].setSize(100,30);

			b[29].setLocation(700,700);		// Anandnagar
			b[29].setSize(100,30);

			t.setLocation(0,0);
			t.setSize(70,30);
			panel.add(t);

			contentPane.add(panel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				//System.exit(0);
				page11 myObj = new page11();
			myObj.show();
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

				t.setText(h.pathRetrieval(src,dest,this) + "");
				System.out.print("\naction indx = " + indx + "\tpath[" + indx + "] = " + path[indx]);
				repaint();

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			src = null;
			dest = null;
			indx = 0;
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

		// highlight line
		path[indx++] = i+1;
		System.out.println("\n highlight indx = " + indx + "\tpath[" + (indx-1) + "] = " + path[(indx-1)]);
	}

	public void paint(Graphics g)
	{

    	g.drawLine(250,220,270,450);
		System.out.print("\nindx = " + indx);
		for(int i = 1;path[i] != 0;i++)
		{
			System.out.print("\npath[" + i + "] = " + path[i]);

			if(path[i] == 2 && path[i-1] == 1)		//Pune Station and Sancheti Hospital
			{
				g.drawLine(240,120,180,170);
			}
		//	if()
		}
		//g.drawLine(250,220,270,450);

	}
}