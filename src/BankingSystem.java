import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.table.DefaultTableModel;
/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	private static int accountNum;
	private static int customerId;
	private static DefaultTableModel tableModel;
	private static boolean isTableModel = false;
	private static String query;
	
	// JDBC Objects
	private static Connection con;
	private static PreparedStatement stmt;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			//con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
		System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
				/* insert your code here */
         if(name.length() > 15){
			System.out.println(":: CREATE NEW CUSTOMER - ERROR: Name length cannot exceed 15 characters");
			return;
		}

        if (!gender.equals("M") && !gender.equals("F")) {
			System.out.println(":: CREATE NEW CUSTOMER - ERROR: Gender must be either M or F");
			return;
		}

		try{
			int i = Integer.parseInt(age);
			if(i < 0){
				System.out.println(":: CREATE NEW CUSTOMER - ERROR: Age must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println(":: CREATE NEW CUSTOMER - ERROR: Invalid AGE");
			return;
		}

		try{
			int i = Integer.parseInt(pin);
			if(i < 0){
				System.out.println(":: CREATE NEW CUSTOMER - ERROR: Pin must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println(":: CREATE NEW CUSTOMER - ERROR: INVALID PIN");
			return;
		}

		try {
			query = "INSERT INTO p1.customer(name,gender,age,pin) VALUES (?,?,?,?)";
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, gender);
			stmt.setString(3, age);
			stmt.setString(4, pin);
			stmt.executeUpdate();

			ResultSet tableKeys = stmt.getGeneratedKeys();
            tableKeys.next();
            customerId = tableKeys.getInt(1);
			//System.out.println(customerId);
			System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
		}catch (Exception e){
			System.out.println(":: CREATE NEW CUSTOMER - FAILED");
		}
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
		System.out.println(":: OPEN ACCOUNT - RUNNING");
				/* insert your code here */

		try{
			Integer.parseInt(id);
		}catch (Exception e){
			System.out.println(":: OPEN ACCOUNT - ERROR: INVALID CUSTOMER ID");
			return;
		}

		if(!type.toUpperCase().equals("C") && !type.toUpperCase().equals("S")) {
			System.out.println(":: OPEN ACCOUNT - ERROR: Account type must be either C or S");
			return;
		}

		try{
			int b = Integer.parseInt(amount);
			if(b < 0){
				System.out.println(":: OPEN ACCOUNT - ERROR: deposit must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println(":: OPEN ACCOUNT - ERROR: INVALID DEPOSIT");
			return;
		}

		try{
			query = "INSERT INTO p1.account(id,balance,type,status) VALUES (?,?,?,'A')";
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, id);
			stmt.setString(2, amount);
			stmt.setString(3, type);
			stmt.executeUpdate();

			ResultSet tableKeys = stmt.getGeneratedKeys();
            tableKeys.next();
            accountNum = tableKeys.getInt(1);
			//System.out.println(accountNum);
			System.out.println(":: OPEN ACCOUNT - SUCCESS");
		}catch (Exception e ){
			System.out.println(":: OPEN ACCOUNT - FAILED");
			e.getStackTrace();
		}
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
		System.out.println(":: CLOSE ACCOUNT - RUNNING");
				/* insert your code here */
		try{
			query = "update p1.account set status = 'I', balance = 0 where p1.account.number = ?" ;
			stmt = con.prepareStatement(query);
			stmt.setString(1, accNum);
			stmt.executeUpdate();
			System.out.println(":: CLOSE ACCOUNT - SUCCESS");
		}catch (Exception e ){
			System.out.println(":: CLOSE ACCOUNT - FAILED");
		}
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
		System.out.println(":: DEPOSIT - RUNNING");
				/* insert your code here */

		try{
			int b = Integer.parseInt(amount);
			if(b < 0){
				System.out.println(":: DEPOSIT - ERROR: deposit must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println(":: DEPOSIT - ERROR: INVALID DEPOSIT AMOUNT");
			return;
		}

		try{
			query = "update p1.account set balance = balance + ?  where number = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, amount);
			stmt.setString(2, accNum);
			stmt.executeUpdate();
		    System.out.println(":: DEPOSIT - SUCCESS");
		}catch (Exception e){
			System.out.println(":: DEPOSIT - FAILED");
		}
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
		System.out.println(":: WITHDRAW - RUNNING");
				/* insert your code here */
		try{
			int b = Integer.parseInt(amount);
			if(b < 0){
				System.out.println(":: WITHDRAW - ERROR: withdraw must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println(":: WITHDRAW - ERROR: INVALID WITHDRAW AMOUNT");
			return;
		}

		try{
			if(!isEnoughFund(accNum, amount)){
				System.out.println(":: WITHDRAW - ERROR - NOT ENOUGH FUNDS");
				return;
			}else {
                query = "update p1.account set balance = balance - ? where number = ?";
				stmt = con.prepareStatement(query);
				stmt.setString(1, amount);
				stmt.setString(2, accNum);
				stmt.executeUpdate();
            }           
            rs.close();
			System.out.println(":: WITHDRAW - SUCCESS");
		}catch (Exception e){
			System.out.println(":: WITHDRAW - FAILED");
		}		
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
		System.out.println(":: TRANSFER - RUNNING");
				/* insert your code here */	

		try{
			int b = Integer.parseInt(amount);
			if(b < 0){
				System.out.println(":: TRANSFER - ERROR: transfer amount must be greater or equal to 0");
				return;
			}
		}catch (Exception e){
			System.out.println("::TRANSFER - ERROR: INVALID TRANSFER AMOUNT");
			return;
		}

		try{
			if(!isEnoughFund(srcAccNum, amount)){
				System.out.println(":: TRANSFER - ERROR - NOT ENOUGH FUNDS");
				return;
			}else {
				query = "update p1.account set balance = balance - ? where number = ?";
				stmt = con.prepareStatement(query);
				stmt.setString(1, amount);
				stmt.setString(2, srcAccNum);
				stmt.executeUpdate();

				query = "update p1.account set balance = balance + ? where number = ?";
				stmt = con.prepareStatement(query);
				stmt.setString(1, amount);
				stmt.setString(2, destAccNum);
				stmt.executeUpdate();
			}           
			rs.close();
			System.out.println(":: TRANSFER - SUCCESS");
		}catch (Exception e){
			System.out.println(":: TRANSFER - FAILED");
		}	
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) {
		System.out.println(":: ACCOUNT SUMMARY - RUNNING");
				/* insert your code here */		

		try{
			query = "select number,balance,status from p1.account where id = ? AND status != 'I'";
			stmt = con.prepareStatement(query);
			stmt.setString(1, cusID);
			rs = stmt.executeQuery();

			if(isTableModel) {
				tableModel.addRow(new Object[]{"NUMBER", "BALANCE"});
			}
			System.out.printf("%s %12s %n","NUMBER","BALANCE");
			System.out.printf("%s %12s %n","--------","---------");

			int total  = 0;
			while(rs.next()){
				total += rs.getInt(2);
				if(isTableModel)tableModel.addRow(new Object[]{rs.getInt(1), rs.getInt(2)});
				System.out.printf("%s %12s %n", rs.getInt(1), rs.getInt(2));
			}

			if(isTableModel) {
				tableModel.addRow(new Object[]{"------", "-------"});
				tableModel.addRow(new Object[]{"TOTAL", Integer.toString(total)});
			}
			System.out.printf("%s %12s %n","--------","---------");
			System.out.printf("%s %12s %n","TOTAL", total);

			rs.close();
			System.out.println();
			System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
		}catch (Exception e){
			System.out.println(":: ACCOUNT SUMMARY - FAILED");
		}
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
		System.out.println(":: REPORT A - RUNNING");
				/* insert your code here */	
		try{
			query =  "select p1.customer.id, name,gender, age, total from ((select id, sum(balance) as total from p1.account where status != 'I' group by id) as tb inner join p1.customer on p1.customer.id = tb.id) order by total desc";
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			if(isTableModel) {
				tableModel.addRow(new Object[]{"ID", "  NAME  ", "  Gender  ", "  Age   ", "  Total  "});
			}
			System.out.printf("%10s %10s %10s %10s %10s %n","Id","Name","Gender","Age","Total");
			System.out.printf("%10s %10s %10s %10s %10s %n","-----------","-----------","---------","----------","----------");

			while(rs.next()){
					int id = rs.getInt(1);
					String name = rs.getString(2);
					char gender = rs.getString(3).charAt(0);
					int age = rs.getInt(4);
					int total = rs.getInt(5);
					
					if(isTableModel){
						tableModel.addRow(new Object[]{id, name, gender, age, total});
					}	
					System.out.printf("%10s %10s %10s %10s %10s %n",id, name, gender, age, total);
			}
			System.out.println();
			System.out.println(":: REPORT A - SUCCESS");
			rs.close();
		}catch (Exception e){
				System.out.println(":: REPORT A - FAILED");
		}
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
		System.out.println(":: REPORT B - RUNNING");
				/* insert your code here */	
		try{
			query = "select AVG(total) from ((select id, sum(balance) as total from p1.account where status != 'I' group by id) as tb inner join p1.customer on p1.customer.id = tb.id) where age >= ? AND age <= ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, min);
			stmt.setString(2, max);
			rs = stmt.executeQuery();	

			if(isTableModel) {
				tableModel.addRow(new Object[]{"Average "});
				tableModel.addRow(new Object[]{"-----------"});
			}

			int avg = 0;
			while(rs.next()) {
				avg = rs.getInt(1);
			}
			if(isTableModel){
				tableModel.addRow(new Object[]{avg});
			}	
			System.out.println("Average");
			System.out.println("----------");
			System.out.println(avg);
			System.out.println();
			System.out.println(":: REPORT B - SUCCESS");

			rs.close();
		}catch (Exception e){
			System.out.println(":: REPORT B - FAILED");
		}
	}

	public static int getAccountNum(){
        return accountNum;
    }

	public static int getCustomerId(){
        return customerId;
    }

	public static void setTableModel(){
        isTableModel = true;
        tableModel = new DefaultTableModel(new String[5], 0);
    }

	public static Boolean authenticate(String id, String pin){
		try{
			query = "Select * from p1.customer where id = ? AND pin = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setString(2, pin);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e){
			System.out.println("Failed to authenticate");
			return false;
		}
	}

	public static Boolean validateCustomerId(String id) {
		try{
			query = "Select * from p1.customer where id = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e){
			System.out.println("Failed to validate customer id");
			return false;
		}
    }

	public static Boolean validateAccountNum(String accNum){
		try{
			query = "Select * from p1.Account where Number  = ? AND status != 'I'";
			stmt = con.prepareStatement(query);
			stmt.setString(1, accNum);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e){
			System.out.println("Failed to validate account number");
			return false;
		}
    }

	public static Boolean validatePermission(String id,String accNum){
        try{
            query = "Select * from p1.Account where id = ? AND Number = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setString(2, accNum);
            rs = stmt.executeQuery();
			return rs.next();
        }catch (Exception e){
            System.out.println("Failed to validate permission");
			return false;
        }
    }

	public static DefaultTableModel getTableModel(){
        return tableModel;
    }

	public static boolean isEnoughFund(String accNum, String amount){
		try{
			query = "select balance from p1.account where number = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, accNum);
			rs = stmt.executeQuery();
			int balance = 0;

			while(rs.next()){
                balance += rs.getInt(1);
            }
			rs.close();
			return  balance - Integer.parseInt(amount) < 0 ? false: true;
		}catch (Exception e){
			System.out.println(":: CHECK BALANCE - FAILED");
			return false;
		}		
    }

	public static void close(){
        try {
			stmt.close();
            con.close();
        }catch (Exception e){
            System.out.println("Close Failed");
        }
    }
}
