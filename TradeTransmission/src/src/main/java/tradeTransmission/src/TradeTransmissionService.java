/**
 * 
 */
package tradeTransmission.src;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author amruta Trade transmission operations
 */
public class TradeTransmissionService {

	public HashMap<String, Trade> tradeStore = new LinkedHashMap<String, Trade>();
	private int counterForKey = -1;

	// add Trade
	public void addTrade(Trade trade) throws Exception {
		String key = checktradeID(trade);
		if (!checktradeID(trade).equalsIgnoreCase("NOT_FOUND")) {
			System.out.println(trade.getTradeId() + " trade is already present. checking version... ");

			int isVersionUpper = -1;
			try {
				isVersionUpper = checkVersion(trade, tradeStore.get(trade.getTradeId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// versions are same then override existing trade
			if (checkMaturityDate(trade.getMaturityDate(), tradeStore.get(trade.getTradeId()).getMaturityDate())
					&& isVersionUpper == 0) {
				tradeStore.replace(key, trade);
				System.out.println(trade.getTradeId() + " is overrided to the Store");
				// versions are not same then add new trade
			} else if (checkMaturityDate(trade.getMaturityDate(), tradeStore.get(trade.getTradeId()).getMaturityDate())
					&& isVersionUpper == 1) {
				tradeStore.put(counterForKey + 1 + "", trade);

				System.out.println(trade.getTradeId() + " is added to the Store");
			} else {
				System.out.println("Not able to add " + trade.getTradeId()
						+ " in the store as maturity date is lower than current date Or you are trying to add lower version");
			}
		} else {

			if (checkMaturityDate(trade.getMaturityDate(), trade.getCreatedDate())) {

				tradeStore.put(trade.getTradeId(), trade);
				System.out.println(trade.getTradeId() + " is added to the Store");

			} else {
				System.out.println("Not able to add " + trade.getTradeId()
						+ " in the store as maturity date is lower than current date");
			}
		}
		counterForKey++;
	}

	// print trade store
	public void printTrade() {
		for (String tId : tradeStore.keySet()) {
			System.out.println(tradeStore.get(tId).toString());
		}
	}

	// check if trade is empty
	public boolean checkIfTradeEmpty() {
		return tradeStore.isEmpty();
	}

	// Check if the lower version is being received by the store it will reject the
	// trade and throw an exception.
	// If the version is same it will override the existing record
	public int checkVersion(Trade t, Trade oldTrade) throws Exception {
		if (t.getVersion() < oldTrade.getVersion()) {
			throw new Exception(t.getVersion() + " is less than " + oldTrade);

		} else if (t.getVersion() == oldTrade.getVersion()) {
			return 0;
		} else {
			System.out.println("New Trade is having upper version. adding new trade...");
			return 1;
		}

	}

	// Check if maturityDate is greater than current date
	public boolean checkMaturityDate(Date maturityDate, Date CurrentDate) {

		if (CurrentDate.compareTo(maturityDate) > 0)
			return false;

		return true;

	}

	//Change Expiry flag to Y if trade is expired
	public void checkExpiredDates() {

		// SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();

		for (String strKey : tradeStore.keySet()) {
			if (currentDate.compareTo(tradeStore.get(strKey).getMaturityDate()) > 0) {
				Trade t = tradeStore.get(strKey);
				t.setExpiredFlag('Y');
				tradeStore.replace(strKey, t);
				System.out.println("Expiry Flag changed");
			}
		}

	}
//Check trade id to find duplicate
	public String checktradeID(Trade t) {

		for (String strKey : tradeStore.keySet()) {
			if (tradeStore.get(strKey).getTradeId() == t.getTradeId()) {
				return strKey;
			}
		}
		return "NOT_FOUND";

	}
// replace trade in tradestore
	public void replaceTrade(Trade t) {
		String key = checktradeID(t);
		tradeStore.replace(key, t);

	}
}
