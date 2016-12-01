import java.sql.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

class HEPV9
{
	private String superborder[], border[];
	int n,p,l;

	public HEPV9()
	{
		try
		{

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:flat","system","system");
			Statement st = con.createStatement();
			ResultSet rs = null;

			rs = st.executeQuery("select count(src) from source");	// src is primary key ... number of nodes
			if(rs.next())
				n = rs.getInt("count(src)");
			rs.close();

			rs =st.executeQuery("select count(distinct fragid) from flat_graph");
			if(rs.next())
				p = rs.getInt("count(distinctfragid)");
			rs.close();

			rs = st.executeQuery("select count(*) from flat_graph");
			if(rs.next())
				l = rs.getInt("count(*)");
			rs.close();

			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		superborder = new String[15];
		border = new String[10];
	}

	public static void main(String[] args)
	{
	//	HEPV9 h = new HEPV9();
	//	h.createHEPV();
		//System.out.println("\nShortest = " + h.pathRetrieval("17","4"));
	//	h.HEPVUpdate();
	//	System.out.println("\nShortest = " + h.pathRetrieval("1","10"));*/

	JFrame frame2 = new page11();
	frame2.setVisible(true);

	}

	public int getNodeCount()
	{
		return n;
	}

	public int getLinkCount()
	{
		return l;
	}

	public int getFragCount()
	{
		return p;
	}

	public void createHEPV()
	{
		Fragment frag = new Fragment();
		Supergraph superg = new Supergraph();

		borderSetting();

		for(int k = 1; k <= p; k++)		// k = fragid
		{
			encode(k);

			for(int c = 0;border[c] != null ;c++)
				border[c] = null;

			bordern(border,k);


			for(int i = 0;border[i]!=null;i++)		// border[i] = src
			{
				for(int j = 0;border[j]!=null;j++)	// border[j] = dest
				{

					if(i != j)
					{
						frag.set(border[i], border[j], k);
						superg.set(border[i], border[j],k);

						float swt, fwt;
						String fhop;

						swt = superg.getWt();
						fwt = frag.getWt();
						fhop = frag.getHop();

						if(fwt < 999.00f)
						{
							if(swt < 999.00f)
							{

								if(swt > fwt)
								{
									superg.update(fhop, fwt);

								}

							}
							else
							{
								superg.insert(fhop, fwt);

							}

						}
					}
				}
			}
		}
		encode(0);	// encode supergraph

	}

	private void borderSetting()
	{
		int c = 0;

		for(int i = 1;i <= n;i++)
		{
			for(int j = 1; j <= p;j++)
			{
				for(int k = j+1;k <= p;k++)
				{
					// check src if it resides in both fragments
						// if true then check whether it is present in superborder[]
						// then put src into superborder[c++]
						// then make fragj.border = 1 and fragid.border = 1 where fragj.src = i and fragid.src = i

					try
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

						Connection con = null;
						ResultSet rs = null;

						con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

						Statement st = con.createStatement();
						rs = st.executeQuery("select bor from frag where fragid = " + j + " and src = " + i + " and src in ( select src from frag where fragid = " + k + ")");


						int l;

						if(rs.next())
						{
							for(l = 0;l < c; l++)
							{
								if(superborder[l].equals(String.valueOf(i)))
								{
									break;
								}
							}

							if(l == c)
								superborder[c++] = String.valueOf(i);
							st.executeUpdate("Update frag set bor = 1 where fragid = " + j + " and src = " + i);
							st.executeUpdate("Update frag set bor = 1 where fragid = " + k + " and src = " + i);

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
		}
	}

	private void bordern(String[] border, int fragid)
	{
		int c = 0;

		for(int i = 1;i <= n;i++)
		{
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

				Connection con = null;
				ResultSet rs = null;

				con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

				Statement st = con.createStatement();

				rs = st.executeQuery("select distinct src from frag where fragid = " + fragid + " and src = " + i + " and bor = 1");

				if(rs.next())
				{
					border[c++] = rs.getString("src");
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

	private void encode(int fragid)
	{
		int count = 0, c = 0,pos, fragidsrc,fragidcurr;
		float wt;
		String src[], dest[],curr;
		Node node[];

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

			Statement st = con.createStatement();

			if(fragid == 0)		// supergraph
			{
				rs = st.executeQuery("select count(distinct src) from super_graph");	// Take out count of distinct src from super_graph

				if(rs.next())
				{
					count = rs.getInt("count(distinctsrc)");
				}
				rs.close();

				src = new String[count];

				rs = st.executeQuery("select distinct src from super_graph");		// Fill up src array with distinct src from supergraph

				while(rs.next())
				{
					src[c++] = rs.getString("src");
				}
				rs.close();


				count = 0;
				rs = st.executeQuery("select count(src) from source");		// Count distinct nodes in flatgraph
				if(rs.next())
				{
					count = rs.getInt("count(src)");
				}
				rs.close();

				node = new Node[count];
				c = 0;

				rs = st.executeQuery("select src from source");		// Fill up node array object
				while(rs.next())
				{
					node[c++] = new Node(rs.getString("src"));
				}
				rs.close();
			}
			else		// any other fragment
			{
				rs = st.executeQuery("select count(distinct src) from frag where fragid = " + fragid);	// Take out count of distinct src from fragment

				if(rs.next())
				{
					count = rs.getInt("count(distinctsrc)");
				}
				rs.close();

				src = new String[count];

				rs = st.executeQuery("select distinct src from frag where fragid = " + fragid);		// Fill up src array with distinct src from fragment

				while(rs.next())
				{
					src[c++] = rs.getString("src");
				}
				rs.close();


				count = 0;
				int temp;
				rs = st.executeQuery("select count(distinct src) from frag where fragid = " + fragid);		// Count distinct nodes in fragment
				while(rs.next())
				{
					temp = rs.getInt("count(distinctsrc)");
					if(count < temp)
						count = temp;
				}
				rs.close();


				node = new Node[count];
				c = 0;

				rs = st.executeQuery("select distinct src from frag where fragid = " + fragid);		// Fill up node array object
				while(rs.next())
				{
					node[c++] = new Node(rs.getString("src"));
				}
				rs.close();
			}



			for(int k = 0;k < src.length;k++)
			{
				if(fragid == 0)
				{
					count = 0;
					rs = st.executeQuery("select count(distinct dests) from super_graph where src = '" + src[k] +"'");
					if(rs.next())
					{
						count = rs.getInt("count(distinctdests)");
					}
					rs.close();

					dest = new String[count];
					c = 0;

					rs = st.executeQuery("select distinct dests from super_graph where src = '" + src[k] + "'");
					while(rs.next())
					{
						dest[c++] = rs.getString("dests");
					}
					rs.close();
				}
				else
				{
					count = 0;
					rs = st.executeQuery("select count(distinct dest) from frag where fragid = " + fragid + " and src = '" + src[k] +"'");
					if(rs.next())
					{
						count = rs.getInt("count(distinctdest)");
					}
					rs.close();

					dest = new String[count];
					c = 0;

					rs = st.executeQuery("select distinct dest from frag where fragid = " + fragid + " and src = '" + src[k] + "'");
					while(rs.next())
					{
						dest[c++] = rs.getString("dest");
					}
					rs.close();
				}

				for(int l = 0;l < dest.length;l++)
				{
					for(int i = 0;i < node.length;i++)
					{
						node[i].resetVisit();
						node[i].setHop(null);
						if(src[k].equals(node[i].getNode()))
							node[i].setWt(0.0f);
						else
							node[i].setWt(999.0f);
					}


					curr = src[k];
					float min = 999.0f;

					while(curr.equals(dest[l]) == false)		//till destination isnt visited i.e. curr != dest
					{
						for(pos = 0;pos < node.length;pos++)
						{
							if(curr.equals(node[pos].getNode()))
							{
								node[pos].setVisit();
								break;
							}
						}

						for(int i = 0;i < node.length;i++)
						{
							if(node[i].getVisit() == 0)
							{
								if(fragid == 0)
									rs = st.executeQuery("select wt from flat_graph where src = '" + node[pos].getNode() + "' and dest = '" + node[i].getNode() + "'");
								else
									rs = st.executeQuery("select wt from flat_graph where fragid = " + fragid + " and src = '" + node[pos].getNode() + "' and dest = '" + node[i].getNode() + "'");
								if(rs.next())
								{
									wt = rs.getFloat("wt");
								}
								else
									wt = 999.0f;
								rs.close();

								if(node[i].getWt() > (node[pos].getWt() + wt))
								{
									node[i].setWt((node[pos].getWt() + wt));		// Change shortest path weight

									if(node[pos].getHop() == null)
										node[i].setHop(node[i].getNode());
									else
										node[i].setHop(node[pos].getHop());
								}
							}
						}

						min = 999.0f;
						for(int i = 0;i < node.length;i++)		//find out min length therefore next vertex
						{
							if(min > node[i].getWt() && node[i].getVisit() == 0)
							{
								pos = i;
								min = node[i].getWt();
							}
						}
						if(min == 999.0f)
							break;
						curr = node[pos].getNode();

					} // while(curr != dest[l])


					if(min < 999.0f)
					{
						for(int i = 0;i < node.length ;i++)
						{
							if(node[i].getNode().equals(dest[l]))
							{
								if(fragid == 0)
								{
									st.executeUpdate("update super_graph set hops = " + node[i].getHop() + " where src = " + src[k] + " and dests = " + dest[l]);
									st.executeUpdate("update super_graph set wts = " + node[i].getWt() + " where src = " + src[k] + " and dests = " + dest[l]);
								}
								else
								{
									st.executeUpdate("update frag set hop = " + node[i].getHop() + " where fragid = " + fragid + " and src = " + src[k] + " and dest = " + dest[l]);
									st.executeUpdate("update frag set wt = " + node[i].getWt() + " where fragid = " + fragid + " and src = " + src[k] + " and dest = " + dest[l]);
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void fillSuperborder()
	{
		int c = 0;

		for(int i = 0;superborder[i] != null;i++)
		{
			superborder[i] = null;
		}

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

			Statement st = con.createStatement();

			rs = st.executeQuery("select unique src from super_graph");

			while(rs.next())
			{
				superborder[c++] = rs.getString("src");
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

/*	public float pathRetrieval(String src,String dest, Map frame)
	{
		float SPW = 999.00f;
		int flags = 0, flagd = 0;

		Fragment frag = new Fragment();
		Supergraph superg = new Supergraph();

		if(!src.equals(dest))
		{
			if(superborder[0] == null)
			{
				fillSuperborder();
			}

			for(int i = 0; superborder[i] != null; i++)
			{
				if(superborder[i].equals(src))
				{
					flags = 1; 	// src is a border node (in supergraph)
				}
				else if(superborder[i].equals(dest))
				{
					flagd = 1;	// dest is a border node (in supergraph)
				}
			}

			if(flags == 1 && flagd == 1)		// Case 1: Both src and dest nodes are border nodes (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs1 = st.executeQuery("select * from super_graph where src = '"+ src + "' and dests = '"+ dest +"'");

					if(rs1.next())
					{
						SPW = rs1.getFloat("wts");

						String hops = rs1.getString("hops");
						int fragidd = rs1.getInt("fragid");

						System.out.print(src + " -> " + hops);

						frame.highlight(src);	// colour buttons
						frame.highlight(hops);

						int fragids = 0,flag = 1;
						String hopd = null;

						rs = st.executeQuery("select fragid from frag where src  = " + src + " and dest = " + hops + " and wt = (select min(wt) from frag where src = " + src + " and dest = " + hops +")");

						if(rs.next())
						{
							fragids = rs.getInt("fragid");
							bordern(border, fragids);		// border[] is used for src
						}
						rs.close();

						if(fragids == fragidd && fragids != 0)	// src and dest are in same fragment
						{
							while(!hops.equals(dest))
							{
								frag.set(hops,dest,fragids);
								hops = frag.getHop();
								System.out.print(" -> " + hops);
								frame.highlight(hops);
							}

						}
						else		// traverse more than one fragment
						{
							int fragid = 0,indxs = 0;
							float min = 999.0f,wt;
							String hop = null;

							for(int i = 0;i < border.length;i++)
							{
								if(src.equals(border[i]) == false)
								{
									frag.set(hops,border[i],fragids);
									wt = frag.getWt();
									if(min > wt)
									{
										min = wt;
										indxs = i;
										hop = frag.getHop();	// hops -> hop -> border[i]
									}
								}
							}

							if(hop.equals(null) == false)
							{
								System.out.print(" -> " + hop);		// hops -> hop -> border[indxs]
								frame.highlight(hop);
								while(hop.equals(border[indxs]) == false)
								{
									frag.set(hop,border[indxs],fragids);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}
							}
							else
							{
								System.out.print(" -> " + border[indxs]);
								frame.highlight(border[indxs]);
							}


							String[] borderd = new String[10];	// border -> borderd

							bordern(borderd, fragidd);	// borderd is used for dest

							min = 999.0f;
							int indxd = 0;
							for(int i = 0;i < borderd.length;i++)
							{
								if(border[indxs].equals(src) == false)
								{
									if(border[indxs].equals(borderd[i]))	// if we get same border node in fragids and fragidd
									{
										indxd = i;
										break;
									}
									else
									{
										rs = st.executeQuery("select fragid from super_graph where src = " + border[indxs] + " and dests = " + borderd[i]);
										if(rs.next())
											fragid = rs.getInt("fragid");
										frag.set(border[indxs],borderd[i],fragid);
										wt = frag.getWt();
										if(min > wt)
										{
											min = wt;
											hop = frag.getHop();
											indxd = i;
										}
										rs.close();
									}
								}
							}

							if(border[indxs].equals(borderd[indxd]) == false)	// 2 different border nodes in different fragment
							{
								while(!hop.equals(borderd[indxd]))
								{
									frag.set(hop,borderd[indxd],fragid);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}
							}

							hopd = borderd[indxd];
							while(!hopd.equals(dest))
							{
								frag.set(hopd,dest,fragidd);
								hopd = frag.getHop();
								System.out.print(" -> " + hopd);
								frame.highlight(hopd);
							}
						}
					}
					else
						SPW = 999.0f;

					//rs1.close();
					st.close();
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 1 && flagd == 0)	// Case 2: The src is a border node (in supergraph), but the dest is a local node (in fragment)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where src = '" + src + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and dest = " + dest + " and bor = 0");
							if(rs1.next())
							{
								fragidd = j;
								bordern(border, fragidd);

								float swt = 0.00f,fwt = 0.00f;

								for(int k = 0; border[k]!= null; k++)
								{
									superg.set(src,border[k],0);
									frag.set(border[k],dest,fragidd);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src  = '" + src + "' and dests = '" + border[k] + "'");
										if(rs2.next())
										{
											fragids = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);
								frame.highlight(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
									frame.highlight(src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}

								rs1.close();
								break;
							}
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 0 && flagd == 1)	// Case 3: The src is a local node (in fragment), but the dest is a border node (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where dests = '" + dest + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and src = '"+ src +"' and bor = 0");

							if(rs1.next())
							{
								fragids = j;
								bordern(border, fragids);

								float swt,fwt;

								for(int k = 0; border[k]!= null; k++)
								{
									frag.set(src,border[k],fragids);
									superg.set(border[k],dest,0);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src = '" + border[k] + "' and dests = '" + dest + "'");
										if(rs2.next())
										{
											fragidd = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);
								frame.highlight(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
									frame.highlight(src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}

								rs1.close();
								break;
							}
							rs1.close();
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else		// Case 4: Both the src and dest nodes are local nodes (in fragment)
			{
				try
				{
					int flag = 0;
					String hops = null,hopd = null;

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					int fragids = 0,fragidd = 0;

					for(int i = 1; i <= p; i++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + i + " and src = '" + src + "'");

						if(rs.next())
						{
							bordern(border, i);		// border[] is used for src
							fragids = i;
							rs.close();
							break;
						}

						rs.close();
					}

					String[] borderd = new String[10];

					for(int j = 1; j <= p; j++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + j + " and dest = '" + dest + "'");

						if(rs.next())
						{
							bordern(borderd, j);	// borderd is used for dest
							fragidd = j;
							rs.close();
							break;
						}

						rs.close();
					}

					if(fragids!=0 && fragidd!=0)
					{
						float swt,fwts,fwtd;

						for(int i = 0; border[i]!= null; i++)
						{
							for(int j = 0; borderd[j]!=null; j++)
							{
								frag.set(src,border[i],fragids);
								fwts = frag.getWt();
								superg.set(border[i],borderd[j],0);
								swt = superg.getWt();
								frag.set(borderd[j], dest,fragidd);
								fwtd = frag.getWt();

								if((swt + fwts + fwtd) < SPW)
								{
									hops = border[i];
									hopd = borderd[j];
									SPW = swt + fwts + fwtd;
									flag = 1;
								}
							}
						}
					}
					if(fragids == fragidd && fragids != 0)
					{
						float fwt;

						frag.set(src,dest,fragids);
						fwt = frag.getWt();

						if(fwt < SPW)
						{
							hops = frag.getHop();
							SPW = fwt;
							flag = 2;
						}
					}

					if(flag == 1)
					{
						int fragid = 0;

						System.out.print(src);
						frame.highlight(src);

						while(!src.equals(hops))
						{
							frag.set(src,hops,fragids);
							src = frag.getHop();
							System.out.print(" -> " + src);
							frame.highlight(src);
						}

						rs = st.executeQuery("select fragid from super_graph where src = '" + hops + "' and dests = '" + hopd + "'");
						if(rs.next())
						{
							fragid = rs.getInt("fragid");
						}

						while(!hops.equals(hopd))
						{
							frag.set(hops,hopd,fragid);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
							frame.highlight(hops);
						}

						while(!hopd.equals(dest))
						{
							frag.set(hopd,dest,fragidd);
							hopd = frag.getHop();
							System.out.print(" -> " + hopd);
							frame.highlight(hopd);
						}
					}
					else if(flag == 2)
					{
						System.out.print(src + " -> " + hops);
						frame.highlight(src);
						frame.highlight(hops);

						while(!hops.equals(dest))
						{
							frag.set(hops,dest,fragids);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
							frame.highlight(hops);
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else
			System.out.println(" **** Source and Destination are same. Thus, shortest distance = 0.0");

		return SPW;
	}
*/
/*	public float pathRetrieval(String src,String dest)
	{
		float SPW = 999.00f;
		int flags = 0, flagd = 0;

		Fragment frag = new Fragment();
		Supergraph superg = new Supergraph();

		if(!src.equals(dest))
		{
			if(superborder[0] == null)
			{
				fillSuperborder();
			}

			for(int i = 0; superborder[i] != null; i++)
			{
				if(superborder[i].equals(src))
				{
					flags = 1; 	// src is a border node (in supergraph)
				}
				else if(superborder[i].equals(dest))
				{
					flagd = 1;	// dest is a border node (in supergraph)
				}
			}

			if(flags == 1 && flagd == 1)		// Case 1: Both src and dest nodes are border nodes (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs1 = st.executeQuery("select * from super_graph where src = '"+ src + "' and dests = '"+ dest +"'");

					if(rs1.next())
					{
						SPW = rs1.getFloat("wts");

						String hops = rs1.getString("hops");
						int fragidd = rs1.getInt("fragid");

						System.out.print(src + " -> " + hops);

						int fragids = 0,flag = 1;
						String hopd = null;

						rs = st.executeQuery("select fragid from frag where src  = " + src + " and dest = " + hops + " and wt = (select min(wt) from frag where src = " + src + " and dest = " + hops +")");

						if(rs.next())
						{
							fragids = rs.getInt("fragid");
							bordern(border, fragids);		// border[] is used for src
						}
						rs.close();

						if(fragids == fragidd && fragids != 0)	// src and dest are in same fragment
						{
							while(!hops.equals(dest))
							{
								frag.set(hops,dest,fragids);
								hops = frag.getHop();
								System.out.print(" -> " + hops);
							}

						}
						else		// traverse more than one fragment
						{
							int fragid = 0,indxs = 0;
							float min = 999.0f,wt;
							String hop = null;

							for(int i = 0;i < border.length;i++)
							{
								if(src.equals(border[i]) == false)
								{
									frag.set(hops,border[i],fragids);
									wt = frag.getWt();
									if(min > wt)
									{
										min = wt;
										indxs = i;
										hop = frag.getHop();	// hops -> hop -> border[i]
									}
								}
							}

							if(hop.equals(null) == false)
							{
								System.out.print(" -> " + hop);		// hops -> hop -> border[indxs]
								while(hop.equals(border[indxs]) == false)
								{
									frag.set(hop,border[indxs],fragids);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
								}
							}
							else
							{
								System.out.print(" -> " + border[indxs]);
							}


							String[] borderd = new String[10];	// border -> borderd

							bordern(borderd, fragidd);	// borderd is used for dest

							min = 999.0f;
							int indxd = 0;
							for(int i = 0;i < borderd.length;i++)
							{
								if(border[indxs].equals(src) == false)
								{
									if(border[indxs].equals(borderd[i]))	// if we get same border node in fragids and fragidd
									{
										indxd = i;
										break;
									}
									else
									{
										rs = st.executeQuery("select fragid from super_graph where src = " + border[indxs] + " and dests = " + borderd[i]);
										if(rs.next())
											fragid = rs.getInt("fragid");
										frag.set(border[indxs],borderd[i],fragid);
										wt = frag.getWt();
										if(min > wt)
										{
											min = wt;
											hop = frag.getHop();
											indxd = i;
										}
										rs.close();
									}
								}
							}

							if(border[indxs].equals(borderd[indxd]) == false)	// 2 different border nodes in different fragment
							{
								while(!hop.equals(borderd[indxd]))
								{
									frag.set(hop,borderd[indxd],fragid);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
								}
							}

							hopd = borderd[indxd];
							while(!hopd.equals(dest))
							{
								frag.set(hopd,dest,fragidd);
								hopd = frag.getHop();
								System.out.print(" -> " + hopd);
							}
						}
					}
					else
						SPW = 999.0f;

					//rs1.close();
					st.close();
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 1 && flagd == 0)	// Case 2: The src is a border node (in supergraph), but the dest is a local node (in fragment)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where src = '" + src + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and dest = " + dest + " and bor = 0");
							if(rs1.next())
							{
								fragidd = j;

								bordern(border, fragidd);

								float swt = 0.00f,fwt = 0.00f;

								for(int k = 0; border[k]!= null; k++)
								{
									superg.set(src,border[k],0);
									frag.set(border[k],dest,fragidd);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src  = '" + src + "' and dests = '" + border[k] + "'");
										if(rs2.next())
										{
											fragids = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
								}

								rs1.close();
								break;
							}
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 0 && flagd == 1)	// Case 3: The src is a local node (in fragment), but the dest is a border node (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where dests = '" + dest + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and src = '"+ src +"' and bor = 0");

							if(rs1.next())
							{
								fragids = j;
								bordern(border, fragids);

								float swt,fwt;

								for(int k = 0; border[k]!= null; k++)
								{
									frag.set(src,border[k],fragids);
									superg.set(border[k],dest,0);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src = '" + border[k] + "' and dests = '" + dest + "'");
										if(rs2.next())
										{
											fragidd = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
								}

								rs1.close();
								break;
							}
							rs1.close();
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else		// Case 4: Both the src and dest nodes are local nodes (in fragment)
			{
				try
				{
					int flag = 0;
					String hops = null,hopd = null;

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:HEPV","system","system");

					Statement st = con.createStatement();

					int fragids = 0,fragidd = 0;

					for(int i = 1; i <= p; i++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + i + " and src = '" + src + "'");

						if(rs.next())
						{
							bordern(border, i);		// border[] is used for src
							fragids = i;
							rs.close();
							break;
						}

						rs.close();
					}

					String[] borderd = new String[10];

					for(int j = 1; j <= p; j++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + j + " and dest = '" + dest + "'");

						if(rs.next())
						{
							bordern(borderd, j);	// borderd is used for dest
							fragidd = j;
							rs.close();
							break;
						}

						rs.close();
					}

					if(fragids!=0 && fragidd!=0)
					{
						float swt,fwts,fwtd;

						for(int i = 0; border[i]!= null; i++)
						{
							for(int j = 0; borderd[j]!=null; j++)
							{
								frag.set(src,border[i],fragids);
								fwts = frag.getWt();
								superg.set(border[i],borderd[j],0);
								swt = superg.getWt();
								frag.set(borderd[j], dest,fragidd);
								fwtd = frag.getWt();

								if((swt + fwts + fwtd) < SPW)
								{
									hops = border[i];
									hopd = borderd[j];
									SPW = swt + fwts + fwtd;
									flag = 1;
								}
							}
						}
					}
					if(fragids == fragidd && fragids != 0)
					{
						float fwt;

						frag.set(src,dest,fragids);
						fwt = frag.getWt();

						if(fwt < SPW)
						{
							hops = frag.getHop();
							SPW = fwt;
							flag = 2;
						}
					}

					if(flag == 1)
					{
						int fragid = 0;

						System.out.print(src);

						while(!src.equals(hops))
						{
							frag.set(src,hops,fragids);
							src = frag.getHop();
							System.out.print(" -> " + src);
						}

						rs = st.executeQuery("select fragid from super_graph where src = '" + hops + "' and dests = '" + hopd + "'");
						if(rs.next())
						{
							fragid = rs.getInt("fragid");
						}

						while(!hops.equals(hopd))
						{
							frag.set(hops,hopd,fragid);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
						}

						while(!hopd.equals(dest))
						{
							frag.set(hopd,dest,fragidd);
							hopd = frag.getHop();
							System.out.print(" -> " + hopd);
						}
					}
					else if(flag == 2)
					{
						System.out.print(src + " -> " + hops);

						while(!hops.equals(dest))
						{
							frag.set(hops,dest,fragids);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else
			System.out.println(" **** Source and Destination are same. Thus, shortest distance = 0.0");

		return SPW;
	}
*/
	private int flatUpdate(String src, String dest,float linkwt)
	{
		int fragid = 0;

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			Connection con = null;
			ResultSet rs = null;

			con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

			Statement st = con.createStatement();

			st.executeUpdate("Update flat_graph set wt = " + linkwt + " where src = '" + src + "' and dest = '" + dest + "'");

			rs = st.executeQuery("select fragid from flat_graph where src = '" + src + "' and dest = '" + dest + "'");
			if(rs.next())
			{
				fragid = rs.getInt("fragid");
			}
			rs.close();

			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return fragid;
	}

	public void HEPVUpdate(Next obj[])
	{
		int fragid[] = new int[p];
		String ch, borderc[] = new String[10], bor[] = new String[10];

	/*	for(int i = 0;i < obj.length;i++)
		{
			System.out.println(bj[i].src + obj[i].dest + obj[i].wt);
		}*/

		for(int i = 0; i < p; i++)
			fragid[i] = 0;


		for(int i = 0;i<obj.length;i++)
		{
			String	src = obj[i].src;
			String	dest = obj[i].dest;
			float	wt = obj[i].wt;
				int id = flatUpdate(src,dest,wt);
				fragid[--id] = 1;
		}
		for(int i = 0; i < p; i++)
		{
			if(fragid[i] == 1)
				encode(i+1);
		}


		for(int i = 0; border[i]!= null; i++)
		{
			border[i] = null;
			borderc[i] = null;
		}

		for(int i = 0; i < p; i++)
		{
			if(fragid[i] == 1)
			{
				bordern(border,i+1);

				for(int j = 0; border[j] != null; j++)
				{
					for(int k = 0; border[k] != null; k++)
					{
						if(j != k)
						{
							Supergraph superg = new Supergraph();
							superg.set(border[j], border[k], i+1);
							superg.update(null,999.00f);
						}
					}
				}
			}
		}

		for(int k = 1; k <= p; k++)
		{
			for(int i = 0; border[i]!=null; i++)
			{
				border[i] = null;
			}
			bordern(border,k);

			for(int l = 0; l < p; l++)
			{
				if(fragid[l] == 1)
				{
					for(int i = 0; borderc[i] != null; i++)
					{
						borderc[i] = null;
					}
					bordern(borderc,l+1);

					int c = 0;

					for(int u =0; border[u] != null;u++) // create bor[] for Ni, Nj that belong to both border[] and borderc[]
					{
						for(int v = 0; borderc[v] != null;v++)
						{
							if(border[u] == borderc[v])
							{
								int i;
								for(i = 0; bor[i] != null; i++) // no duplicate entries
								{
									if(bor[i] == border[u])
										break;
								}
								if(bor[i] == null)
									bor[c++] = border[u];
							}
						}
					}

					for(int i = 0; bor[i] != null; i++)
					{
						for(int j = 0; bor[j] != null; j++)
						{
							if(bor[i] != bor[j])
							{
								Supergraph superg = new Supergraph();
								Fragment frag = new Fragment();

								superg.set(bor[i], bor[j], l+1);
								frag.set(bor[i], bor[j], l+1);

								float fwt = frag.getWt();
								float swt = superg.getWt();

								if(fwt < 999.00f && swt > fwt)
								{
									superg.update(frag.getHop(),fwt);
								}
							}
						} // for j
					} // for i
				}
			} // for l
		} // for k

		encode(0);	// encode supergraph
	}


	public float pathRetrieval(String src,String dest, Map11 frame)
	{
		float SPW = 999.00f;
		int flags = 0, flagd = 0;

		Fragment frag = new Fragment();
		Supergraph superg = new Supergraph();

		if(!src.equals(dest))
		{
			if(superborder[0] == null)
			{
				fillSuperborder();
			}

			for(int i = 0; superborder[i] != null; i++)
			{
				if(superborder[i].equals(src))
				{
					flags = 1; 	// src is a border node (in supergraph)
				}
				else if(superborder[i].equals(dest))
				{
					flagd = 1;	// dest is a border node (in supergraph)
				}
			}

			if(flags == 1 && flagd == 1)		// Case 1: Both src and dest nodes are border nodes (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

					Statement st = con.createStatement();

					rs1 = st.executeQuery("select * from super_graph where src = '"+ src + "' and dests = '"+ dest +"'");

					if(rs1.next())
					{
						SPW = rs1.getFloat("wts");

						String hops = rs1.getString("hops");
						int fragidd = rs1.getInt("fragid");

						System.out.print(src + " -> " + hops);

						frame.highlight(src);	// colour buttons
						frame.highlight(hops);

						int fragids = 0,flag = 1;
						String hopd = null;

						rs = st.executeQuery("select fragid from frag where src  = " + src + " and dest = " + hops + " and wt = (select min(wt) from frag where src = " + src + " and dest = " + hops +")");

						if(rs.next())
						{
							fragids = rs.getInt("fragid");
							bordern(border, fragids);		// border[] is used for src
						}
						rs.close();

						if(fragids == fragidd && fragids != 0)	// src and dest are in same fragment
						{
							while(!hops.equals(dest))
							{
								frag.set(hops,dest,fragids);
								hops = frag.getHop();
								System.out.print(" -> " + hops);
								frame.highlight(hops);
							}

						}
						else		// traverse more than one fragment
						{
							int fragid = 0,indxs = 0;
							float min = 999.0f,wt;
							String hop = null;

							for(int i = 0;i < border.length;i++)
							{
								if(src.equals(border[i]) == false)
								{
									frag.set(hops,border[i],fragids);
									wt = frag.getWt();
									if(min > wt)
									{
										min = wt;
										indxs = i;
										hop = frag.getHop();	// hops -> hop -> border[i]
									}
								}
							}

							if(hop.equals(null) == false)
							{
								System.out.print(" -> " + hop);		// hops -> hop -> border[indxs]
								frame.highlight(hop);
								while(hop.equals(border[indxs]) == false)
								{
									frag.set(hop,border[indxs],fragids);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}
							}
							else
							{
								System.out.print(" -> " + border[indxs]);
								frame.highlight(border[indxs]);
							}


							String[] borderd = new String[10];	// border -> borderd

							bordern(borderd, fragidd);	// borderd is used for dest

							min = 999.0f;
							int indxd = 0;
							for(int i = 0;i < borderd.length;i++)
							{
								if(border[indxs].equals(src) == false)
								{
									if(border[indxs].equals(borderd[i]))	// if we get same border node in fragids and fragidd
									{
										indxd = i;
										break;
									}
									else
									{
										rs = st.executeQuery("select fragid from super_graph where src = " + border[indxs] + " and dests = " + borderd[i]);
										if(rs.next())
											fragid = rs.getInt("fragid");
										frag.set(border[indxs],borderd[i],fragid);
										wt = frag.getWt();
										if(min > wt)
										{
											min = wt;
											hop = frag.getHop();
											indxd = i;
										}
										rs.close();
									}
								}
							}

							if(border[indxs].equals(borderd[indxd]) == false)	// 2 different border nodes in different fragment
							{
								while(!hop.equals(borderd[indxd]))
								{
									frag.set(hop,borderd[indxd],fragid);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}
							}

							hopd = borderd[indxd];
							while(!hopd.equals(dest))
							{
								frag.set(hopd,dest,fragidd);
								hopd = frag.getHop();
								System.out.print(" -> " + hopd);
								frame.highlight(hopd);
							}
						}
					}
					else
						SPW = 999.0f;

					//rs1.close();
					st.close();
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 1 && flagd == 0)	// Case 2: The src is a border node (in supergraph), but the dest is a local node (in fragment)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where src = '" + src + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and dest = " + dest + " and bor = 0");
							if(rs1.next())
							{
								fragidd = j;
								bordern(border, fragidd);

								float swt = 0.00f,fwt = 0.00f;

								for(int k = 0; border[k]!= null; k++)
								{
									superg.set(src,border[k],0);
									frag.set(border[k],dest,fragidd);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src  = '" + src + "' and dests = '" + border[k] + "'");
										if(rs2.next())
										{
											fragids = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);
								frame.highlight(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
									frame.highlight(src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}

								rs1.close();
								break;
							}
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else if(flags == 0 && flagd == 1)	// Case 3: The src is a local node (in fragment), but the dest is a border node (in supergraph)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;
					ResultSet rs2 = null;

					con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

					Statement st = con.createStatement();

					rs = st.executeQuery("select * from super_graph where dests = '" + dest + "'");

					if(rs.next())
					{
						int fragids = 0,fragidd = 0;
						String hop = null;

						for(int j = 1;j <= p;j++)
						{
							rs1 = st.executeQuery("select * from frag where fragid = " + j + " and src = '"+ src +"' and bor = 0");

							if(rs1.next())
							{
								fragids = j;
								bordern(border, fragids);

								float swt,fwt;

								for(int k = 0; border[k]!= null; k++)
								{
									frag.set(src,border[k],fragids);
									superg.set(border[k],dest,0);

									swt = superg.getWt();
									fwt = frag.getWt();

									if((swt + fwt) < SPW)
									{
										hop = border[k];
										rs2 = st.executeQuery("select fragid from super_graph where src = '" + border[k] + "' and dests = '" + dest + "'");
										if(rs2.next())
										{
											fragidd = rs2.getInt("fragid");
										}
										rs2.close();
										SPW = swt + fwt;
									}
								}

								System.out.print(src);
								frame.highlight(src);

								while(!src.equals(hop))
								{
									frag.set(src,hop,fragids);
									src = frag.getHop();
									System.out.print(" -> " + src);
									frame.highlight(src);
								}

								while(!hop.equals(dest))
								{
									frag.set(hop,dest,fragidd);
									hop = frag.getHop();
									System.out.print(" -> " + hop);
									frame.highlight(hop);
								}

								rs1.close();
								break;
							}
							rs1.close();
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else		// Case 4: Both the src and dest nodes are local nodes (in fragment)
			{
				try
				{
					int flag = 0;
					String hops = null,hopd = null;

					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

					Connection con = null;
					ResultSet rs = null;
					ResultSet rs1 = null;

					con = DriverManager.getConnection("jdbc:odbc:flat","system","system");

					Statement st = con.createStatement();

					int fragids = 0,fragidd = 0;

					for(int i = 1; i <= p; i++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + i + " and src = '" + src + "'");

						if(rs.next())
						{
							bordern(border, i);		// border[] is used for src
							fragids = i;
							rs.close();
							break;
						}

						rs.close();
					}

					String[] borderd = new String[10];

					for(int j = 1; j <= p; j++)
					{
						rs = st.executeQuery("select * from frag where fragid = " + j + " and dest = '" + dest + "'");

						if(rs.next())
						{
							bordern(borderd, j);	// borderd is used for dest
							fragidd = j;
							rs.close();
							break;
						}

						rs.close();
					}

					if(fragids!=0 && fragidd!=0)
					{
						float swt,fwts,fwtd;

						for(int i = 0; border[i]!= null; i++)
						{
							for(int j = 0; borderd[j]!=null; j++)
							{
								frag.set(src,border[i],fragids);
								fwts = frag.getWt();
								superg.set(border[i],borderd[j],0);
								swt = superg.getWt();
								frag.set(borderd[j], dest,fragidd);
								fwtd = frag.getWt();

								if((swt + fwts + fwtd) < SPW)
								{
									hops = border[i];
									hopd = borderd[j];
									SPW = swt + fwts + fwtd;
									flag = 1;
								}
							}
						}
					}
					if(fragids == fragidd && fragids != 0)
					{
						float fwt;

						frag.set(src,dest,fragids);
						fwt = frag.getWt();

						if(fwt < SPW)
						{
							hops = frag.getHop();
							SPW = fwt;
							flag = 2;
						}
					}

					if(flag == 1)
					{
						int fragid = 0;

						System.out.print(src);
						frame.highlight(src);

						while(!src.equals(hops))
						{
							frag.set(src,hops,fragids);
							src = frag.getHop();
							System.out.print(" -> " + src);
							frame.highlight(src);
						}

						rs = st.executeQuery("select fragid from super_graph where src = '" + hops + "' and dests = '" + hopd + "'");
						if(rs.next())
						{
							fragid = rs.getInt("fragid");
						}

						while(!hops.equals(hopd))
						{
							frag.set(hops,hopd,fragid);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
							frame.highlight(hops);
						}

						while(!hopd.equals(dest))
						{
							frag.set(hopd,dest,fragidd);
							hopd = frag.getHop();
							System.out.print(" -> " + hopd);
							frame.highlight(hopd);
						}
					}
					else if(flag == 2)
					{
						System.out.print(src + " -> " + hops);
						frame.highlight(src);
						frame.highlight(hops);

						while(!hops.equals(dest))
						{
							frag.set(hops,dest,fragids);
							hops = frag.getHop();
							System.out.print(" -> " + hops);
							frame.highlight(hops);
						}
					}

					rs.close();
					st.close();
					con.close();

				} //try

				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else
			System.out.println(" **** Source and Destination are same. Thus, shortest distance = 0.0");

		return SPW;
	}

}