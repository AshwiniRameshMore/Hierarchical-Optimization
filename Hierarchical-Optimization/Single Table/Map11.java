import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Map11 extends JFrame implements ActionListener
{

	JButton b[],b1;
	TextField t;
	String src,dest;
	int path[],indx;
	HEPV9 h;
	JLabel label1,label2,L1,L2,L3,L4,L5,L6,L7,L8,L9,L10,L11,L12,L13,L14,L15,L16,L17,L18,L19,L20,L21,L22,L23,L24,L25,L26;
	JLabel L27,L28,L29,L30,L31,L32,L33,L34,L35,L36,L37,L38,L39,L40,L41,L42,L43,L44,L45,L46;

public static void main(String[] args) throws Exception
{
		new Map11();
	}

public Map11()
{

super("	OPTIMAL PATH RETRIEVAL");
	h = new HEPV9();

		int n = h.getNodeCount();
		b = new JButton[n];

//		label = new JLabel[85];
//		add(label[85]);

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
				b[c].setFont(new Font("Arial Narrow",Font.BOLD,11));
				b[c].setMargin(new Insets(0,0,0,0));
				b[c].addActionListener(this);
				add(b[c++]);
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

			b[21].setLocation(1010,490);	// Tukainagar
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

				b1 = new JButton("Refresh");
				b1.setFont(new Font("Arial Narrow",Font.BOLD,15));
				b1.setMargin(new Insets(0,0,0,0));
				b1.setLocation(900,30);
				b1.setSize(150,40);
				b1.addActionListener(this);
				add(b1);

				label2=new JLabel("Optimal Path ");
  				label2.setLocation(500,10);
				label2.setSize (900,70);
				label2.setFont(new Font("Arial Narrow",Font.BOLD,24));
				//label2.setColor(Color.RED);
				add(label2);

				t.setLocation(680,30);
				t.setSize(100,30);
				add(t);


  				label1=new JLabel("Click on Source and Destination ");
  				label1.setLocation(30,10);
				label1.setSize (1100,70);
				label1.setFont(new Font("Arial Narrow",Font.BOLD,30));
				add(label1);


				L1=new JLabel("3.3km ");
  				L1.setLocation(160,90);
				L1.setSize (100,10);
				add(L1);

				L2=new JLabel("0.65km ");
  				L2.setLocation(150,180);
				L2.setSize (100,10);
				add(L2);

				L3=new JLabel("0.3km");
  				L3.setLocation(140,300);
				L3.setSize (100,10);
				add(L3);

				L4=new JLabel("3km ");
  				L4.setLocation(140,400);
				L4.setSize (100,10);
				add(L4);

				L5=new JLabel("1km ");
  				L5.setLocation(240,380);
				L5.setSize (100,10);
				add(L5);


				L6=new JLabel("1.3km");
  				L6.setLocation(450,370);
				L6.setSize (100,10);
				add(L6);

				L7=new JLabel("1.1km ");
  				L7.setLocation(490,350);
				L7.setSize (100,10);
				add(L7);

				L8=new JLabel("0.75km ");
  				L8.setLocation(380,240);
				L8.setSize (100,10);
				add(L8);

				L9=new JLabel("3.3km ");
  				L9.setLocation(200,80);
				L9.setSize (100,10);
				add(L9);

				L10=new JLabel("2km ");
  				L10.setLocation(240,200);
				L10.setSize (100,10);
				add(L10);

				L11=new JLabel("2.6km ");
  				L11.setLocation(320,150);
				L11.setSize (100,10);
				add(L11);

				L12=new JLabel("0.75km ");
  				L12.setLocation(400,190);
				L12.setSize (100,10);
				add(L12);

				L13=new JLabel("2.4km ");
  				L13.setLocation(560,190);
				L13.setSize (100,10);
				add(L13);

				L14=new JLabel("0.6km ");
  				L14.setLocation(700,150);
				L14.setSize (100,10);
				add(L14);

				L15=new JLabel("0.95km ");
  				L15.setLocation(720,200);
				L15.setSize (100,10);
				add(L15);

				L16=new JLabel("0.6km");
  				L16.setLocation(860,170);
				L16.setSize (100,10);
				add(L16);

				L17=new JLabel("5km ");
  				L17.setLocation(900,160);
				L17.setSize (100,10);
				add(L17);

				L18=new JLabel("2km ");
  				L18.setLocation(1170,260);
				L18.setSize (100,10);
				add(L18);

				L19=new JLabel("1.2km ");
  				L19.setLocation(920,300);
				L19.setSize (100,10);
				add(L19);

				L20=new JLabel("2.1km ");
  				L20.setLocation(750,365);
				L20.setSize (100,10);
				add(L20);

					L21=new JLabel("1.5km");
  				L21.setLocation(520,400);
				L21.setSize (100,10);
				add(L21);


					L22=new JLabel("0.8km ");
  				L22.setLocation(550,455);
				L22.setSize (100,10);
				add(L22);


					L23=new JLabel("2.6km ");
  				L23.setLocation(750,500);
				L23.setSize (100,10);
				add(L23);


					L24=new JLabel("2km ");
  				L24.setLocation(510,650);
				L24.setSize (100,10);
				add(L24);

					L25=new JLabel("2.1km ");
  				L25.setLocation(330,545);
				L25.setSize (100,10);
				add(L25);

					L26=new JLabel("1.4km ");
  				L26.setLocation(500,565);
				L26.setSize (100,10);
				add(L26);

					L27=new JLabel("0.8km ");
  				L27.setLocation(700,560);
				L27.setSize (100,10);
				add(L27);

					L28=new JLabel("1.5km ");
  				L28.setLocation(1000,560);
				L28.setSize (100,10);
				add(L28);

					L29=new JLabel("0.35km ");
  				L29.setLocation(945,665);
				L29.setSize (100,10);
				add(L29);

					L30=new JLabel("2.3km ");
  				L30.setLocation(650,530);
				L30.setSize (100,10);
				add(L30);

					L31=new JLabel("1.1km ");
  				L31.setLocation(450,490);
				L31.setSize (100,10);
				add(L31);

					L32=new JLabel("2.1km ");
  				L32.setLocation(350,510);
				L32.setSize (100,10);
				add(L32);

					L33=new JLabel("4km ");
  				L33.setLocation(200,560);
				L33.setSize (100,10);
				add(L33);

					L34=new JLabel("3km ");
  				L34.setLocation(210,620);
				L34.setSize (100,10);
				add(L34);

					L35=new JLabel("7km ");
  				L35.setLocation(330,690);
				L35.setSize (100,10);
				add(L35);

					L36=new JLabel("0.75km ");
  				L36.setLocation(1020,645);
				L36.setSize (100,10);
				add(L36);

					L37=new JLabel("3.2km ");
  				L37.setLocation(850,710);
				L37.setSize (100,10);
				add(L37);


					L38=new JLabel("1.6km ");
  				L38.setLocation(775,600);
				L38.setSize (100,10);
				add(L38);


					L39=new JLabel("0.7km ");
  				L39.setLocation(230,610);
				L39.setSize (100,10);
				add(L39);


					L40=new JLabel("2.7km ");
  				L40.setLocation(950,520);
				L40.setSize (100,10);
				add(L40);


					L41=new JLabel("2.6km ");
  				L41.setLocation(870,450);
				L41.setSize (100,10);
				add(L41);


					L42=new JLabel("1.3km ");
  				L42.setLocation(810,420);
				L42.setSize (100,10);
				add(L42);


					L43=new JLabel("8km ");
  				L43.setLocation(1150,530);
				L43.setSize (100,10);
				add(L43);


					L44=new JLabel("0.4km ");
  				L44.setLocation(1020,680);
				L44.setSize (100,10);
				add(L44);


					L45=new JLabel("7km ");
  				L45.setLocation(500,100);
				L45.setSize (100,10);
				add(L45);


					L46=new JLabel("0.95km ");
  				L46.setLocation(750,200);
				L46.setSize (100,10);
				add(L46);


				setSize(900,600);
				setLocation(0, 0);
				//setBackground(Color.pink);
					Demo2 demo = new Demo2();
					add(demo);
					setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	//}

addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				page11 myObj = new page11();
			myObj.show();
			}
		});

	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b1)
		{

	this.setVisible(false);
			Map11 myObj = new Map11();
			myObj.show();
					myObj.repaint();
										//		frame2.setVisible(true);
		}

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
    	System.out.print("\nindx = " + indx);


		for(int i = 1;path[i] != 0;i++)
		{
			System.out.print("\npath[" + i + "] = " + path[i]);

			if(path[i] == 2 && path[i-1] == 1 || path[i] == 1 && path[i-1] == 2)		//Pune Station and Sancheti Hospital
			{
				g.setColor(Color.RED);
				g.drawLine(240,110,180,200);
			}

			if(path[i-1] == 1 && path[i] == 4 || path[i-1] == 4 && path[i] == 1)  	//Pune Station and MNP
			{
				g.setColor(Color.RED);
				g.drawLine(240,110,350,250);
			}

			if(path[i-1] == 1 && path[i] == 6 )  	//pune stn and Swargate
			{
				g.setColor(Color.RED);
				g.drawLine(240,110,795,170);
			}

			if(path[i-1] == 2 && path[i] == 3 || path[i-1] == 3 && path[i] == 2)  	//sancheti and shiv ng
			{
				g.setColor(Color.RED);
				g.drawLine(180,200,180,275);
			}

			if(path[i-1] == 4 && path[i] == 5 || path[i-1] == 5 && path[i] == 4)  	//MNP and shanivar wada
			{
				g.setColor(Color.RED);
				g.drawLine(350,250,510,250);
			}

			if(path[i-1] == 4 && path[i] == 11 || path[i-1] == 11 && path[i] == 4)  	//MNP and balgandharva
			{
				g.setColor(Color.RED);
				g.drawLine(350,250,400,330);
			}

			if(path[i-1] == 3 && path[i] == 4 || path[i-1] == 4 && path[i] == 3)    // Shivaji ng and MNP
			{
				g.setColor(Color.RED);
				g.drawLine(170,275,340,250);
			}

			if(path[i-1] == 5 && path[i] == 7 || path[i-1] == 7 && path[i] == 5)  //shaniwaar wada and hirabaug
			{
				g.setColor(Color.RED);
				g.drawLine(510,250,695,250);
			}

			if(path[i-1] == 6 && path[i] == 8 || path[i-1] == 8 && path[i] == 6)  //swargate and bhartividyapeeth
			{
				g.setColor(Color.RED);
				g.drawLine(795,170,1120,240);
			}

		    if(path[i-1] == 6 && path[i] == 9 )  //swargate and sarasbaug
			{
				g.setColor(Color.RED);
				g.drawLine(795,170,875,250);
			}

			if(path[i-1] == 7 && path[i] == 9 || path[i-1] == 9 && path[i] == 7)  //hirabaug and sarasbaug
			{
				g.setColor(Color.RED);
				g.drawLine(695,250,875,250);
			}

			if(path[i-1] == 7 && path[i] == 6)  //swargate and hirabaug
			{
				g.setColor(Color.RED);
				g.drawLine(795,170,695,250);
			}

			if(path[i-1] == 8 && path[i] == 10 || path[i-1] == 10 && path[i] == 8)  //BV and katraj
			{
				g.setColor(Color.RED);
				g.drawLine(1120,240,1110,350);
			}

			if(path[i-1] == 9 && path[i] == 16 || path[i-1] == 16 && path[i] == 9)  //sarasbaug and dandekar
			{
				g.setColor(Color.RED);
				g.drawLine(875,250,950,410);
			}

			if(path[i-1] == 10 && path[i] == 28 || path[i-1] == 28 && path[i] == 10)  //scoe and katraj
			{
				g.setColor(Color.RED);
				g.drawLine(1110,350,1120,700);
			}

			if(path[i-1] == 11 && path[i] == 15)  //baalgandharva and garvare
			{
				g.setColor(Color.RED);
				g.drawLine(400,330,570,400);
			}

			if(path[i-1] == 12 && path[i] == 13 || path[i-1] == 13 && path[i] == 12)  //comptom and aundh
			{
				g.setColor(Color.RED);
				g.drawLine(180,400,160,470);
			}

			if(path[i-1] == 12 && path[i] == 3)  //shiv ng and cromptm grvs
			{
				g.setColor(Color.RED);
				g.drawLine(180,275,180,400);
			}

			if(path[i-1] == 14 && path[i] == 12)  //fc and cromptm grvs
			{
				g.setColor(Color.RED);
				g.drawLine(348,400,180,400);
			}


			if(path[i-1] == 15 && path[i] == 14)  //fc and garware
			{
				g.setColor(Color.RED);
				g.drawLine(348,400,570,400);
			}

			if(path[i-1] == 15 && path[i] == 17 || path[i-1] == 17 && path[i] == 15)  //garware and nal stop
			{
				g.setColor(Color.RED);
				g.drawLine(570,400,475,490);
			}

			if(path[i-1] == 15 && path[i] == 16 || path[i-1] == 16 && path[i] == 15)  //garware and dandekar
			{
				g.setColor(Color.RED);
				g.drawLine(570,400,950,410);
			}

			if(path[i-1] == 16 && path[i] == 18 || path[i-1] == 18 && path[i] == 16)  //dandekar and mhatarepool
			{
				g.setColor(Color.RED);
				g.drawLine(690,500,950,410);
			}

				if(path[i-1] == 16 && path[i] == 21 || path[i-1] == 21 && path[i] == 16)  //rajaram pool and dandekar
				{	g.setColor(Color.RED);
				g.drawLine(950,410,800,575);
				}

				if(path[i-1] == 17 && path[i] == 18 || path[i-1] == 18 && path[i] == 17)  //nal stop and mhantre pool
			{
				g.setColor(Color.RED);
				g.drawLine(475,490,690,500);
			}

				if(path[i-1] == 17 && path[i] == 19 || path[i-1] == 19 && path[i] == 17)  //karnatak and nal stop
			{
				g.setColor(Color.RED);
				g.drawLine(475,490,435,570);
			}

				if(path[i-1] == 17 && path[i] == 24 || path[i-1] == 24 && path[i] == 17)  //karve putala and nal stop
			{
				g.setColor(Color.RED);
				g.drawLine(475,490,270,600);
			}

				if(path[i-1] == 18 && path[i] == 20 || path[i-1] == 20 && path[i] == 18)  //mhatre pool and tathavde
			{
				g.setColor(Color.RED);
				g.drawLine(690,500,610,640);
			}

				if(path[i-1] == 18 && path[i] == 21 || path[i-1] == 21 && path[i] == 18)  //mhatre pool and rajaram pool
			{
				g.setColor(Color.RED);
				g.drawLine(690,500,800,575);
			}

				if(path[i-1] == 19 && path[i] == 20 || path[i-1] == 20 && path[i] == 19)  //karnatak and tathavde
			{
				g.setColor(Color.RED);
				g.drawLine(435,570,610,640);
			}

				if(path[i-1] == 19 && path[i] == 24 || path[i-1] == 24 && path[i] == 19)  //karnatak and karve putala
			{
				g.setColor(Color.RED);
				g.drawLine(435,570,270,600);
			}

				if(path[i-1] == 20 && path[i] == 21 || path[i-1] == 21 && path[i] == 20)  //rajaram and tathavde
			{
				g.setColor(Color.RED);
				g.drawLine(610,640,800,575);
			}

				if(path[i-1] == 20 && path[i] == 26 || path[i-1] == 26 && path[i] == 20)  //tathavde and karve nagr
			{
				g.setColor(Color.RED);
				g.drawLine(610,640,435,750);
			}

				if(path[i-1] == 21 && path[i] == 22 || path[i-1] == 22 && path[i] == 21)  //rajaram and tukai nagr
			{
				g.setColor(Color.RED);
				g.drawLine(800,575,1100,540);
			}

				if(path[i-1] == 21 && path[i] == 30 || path[i-1] == 30 && path[i] == 21)  //rajaram and anand ngr
			{
				g.setColor(Color.RED);
				g.drawLine(800,575,750,750);
			}

				if(path[i-1] == 22 && path[i] == 27 || path[i-1] == 27 && path[i] == 22)  //tukai ng and minakshi ouram
			{
				g.setColor(Color.RED);
				g.drawLine(1100,540,940,670);
			}

				if(path[i-1] == 23 && path[i] == 24 || path[i-1] == 24 && path[i] == 23)  //kothrud depo and karve putala
			{
				g.setColor(Color.RED);
				g.drawLine(115,600,270,600);
			}

				if(path[i-1] == 24 && path[i] == 25 || path[i-1] == 25 && path[i] == 24)  //karve putala and kothrudn stand
			{
				g.setColor(Color.RED);
				g.drawLine(270,600,270,700);
			}

				if(path[i-1] == 23 && path[i] == 25 || path[i-1] == 25 && path[i] == 23)  //kothrud stand and kothrud depo
			{
				g.setColor(Color.RED);
				g.drawLine(115,600,270,700);
			}

				if(path[i-1] == 25 && path[i] == 26 || path[i-1] == 26 && path[i] == 25)  //kothrud stand and karve nag
			{
				g.setColor(Color.RED);
				g.drawLine(270,700,435,750);
			}

				if(path[i-1] == 27 && path[i] == 28 || path[i-1] == 28 && path[i] == 27)  //minak and scoe
			{
				g.setColor(Color.RED);
				g.drawLine(940,670,1120,700);
			}

				if(path[i-1] == 27 && path[i] == 29 || path[i-1] == 29 && path[i] == 27)  //minakshi and vadgaon
			{
				g.setColor(Color.RED);
				g.drawLine(940,670,950,750);
			}

				if(path[i-1] == 28 && path[i] == 29 || path[i-1] == 29 && path[i] == 28)  //scoe and vadgaon
			{
				g.setColor(Color.RED);
				g.drawLine(1120,700,950,750);
			}

			if(path[i-1] == 29 && path[i] == 30 || path[i-1] == 30 && path[i] == 29)  // vadgaon and anand ng
			{
				g.setColor(Color.RED);
				g.drawLine(750,750,950,750);
			}
		}

	}
}