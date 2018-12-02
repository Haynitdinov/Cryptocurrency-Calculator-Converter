package application;

/**
 * This class represent calculation logic
 */

import java.math.BigDecimal;

public class Calculation {
	
	protected static BigDecimal cost(BigDecimal price, BigDecimal amount) {
		
		return price.multiply(amount);
	}
	
	protected static BigDecimal feeWithoutCost(BigDecimal cost, BigDecimal fee) {
		
		return cost.multiply(fee);
	}
	
    protected static BigDecimal costWithoutFee(BigDecimal cost, BigDecimal feeWithoutCost) {
		
		return cost.subtract(feeWithoutCost);
	}

}
