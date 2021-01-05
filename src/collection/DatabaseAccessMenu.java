package collection;

import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Class to display the menu to access the database
 * 
 * @author chris pajtas
 * 
 * Known Issues:
 * - Using AS in queries does not work
 *
 */
public class DatabaseAccessMenu {
	
	/**
	 * The menu for accessing the database and operations
	 */
	public void databaseMenu() {
		
		DatabaseAccess doa = new DatabaseAccess();
		
		// ask user for login details (encrypt)
		
		boolean cont = true;
		String input = "";
		Scanner kb = new Scanner(System.in);
		
		while (cont) {
		System.out.println("Please enter the following options to access the database:");
		System.out.println("SELECT - View the table entries in the database, using user defined input parameters.");
		System.out.println("TABLES - Show a list of tables available to access.");
		System.out.println("UPDATE - Update a table in the music collection user user defined input parameters.");
		System.out.println("DELETE - Delete an entry from a table in the music collection.");
		System.out.println("EXIT - Exits the database program.");
		
		System.out.print("\nEnter a menu option: ");
		input = kb.next().toUpperCase();
		
		String tables = "";
		String columns = "";
		String conditions = "";
		String[] tableArr;
		
		ResultSet rs;
        
		
		switch(input) {
		case "SELECT":
			
			// ask user for table(s) to select from 
			System.out.println("Type each table you want to select from (seperate with commas, no spaces! ex. table1,table2,...)");
			tables = kb.next();
			
			// Splits user input into array of words
			tableArr = tables.split(",");
            
			// Displays the columns for user input
			doa.getColumns(tableArr);
			
			// ask user for columns to select
			System.out.println("Please enter the names of each column to select (seperate with commas, no spaces! ex. col1,col2 [try to type like table.col])");
			columns = kb.next();
			
			// ask for conditions to select
			System.out.println("Type each where condition (seperate with commas, no spaces! ex. col1=con1 and col2=con2,...)");
			System.out.println("Type \"NONE\" for no conditions.");
			conditions = kb.next();
			
			doa.selectFrom(columns, tables, conditions.toUpperCase());
			
			break;
		case "TABLES":
			doa.showTables();
			break;
		case "UPDATE":
			// ask user for table(s) to update
			// ask for conditions to select
			break;
		case "DELETE":
			// ask user for tables to delete
			// ask for conditions, alert them they should enter one
			break;
		case "EXIT":
			cont = false;
			break;
		default:
			// do nothing
		}
		
		}

	}
}
