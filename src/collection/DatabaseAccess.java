package collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Types;

public class DatabaseAccess {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    // Constructor
    public DatabaseAccess() {
    	try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/music_collection?"
					+ "user=root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Calls the "show tables" command and lists all
     * the tables in the database.
     */
    public void showTables() {
    	try {
			statement = connect.createStatement();
			
			String query = "show tables";
	    	
	    	resultSet = statement.executeQuery(query);
	    	
	    	System.out.println();
	    	System.out.print("Tables in the Music Collection: ");
	    	while (resultSet.next()) {
	    		System.out.printf("%10s", resultSet.getString(1));
	    	}
	    	System.out.println("\n");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     *  Selects from band table
     * 
     * @param args Input select arguments
     */
    public void selectBands(String...args) {
    	try {			
    	statement = connect.createStatement();
    	
    	String query = "select * from bands";
    	
    	resultSet = statement.executeQuery(query);
    	
    	getMetaData(resultSet, false);
    	
    	while (resultSet.next()) {
    		String bandName = resultSet.getString("bandname");
    		String solo = resultSet.getString("solo");
    		System.out.printf("%10s %10s",bandName, solo);
    		System.out.println();
    	}
    	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Lists the columns in each table given by user,
     * 
     * @param tables Tables the user entered to get columns from
     * @return String of columns in each table
     */
    public int getColumns(String[] tables) {
    	try {
    		statement = connect.createStatement();
    		
    		String tablesS = "";
    		for (int i = 0; i < tables.length; i++) {
    			if (i == tables.length - 1) {
    				tablesS += tables[i];
    			} else {
    				tablesS += tables[i]+",";
    			}
    		}
    		
    		String query = "select * from " + tablesS;    	
    		
    		resultSet = statement.executeQuery(query);
    		
    		getMetaData(resultSet, true);
    		
    	
    	} catch (Exception e) {
    		
    	} finally {
    		
    	}
    	
		return tables.length;
    	
    }
    
    /**
     * 
     * Outputs selected sql query
     * 
     * @param col Columns to be shown
     * @param tables Tables to be used
     * @param conditions Conditions of the sql statement
     * @return True if successful, false if not
     */
    public boolean selectFrom(String col, String tables, String conditions) {
    	
    	// Array used to output SQL query data for each column
    	// specified by the user
    	String[] colArr = col.split(",");
    	
    	try {
			statement = connect.createStatement();
			
			// check if columns even exist (catch)
			
			if (!conditions.equals("NONE")) {
				resultSet = statement.executeQuery("select " + col + " from " + tables + " where "+ conditions);
			}
			else {
				resultSet = statement.executeQuery("select " + col + " from " + tables);
			}
			
			// Lists columns on top for the SQL query to display
			getMetaData(resultSet, false);
			
			// Display query results in table form
			displayQuery(resultSet, col.split(","));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid SQL Query: "+e.getMessage());
			System.out.println("");
		}
    	
		return true;
    	
    }
    
    /**
     * 
     * @param rs Result set
     * @param colArr Array of the column names to display in the query
     */
    private void displayQuery(ResultSet rs, String[] colArr) {
    	try {
    		
    		// Used to determine the type to pull from
    		ResultSetMetaData meta = rs.getMetaData();  		
    		
    		//meta.getColumn
			while(resultSet.next()) {
				// bands,albums -> bandName (1), albumName (2) -> String, String
				//              -> bandID (1), albumName(2) -> int, String
				// just checks strings rn
					for (int i=0; i < meta.getColumnCount(); i++) {
						if (i == meta.getColumnCount() - 1) {
							System.out.println(rs.getString(colArr[i]));
						} else {
							System.out.print(rs.getString(colArr[i]));
							System.out.printf("%10s", "");
						}
					//switch(type) {
					//case String:
						//break;
					//case Integer
					//}
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Retrieves the meta data from the input result set
     * 
     * @param rs ResultSet of the executed query
     * @param list Boolean is true if the function was called
     * 		to output a list of column names
     */
    private void getMetaData(ResultSet rs, boolean list) {
    	ResultSetMetaData rsmd;
    	
		try {
			rsmd = rs.getMetaData();
			rsmd.getColumnCount();
			
			if (list) {
			for (int i=1; i <= rsmd.getColumnCount(); i++) {
				if (i % 4 == 0 && i >= 4) {
					System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
				}
				else {
					System.out.print(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
					System.out.printf("%10s", "");
				}
				
			}
			}
			else {
				for (int i=1; i <= rsmd.getColumnCount(); i++) {
					System.out.print(rsmd.getColumnName(i));
					System.out.printf("%10s", "");
				}
			}
			
			//System.out.printf("%10s %10s", rsmd.getColumnName(2), rsmd.getColumnName(3));
			System.out.println();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
