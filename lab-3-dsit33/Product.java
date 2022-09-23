class Product{
	private String productID ;
	private String productPrice ;

	public Product(String productID, String productPrice){
		this.productID = productID;
		this.productPrice = productPrice;
	}

	public String getProductID(){
		return this.productID;
	}

	public String getProductPrice(){
		return this.productPrice;
	}
}