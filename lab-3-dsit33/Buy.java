class Buy{
	private String productID;
	private int totalSpent;

	public Buy(String productID, int totalSpent){
		this.productID = productID ;
		this.totalSpent = totalSpent ;
	}

	public String getProductID(){
		return this.productID;
	}

	public int getProductPrice(){
		return this.totalSpent;
	}
}