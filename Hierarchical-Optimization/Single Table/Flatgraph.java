import java.sql.*;

abstract public class Flatgraph
{
	protected String src, dest;
	protected int fragid;
	protected float wt;

	public Flatgraph()
	{
		src = dest = null;
		fragid = 0;
		wt = 999.00f;
	}

	abstract public void set(String src, String dest, int fragid);

	public void displayGraph()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();
			rs = st.executeQuery("select src,dest,wt from flat_graph");

			System.out.println("src \t dest\t wt");

			while(rs.next())
			{
				System.out.println(rs.getString("src")+"\t"+rs.getString("dest") + "\t" + rs.getFloat("wt"));

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
}
