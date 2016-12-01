import java.awt.*;
import javax.swing.*;

public class Demo2 extends JComponent {


	public void paint(Graphics g) {

	g.drawLine(1110,310,1120,660);            //katraj...scoe
		g.drawLine(1120,660,950,710);			//scoe...vadgaon
		g.drawLine(940,630,1120,660);			//scoe....minakshi puram
		g.drawLine(950,710,940,630);            //vadgaon...minakshipuram
		g.drawLine(940,630,1100,500);			//MP...tukainagar
		g.drawLine(1100,500,800,535);			//TN...rajarampool
		g.drawLine(800,535,950,370);			//rajarampool...dandekarpool
		g.drawLine(950,710,750,710);			//vadgaon...anand ng
		g.drawLine(750,710,800,535);			//anand ng...rajarampool
		g.drawLine(800,535,610,600);			//raja....thathavde udyan
		g.drawLine(800,535,690,460);			//raja.....mhatrepool
		g.drawLine(690,460,610,600);			//mhatrepool...thathavde udyan
		g.drawLine(610,600,435,710);			//thathavde udyan.....karve ng
		g.drawLine(610,600,435,530);			//thathavde udyan...karnataka school
		g.drawLine(435,710,270,660);			//karve ng....kothrud stand
		g.drawLine(270,660,115,560);			//kothrud stand...kothrud depo
		g.drawLine(270,560,270,660);			//kothrud stand...karve putala
		g.drawLine(115,560,270,560);			//kothrud depo...karve putala
		g.drawLine(270,560,435,530);			//karve putala....karnataka school
		g.drawLine(270,560,475,450);			//karve putala....null stop
		g.drawLine(435,530,475,450);			//karnataka school.....null stop
		g.drawLine(475,450,570,360);			//null stop...garware
		g.drawLine(570,360,400,290);			//garware....balgandharva
		g.drawLine(400,290,360,230);			//balgandharva....MNP

		g.drawLine(350,210,240,70);			///MNP....pune stn
        g.drawLine(240,70,180,160);			//pune stn...sancheti
        g.drawLine(180,160,180,235);			//sancheti...shivaji ngr
        g.drawLine(180,235,350,210);			//shivaji ngr...MNP
        g.drawLine(180,235,180,360);			//shivaji ngr...cromption greaves
        g.drawLine(180,360,160,430);			//cromption greaves...aundh
        g.drawLine(180,360,348,360);			//cromption greaves...FC
        g.drawLine(348,360,570,360);			//FC...garware
        g.drawLine(570,360,950,370);			//garware...dandekarpool
        g.drawLine(950,370,690,460);			//dandekarpool....mhatre pool
        g.drawLine(690,460,475,450);			//mhatre pool...null stop
        g.drawLine(795,130,240,70);			//pune stn....swargate
        g.drawLine(795,130,1120,200);			//swargate...bharti veedhyapeth
        g.drawLine(1120,200,1110,310);			//bharti veedhyapeth..katraj
        g.drawLine(950,370,875,210);			//dandekar pool....saras baug
        g.drawLine(875,210,695,210);			//saras baug...hira baug
        g.drawLine(695,210,510,210);			//hira baug...shanivar wada
        g.drawLine(510,210,350,210);			//shanivar wada...MNP
        g.drawLine(695,210,795,130);			//hira baug....swargate
        g.drawLine(795,130,875,210);			//swargate...saras baug

}
}


