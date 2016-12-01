class Node
{
	private int visit;		// 0 indicates not visited
	private String node,hop;
	private float wt;

	public Node(String node)
	{
		visit = 0;
		this.node = node;
		hop = null;
		wt = 0.0f;
	}

	public void setVisit()
	{
		visit = 1;
	}

	public void resetVisit()
	{
		visit = 0;
	}

	public void setHop(String hop)
	{
		this.hop = hop;
	}

	public void setWt(float wt)
	{
		this.wt = wt;
	}

	public int getVisit()
	{
		return visit;
	}

	public String getNode()
	{
		return node;
	}

	public String getHop()
	{
		return hop;
	}

	public float getWt()
	{
		return wt;
	}
}