package application;

/**
 * This class represent calculation logic
 */

import java.math.BigDecimal;

public class Calculation {
	
	/**
	 * This method we use to calculate what is total cost (price * amount)
	 * @param price
	 * @param amount
	 * @return cost
	 */
	protected static BigDecimal cost(BigDecimal price, BigDecimal amount) {
		
		return price.multiply(amount);
	}
	
	/**
	 * This method we use to calculate amount of fee payed from total cost (cost * 0.001 or 0.002)
	 * @param cost
	 * @param fee
	 * @return feeWithoutCost
	 */
	protected static BigDecimal feeWithoutCost(BigDecimal cost, BigDecimal fee) {
		
		return cost.multiply(fee);
	}
	
	/**
	 * This method we use to calculate what is cost without fee (cost - fee)
	 * @param cost
	 * @param feeWithoutCost
	 * @return costWithoutFee
	 */
    protected static BigDecimal costWithoutFee(BigDecimal cost, BigDecimal feeWithoutCost) {
		
		return cost.subtract(feeWithoutCost);
	}

}
