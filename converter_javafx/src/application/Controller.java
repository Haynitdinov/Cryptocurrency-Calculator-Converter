package application;

/**
 * This class is a base class for all user interface controls 
 */

import java.math.BigDecimal;
import java.util.HashMap;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class Controller {

	/**
	 * create object exmo
	 * then convert to JSON object
	 * 
	 * @param your private key
	 * @param your private secret
	 */
	static Exmo exmo = new Exmo("your_key","your_secret");
	static JSONObject jsonTicker = new JSONObject(exmo.Request("ticker", null));
	JSONObject jsonBalances = new JSONObject(exmo.Request("user_info", null));

	
	/**
	 * declaration of variables extracted from the request
	 */
	private BigDecimal btcBuyPrice = new BigDecimal(jsonTicker.getJSONObject("BTC_USD").getString("buy_price"));
	private BigDecimal btcSellPrice = new BigDecimal(jsonTicker.getJSONObject("BTC_USD").getString("sell_price"));
	private BigDecimal ltcBuyPrice = new BigDecimal(jsonTicker.getJSONObject("LTC_USD").getString("buy_price"));
	private BigDecimal ltcSellPrice = new BigDecimal(jsonTicker.getJSONObject("LTC_USD").getString("sell_price"));
	private BigDecimal xrpBuyPrice = new BigDecimal(jsonTicker.getJSONObject("XRP_USD").getString("buy_price"));
	private BigDecimal xrpSellPrice = new BigDecimal(jsonTicker.getJSONObject("XRP_USD").getString("sell_price"));

	private String balanceUsd = jsonBalances.getJSONObject("balances").getString("USD");
	private String balanceBtc = jsonBalances.getJSONObject("balances").getString("BTC");
	private String balanceLtc = jsonBalances.getJSONObject("balances").getString("LTC");
	private String balanceXrp = jsonBalances.getJSONObject("balances").getString("XRP");

	BigDecimal fee = new BigDecimal("0");
	private final BigDecimal FEE_01 = new BigDecimal("0.001");
	private final BigDecimal FEE_02 = new BigDecimal("0.002");

	private static BigDecimal priceBtc = new BigDecimal("0.0");
	private static BigDecimal priceLtc = new BigDecimal("0.0");
	private static BigDecimal priceXrp = new BigDecimal("0.0");
	private static BigDecimal amountBtc = new BigDecimal("0.0");
	private static BigDecimal amountLtc = new BigDecimal("0.0");
	private static BigDecimal amountXrp = new BigDecimal("0.0");

	@FXML
	private Button buttonBuy;

	@FXML
	private Button buttonSell;

	@FXML
	private Button buttonRefresh;

	@FXML
	private RadioButton radioButtonIfWantToBuy;

	@FXML
	private RadioButton radioButtonIfWantToSell;

	@FXML
	private RadioButton radioButtonFee_0_1_percent;

	@FXML
	private RadioButton radioButtonFee_0_2_percent;

	@FXML
	private Label labelBTC_buy;

	@FXML
	private Label labelBTC_sell;

	@FXML
	private Label labelLTC_buy;

	@FXML
	private Label labelLTC_sell;

	@FXML
	private Label labelXRP_buy;

	@FXML
	private Label labelXRP_sell;

	@FXML
	private TextField amountForBtcExchange;

	@FXML
	private TextField amountForLtcExchange;

	@FXML
	private TextField amountForXrpExchange;

	@FXML
	private TextField costForBtc;

	@FXML
	private TextField costForLtc;

	@FXML
	private TextField costForXrp;

	@FXML
	private TextField amountForBtcExchangeWithoutFee;

	@FXML
	private TextField amountForLtcExchangeWithoutFee;

	@FXML
	private TextField amountForXrpExchangeWithoutFee;

	@FXML
	private TextField feeForBtcExchange;

	@FXML
	private TextField feeForLtcExchange;

	@FXML
	private TextField feeForXrpExchange;

	@FXML
	private Label showMeMyUSD;

	@FXML
	private Label showMeMyBTC;

	@FXML
	private Label showMeMyLTC;

	@FXML
	private Label showMeMyXRP;

	

	/**
	 * This method show our balance and current price
	 */
	public void buttonRefreshBalance() {

		showMeMyUSD.setText(balanceUsd.toString());
		showMeMyBTC.setText(balanceBtc.toString());
		showMeMyLTC.setText(balanceLtc.toString());
		showMeMyXRP.setText(balanceXrp.toString());

		JSONObject jsonTicker2 = new JSONObject(exmo.Request("ticker", null));

		BigDecimal btcBuyPrice = new BigDecimal(jsonTicker2.getJSONObject("BTC_USD").getString("buy_price"));
		BigDecimal btcSellPrice = new BigDecimal(jsonTicker2.getJSONObject("BTC_USD").getString("sell_price"));
		BigDecimal ltcBuyPrice = new BigDecimal(jsonTicker2.getJSONObject("LTC_USD").getString("buy_price"));
		BigDecimal ltcSellPrice = new BigDecimal(jsonTicker2.getJSONObject("LTC_USD").getString("sell_price"));
		BigDecimal xrpBuyPrice = new BigDecimal(jsonTicker2.getJSONObject("XRP_USD").getString("buy_price"));
		BigDecimal xrpSellPrice = new BigDecimal(jsonTicker2.getJSONObject("XRP_USD").getString("sell_price"));

		labelBTC_buy.setText(btcBuyPrice.toString());
		labelLTC_buy.setText(ltcBuyPrice.toString());
		labelXRP_buy.setText(xrpBuyPrice.toString());
		labelBTC_sell.setText(btcSellPrice.toString());
		labelLTC_sell.setText(ltcSellPrice.toString());
		labelXRP_sell.setText(xrpSellPrice.toString());

	}

	private BigDecimal fee() {
		if (radioButtonFee_0_1_percent.isSelected())
			fee = FEE_01;

		else if (radioButtonFee_0_2_percent.isSelected())
			fee = FEE_02;
		return fee;
	}

	/**
	 * This method we use to check if input is a number
	 * 
	 * @param string
	 * @return true or false
	 */
	private boolean isNumber(String string) {
		for (int i = 0; i < string.length(); i++) {
			if ((string.charAt(i) >= '0' && string.charAt(i) <= '9') || string.charAt(i) == '.') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a buy order only if inputed amount is bigger than zero.
	 * @param event
	 */
	public void pressbuttonBuy(ActionEvent event) {
		if (amountBtc.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "BTC_USD");
					put("quantity", amountBtc.toString());
					put("price", btcBuyPrice.toString());
					put("type", "buy");
				}
			});
		}

		if (amountLtc.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "LTC_USD");
					put("quantity", amountLtc.toString());
					put("price", ltcBuyPrice.toString());
					put("type", "buy");
				}
			});
		}

		if (amountXrp.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "XRP_USD");
					put("quantity", amountXrp.toString());
					put("price", xrpBuyPrice.toString());
					put("type", "buy");
				}
			});
		}
	}

	/**
	 * Create a sell order only if inputed amount is bigger than zero.
	 * @param event
	 */
	public void pressbuttonSell(ActionEvent event) {
		if (amountLtc.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "BTC_USD");
					put("quantity", amountBtc.toString());
					put("price", btcSellPrice.toString());
					put("type", "sell");
				}
			});
		}
		if (amountLtc.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "LTC_USD");
					put("quantity", amountLtc.toString());
					put("price", ltcSellPrice.toString());
					put("type", "sell");
				}
			});
		}

		if (amountXrp.compareTo(BigDecimal.ZERO) > 0) {
			exmo.Request("order_create", new HashMap<String, String>() {
				{
					put("pair", "XRP_USD");
					put("quantity", amountXrp.toString());
					put("price", xrpSellPrice.toString());
					put("type", "sell");
				}
			});
		}
	}

	/**
	 * This method we use to calculate profit
	 * 
	 * @param amount
	 * @param textFieldAmount
	 * @param textFieldCost
	 * @param price
	 * @param textFieldFee
	 * @param amountWithoutFee
	 * @param currentFee
	 */
	private void calculateAll(BigDecimal amount, TextField textFieldAmount, TextField textFieldCost, BigDecimal price,
			TextField textFieldFee, TextField amountWithoutFee, BigDecimal currentFee) {
		
		try{
			amount = new BigDecimal(textFieldAmount.getText());
		textFieldCost.setText(Calculation.cost(amount, price).toString());
		textFieldFee
				.setText(Calculation.feeWithoutCost(new BigDecimal(textFieldCost.getText()), currentFee).toString());
		amountWithoutFee.setText(Calculation
				.costWithoutFee(new BigDecimal(textFieldCost.getText()), new BigDecimal(textFieldFee.getText()))
				.toString());
		}
		catch(Exception ex) {
			ex.getMessage();
		}

	}

	public void enterAmountForBtcExchange(KeyEvent event) {
		if (isNumber(amountForBtcExchange.getText())) {
			this.calculateAll(amountBtc, amountForBtcExchange, costForBtc, priceBtc, feeForBtcExchange,
					amountForBtcExchangeWithoutFee, fee());
		} else {
			event.consume();
		}
	}

	public void enterAmountForLtcExchange(KeyEvent event) {
		if (isNumber(amountForLtcExchange.getText())) {
			this.calculateAll(amountLtc, amountForLtcExchange, costForLtc, priceLtc, feeForLtcExchange,
					amountForLtcExchangeWithoutFee, fee());
		} else {
			event.consume();
		}

	}

	public void enterAmountForXrpExchange(KeyEvent event) {
		if (isNumber(amountForXrpExchange.getText())) {

			this.calculateAll(amountXrp, amountForXrpExchange, costForXrp, priceXrp, feeForXrpExchange,
					amountForXrpExchangeWithoutFee, fee());
		} else {
			event.consume();
		}
	}

	public void pressradioButtonIfWantToBuy(ActionEvent event) {
		try {
			if (radioButtonIfWantToBuy.isSelected()) {

				priceBtc = btcBuyPrice;
				priceLtc = ltcBuyPrice;
				priceXrp = xrpBuyPrice;
				radioButtonIfWantToSell.setSelected(false);
				this.calculateAll(amountXrp, amountForXrpExchange, costForXrp, priceXrp, feeForXrpExchange,
						amountForXrpExchangeWithoutFee, fee());
				this.calculateAll(amountBtc, amountForBtcExchange, costForBtc, priceBtc, feeForBtcExchange,
						amountForBtcExchangeWithoutFee, fee());
				this.calculateAll(amountLtc, amountForLtcExchange, costForLtc, priceXrp, feeForLtcExchange,
						amountForLtcExchangeWithoutFee, fee());
			}
		} catch (Exception ex) {
			ex.getMessage();
		}

	}

	public void pressradioButtonIfWantToSell(ActionEvent event) {
		try {
			if (radioButtonIfWantToSell.isSelected()) {

				priceBtc = btcSellPrice;
				priceLtc = ltcSellPrice;
				priceXrp = xrpSellPrice;
				radioButtonIfWantToBuy.setSelected(false);
				this.calculateAll(amountXrp, amountForXrpExchange, costForXrp, priceXrp, feeForXrpExchange,
						amountForXrpExchangeWithoutFee, fee());
				this.calculateAll(amountBtc, amountForBtcExchange, costForBtc, priceBtc, feeForBtcExchange,
						amountForBtcExchangeWithoutFee, fee());
				this.calculateAll(amountLtc, amountForLtcExchange, costForLtc, priceXrp, feeForLtcExchange,
						amountForLtcExchangeWithoutFee, fee());
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	public void pressradioButtonForFee_0_1_percent(ActionEvent event) {
		try {
			if (radioButtonFee_0_1_percent.isSelected()) {
				radioButtonFee_0_2_percent.setSelected(false);
			}
			this.calculateAll(amountXrp, amountForXrpExchange, costForXrp, priceXrp, feeForXrpExchange,
					amountForXrpExchangeWithoutFee, fee());
			this.calculateAll(amountBtc, amountForBtcExchange, costForBtc, priceBtc, feeForBtcExchange,
					amountForBtcExchangeWithoutFee, fee());
			this.calculateAll(amountLtc, amountForLtcExchange, costForLtc, priceXrp, feeForLtcExchange,
					amountForLtcExchangeWithoutFee, fee());

		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	public void pressradioButtonForFee_0_2_percent(ActionEvent event) {
		try {
			if (radioButtonFee_0_2_percent.isSelected()) {
				radioButtonFee_0_1_percent.setSelected(false);
			}
			this.calculateAll(amountXrp, amountForXrpExchange, costForXrp, priceXrp, feeForXrpExchange,
					amountForXrpExchangeWithoutFee, fee());
			this.calculateAll(amountBtc, amountForBtcExchange, costForBtc, priceBtc, feeForBtcExchange,
					amountForBtcExchangeWithoutFee, fee());
			this.calculateAll(amountLtc, amountForLtcExchange, costForLtc, priceXrp, feeForLtcExchange,
					amountForLtcExchangeWithoutFee, fee());

		} catch (Exception ex) {
			ex.getMessage();
		}
	}

}
