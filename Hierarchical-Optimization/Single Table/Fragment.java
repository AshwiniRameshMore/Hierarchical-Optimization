import java.sql.*;

class Fragment extends Supergraph
{
	/* Supergraph :-
		protected String src, dest;
		protected int fragid;
		protected float wt;
		protected String hop;	*/

	private int border;

	public Fragment()
	{
		super();
		border = 0;
	}

	public void set(String src, String dest, int fragid)
	{
		super.set(src,dest,fragid);
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
			rs = st.executeQuery("select wt from frag where fragid = " + fragid + " and src = " + src + " and dest = " + dest);

			if(rs.next())
			{
				wt = rs.getFloat("wt");
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

		return 999.0f;
	}

	public String getHop()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();
			rs = st.executeQuery("select hop from frag where fragid = " + fragid + " and src = " + src + " and dest = " + dest);

			if(rs.next())
			{
				hop = rs.getString("hop");
			}
			else
				hop = null;

			rs.close();
			st.close();
			con.close();

			return hop;

		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return "999";
	}
}
