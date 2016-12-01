import java.sql.*;

class Supergraph extends Flatgraph
{
	/* Flatgraph :-
		protected String src, dest;
		protected int fragid;
		protected float wt;		*/

	protected String hop;

	public Supergraph()
	{
		super();
		hop = null;
	}

	public void set(String src, String dest, int fragid)
	{
		this.src = src;
		this.dest = dest;
		this.fragid = fragid;
	}

	public float getWt()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();
			rs = st.executeQuery("select wts from super_graph where src = '" + src + "' and dests = '" + dest +"'");

			if(rs.next())
			{
				wt = rs.getFloat("wts");
			}
			else
			{
				if(src.equals(dest))
					wt = 0.00f;
				else
					wt = 999.00f;
			}

			rs.close();
			st.close();
			con.close();

			return wt;

		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return wt;
	}

	public void update(String hop, float wt)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();
			st.executeUpdate("update super_graph set hops = " + hop + " where src = '" + src + "' and dests ='" + dest+"'"); // check update query
			st.executeUpdate("update super_graph set wts = " + wt + " where src = '" + src + "' and dests = '" + dest+"'");
			st.executeUpdate("update super_graph set fragid = " + fragid + " where src = '" + src + "' and dests = '" + dest+"'");

			st.close();
			con.close();

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void insert(String hop, float wt)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();
			st.executeUpdate("insert into super_graph values('" + src + "','" + dest + "','" + hop + "'," + wt + "," + fragid + ")");

			st.close();
			con.close();

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}