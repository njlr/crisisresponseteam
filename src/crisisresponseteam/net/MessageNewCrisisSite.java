package crisisresponseteam.net;

public strictfp final class MessageNewCrisisSite extends Message {
	
	private static final long serialVersionUID = -4961880892572261605L;
	
	private final String crisisSiteName;
	
	public String getCrisisSiteName() {
		
		return this.crisisSiteName;
	}
	
	public MessageNewCrisisSite(final String crisisSiteName) {
		
		super();
		
		this.crisisSiteName = crisisSiteName;
	}
}
