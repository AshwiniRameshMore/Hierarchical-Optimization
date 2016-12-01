import javax.swing.*;

public class DisplayPath {

		public static void main(String[] args) {

				JFrame frame = new JFrame ("map");
				JButton b[];
				int c =0;
				b = new JButton[10];

				while(c<10)
				{

					b[c] = new JButton("pune");
					frame.add(b[c++]);

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

					frame.setSize(900,600);
					frame.setLocation(0, 0);
					Demo2 demo = new Demo2();
					frame.add(demo);
					frame.setVisible(true);

			}
}