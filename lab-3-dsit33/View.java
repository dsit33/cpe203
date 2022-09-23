class View{
	private String product;
	private String productPrice;

	public View(String product, String productPrice){
		this.product = product ;
	}

	public String getProductID(){
		return this.product ;
	}

	public String getPrice(){
		return this.productPrice;
	}
}