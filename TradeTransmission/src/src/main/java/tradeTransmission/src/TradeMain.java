package tradeTransmission.src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class TradeMain {
	public static void main(String[] args) throws Exception {
		TradeTransmissionService tradeTransmissionService = new TradeTransmissionService();

		Date todaysDate = Calendar.getInstance().getTime();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

//		Adding Trade T1

		Date maturityDate = sd.parse("20/04/2022");
		Trade t1 = new Trade("T1", 1, "CP-1", "B1", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t1);

		// Adding Trade T2
		maturityDate = sd.parse("20/04/2022");
		Trade t2 = new Trade("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t2);
//				tf.printTrade();	

		// Changing Trade T3
		
		Trade t3 = new Trade("T3", 2, "CP-4", "B1", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t3);

		// Adding Trade T4
		maturityDate = sd.parse("20/05/2021");
		Trade t4 = new Trade("T4", 2, "CP-3", "B2", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t4);
		
		
		// Adding Trade T5 with old tradeID with lower version
		maturityDate = sd.parse("20/05/2021");
		Trade t5 = new Trade("T4", 1, "CP-4", "B3", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t5);

		// Adding Trade T6 with old tradeID with equal version
		maturityDate = sd.parse("20/05/2021");
		Trade t6 = new Trade("T4", 2, "CP-5", "B3", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t6);

		// Adding Trade T7 with old tradeID with higher version
		maturityDate = sd.parse("20/05/2021");
		Trade t7 = new Trade("T4", 4, "CP-6", "B3", maturityDate, todaysDate, 'N');
		tradeTransmissionService.addTrade(t7);

			
		// Display all Trade
		System.out.println("\n\n");
		System.out.println("Displaying total number of Trade in the list");
		tradeTransmissionService.printTrade();
		System.out.println("\n\n");

		// change maturity date to todays date for 2 trades
		System.out.println("Checking for Expired Flag");
		maturityDate = todaysDate;
		Trade t8 = new Trade("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
		tradeTransmissionService.replaceTrade(t8);

		maturityDate =todaysDate;
		Trade t9 = new Trade("T4", 2, "CP-3", "B2", maturityDate, todaysDate, 'N');
		tradeTransmissionService.replaceTrade(t9);
		
		
		//Change expiry flag of matured trades
		tradeTransmissionService.checkExpiredDates();
		tradeTransmissionService.printTrade();

	}
}
