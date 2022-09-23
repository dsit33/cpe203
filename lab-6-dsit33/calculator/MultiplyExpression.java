class MultiplyExpression extends BinaryExpression
{

   public MultiplyExpression(final Expression lft, final Expression rht)
   {
    	super(lft, rht, " * ");
   }

   @Override
   public double _applyOperator(double operand1, double operand2)
   {
   		return operand1 * operand2;
   }

}

