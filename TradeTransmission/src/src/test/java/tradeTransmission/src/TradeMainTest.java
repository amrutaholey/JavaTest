package tradeTransmission.src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;


/**
 * Unit test for simple App.
 */
@DisplayName("Trade transmission test cases")

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TradeMainTest extends TradeTransmissionService

{

	TradeTransmissionService tradeTransmissionService = new TradeTransmissionService();
	Date todaysDate=Calendar.getInstance ().getTime ();
    SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Rigourous Test :-)
	 */
@Order(1)
	@Test
	/**
	 * Checks trade store is empty or not 
	 */
	public void testIstradeEmpty() {
		boolean check = tradeTransmissionService.checkIfTradeEmpty();

		System.out.println("trade store is empty: " + check);
		assertTrue(check);
	}
/**
 * Add to store is maturity date is greater that current date. negative
 * @throws Exception
 */
@Order(2)
	@Test
	public void checkMaturityDateNegative() throws Exception {
		
		Date maturityDate=sd.parse("20/10/2020");

        Trade t1=new Trade("T9",5,"CP-4","B3",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);

        System.out.println("Size of store: "+tradeTransmissionService.tradeStore.size());
        assertEquals(0,tradeTransmissionService.tradeStore.size());
	}
/**
 * Add to store is maturity date is greater that current date. positive
 * @throws Exception
 */
@Order(3)
	@Test
	public void checkMaturityDatePositive() throws Exception {
		
		Date maturityDate=sd.parse("20/10/2021");

        Trade t1=new Trade("T9",5,"CP-4","B3",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);

        System.out.println("Size of store: "+tradeTransmissionService.tradeStore.size());
        assertEquals(1,tradeTransmissionService.tradeStore.size());
        assertNotNull(tradeTransmissionService.tradeStore);
	}
/**
 * If maturity date is expired then updates flag valye to 'Y'
 * @throws Exception
 */
@Order(4)
	@Test
	public void testCheckExpiry() throws Exception {
	Date maturityDate=sd.parse("20/10/2021");
	Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
    tradeTransmissionService.addTrade(t1);
    maturityDate = todaysDate;
	Trade t8 = new Trade("T1", 3, "CP-1", "B1", maturityDate, todaysDate, 'N');
	tradeTransmissionService.replaceTrade(t8);
	tradeTransmissionService.checkExpiredDates();
		
		int count =0;
		for (String strKey : tradeTransmissionService.tradeStore.keySet()) {
			
			Trade t = tradeTransmissionService.tradeStore.get(strKey);
			if(t.getExpiredFlag()=='Y') {
				count++;
			}
		}
		assertEquals(count, 1);
}
/**
 * Check if trade is already exist in the store. Positive test
 * @throws Exception
 */
@Order(5)
	@Test
	public void  checkDuplicateTradePositive() throws Exception {
		Date maturityDate=sd.parse("20/10/2021");

		 Trade t1=new Trade("T1",1,"CP-1","B1",maturityDate, todaysDate, 'N');
	        tradeTransmissionService.addTrade(t1);
	     Trade t2=new Trade("T1",2,"CP-1","B1",maturityDate, todaysDate, 'N');
	        String tradeid = tradeTransmissionService.checktradeID(t2);
	        System.out.println("Trade found");
	        assertEquals(tradeid,"T1" );
	        assertNotNull(tradeid);
	        
	}
/**
 * Check if trade is already exist in the store. Negative test
 * @throws Exception
 */
@Order(6)
	@Test
	public void  checkDuplicateTradeNegative() throws Exception {
		Date maturityDate=sd.parse("20/10/2021");

		 Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
	        tradeTransmissionService.addTrade(t1);
	     Trade t2=new Trade("T2",2,"CP-1","B1",maturityDate, todaysDate, 'N');
	        String tradeid = tradeTransmissionService.checktradeID(t2);
	        System.out.println("Trade not found");
	        assertEquals(tradeid,"NOT_FOUND" );
	        assertNotNull(tradeid);
	        
	}
/**
 * Checks maturity date expired or not
 * @throws Exception
 */
@Order(7)
	@Test
	public void checkMaturityDate() throws Exception {
		
		Date maturityDate=sd.parse("20/10/2020");
		boolean check= tradeTransmissionService.checkMaturityDate(maturityDate,todaysDate);
		assertEquals(check, false);
		
	}

/**
 * Adds trade with same trade id and higher version
 * @throws Exception
 */
@Order(8)
	@Test
	public void addTradePositive() throws Exception
	{
		Date maturityDate=sd.parse("20/10/2021");

		Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);
        int count =0;
		for (String strKey : tradeTransmissionService.tradeStore.keySet()) {
			
			Trade t = tradeTransmissionService.tradeStore.get(strKey);
			if(t.getTradeId().equals("T1") && t.getVersion()==3) {
				count++;
			}
		}
		assertEquals(count, 1);
	}
/**
 * Add trade with same trade id and lower version
 * @throws Exception
 */
@Order(9)
	@Test
	public void addTradeNegative() throws Exception
	{
		Date maturityDate=sd.parse("20/10/2021");

		Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);
        Trade t2=new Trade("T1",2,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t2);
        int count =0;
		for (String strKey : tradeTransmissionService.tradeStore.keySet()) {
			
			Trade t = tradeTransmissionService.tradeStore.get(strKey);
			if(t.getTradeId().equals("T1") && t.getVersion()==2) {
				count++;
			}
		}
		assertEquals(count, 0);
	}
	
/**
 * Add trade with same trade id and same version
 * @throws Exception
 */
@Order(10)
	@Test
	public void addTradeSameVersion() throws Exception
	{
		Date maturityDate=sd.parse("20/10/2021");

		Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);
        Trade t2=new Trade("T1",3,"CP-3","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t2);
        int count =0;
		for (String strKey : tradeTransmissionService.tradeStore.keySet()) {
			
			Trade t = tradeTransmissionService.tradeStore.get(strKey);
			if(t.getTradeId().equals("T1") && t.getVersion()==3 && t.getCounterPartyId().equalsIgnoreCase("CP-3")) {
				count++;
			}
			if(t.getTradeId().equals("T1") && t.getVersion()==3 && t.getCounterPartyId().equalsIgnoreCase("CP-1")) {
				count++;
			}
		}
		assertEquals(count, 1);
	}
/**
 * Prints all trades
 * @throws Exception
 */
@Order(11)
	@Test
	public void PrintTrades() throws Exception {
		Date maturityDate=sd.parse("20/10/2021");
		Trade t1=new Trade("T1",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t1);
        Trade t2=new Trade("T2",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t2);
        Trade t3=new Trade("T3",3,"CP-1","B1",maturityDate, todaysDate, 'N');
        tradeTransmissionService.addTrade(t3);
        
        tradeTransmissionService.printTrade();
		
        assertTrue(true);
		
	}
}
