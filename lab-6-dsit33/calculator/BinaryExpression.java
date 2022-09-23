class BinaryExpression implements Expression 
{
	  private final Expression lft;
    private final Expression rht;
    private final String operator;

    protected double _applyOperator(double lftResult, double rhtResult)
    {
      return 0.0;
    }

    public BinaryExpression(final Expression lft, final Expression rht, final String operator)
    {
    	this.lft = lft;
     	this.rht = rht;
     	this.operator = operator;
    }

    public String toString()
   	{
      	return "(" + lft + "%s".format(operator) + rht + ")";
   	}

   	public double evaluate(final Bindings bindings)
   	{
      	double lftResult = lft.evaluate(bindings);
      	double rhtResult = rht.evaluate(bindings);
      	return _applyOperator(lftResult, rhtResult);
   	}

}