package database;

public class StatementParser {
	
	private final String table;
	
	public StatementParser(String table){
		this.table = table;
	}

	public String getCreateStatement() throws StatementParserException{
		String createStatement = null;
		if (table == "Drivers") {
			createStatement ="CREATE TABLE " + table + " (" +
					"DLN NUMBER NOT NULL," +
					"dName VARCHAR2(20)," +
					"gender CHAR(1)," +
					"ifShopping VARCHAR2(3)," +
					"street VARCHAR2(30)," +
					"city VARCHAR2(20)," +
					"state VARCHAR2(20)," +
					"zipcode VARCHAR2(10)," +
					"CONSTRAINT DLN_pk PRIMARY KEY (DLN)," +
					"CONSTRAINT gender_ck CHECK (gender in ('M','F'))," +
					"CONSTRAINT shopping_ck CHECK (ifShopping in ('Yes','No'))" +
					")";
		}
		else if(table == "Cars") {
			createStatement ="CREATE TABLE " + table + " (" +
					"LP VARCHAR2(10) NOT NULL," +
					"brand VARCHAR2(20)," +
					"color VARCHAR2(20)," +
					"cType VARCHAR2(20)," +
					"CONSTRAINT LP_pk PRIMARY KEY (LP)" +
					")";
		}
		else if(table == "Ownship") {
			createStatement ="CREATE TABLE " + table + " (" +
					"DLN VARCHAR2(10) NOT NULL," +
					"LP VARCHAR2(10) NOT NULL," +
					"CONSTRAINT DL_pk PRIMARY KEY (DLN, LP)" +
					")";
		}
		else if(table == "Park_Transaction") {
			createStatement ="CREATE TABLE " + table + " (" +
					"receiptNum NUMBER NOT NULL," +
					"startTime TIMESTAMP," +
					"endTime TIMESTAMP," +
					"pPrice float," +
					"DLN NUMBER NOT NULL," +
					"CONSTRAINT DLNP_fk FOREIGN KEY(DLN) REFERENCES Drivers(DLN)," +
					"CONSTRAINT RN_pk PRIMARY KEY (receiptNum)" +
					")";
		}
		else if(table == "Grocery_Transaction") {
			createStatement ="CREATE TABLE " + table + " (" +
					"transactionNum NUMBER NOT NULL," +
					"totalQuantity NUMBER," +
					"discount float," +
					"DLN NUMBER NOT NULL," +
					"CONSTRAINT DLNG_fk FOREIGN KEY(DLN) REFERENCES Drivers(DLN)," +
					"CONSTRAINT TN_pk PRIMARY KEY (transactionNum)," +
					 "CONSTRAINT check_discount CHECK (discount < 1.0 AND discount > 0.0)" +
					")";
		}
		else if(table == "Items") {
			createStatement ="CREATE TABLE " + table + " (" +
					"itemID NUMBER NOT NULL," +
					"transactionNum NUMBER NOT NULL," +
					"quantity NUMBER," +
					"iType VARCHAR2(20)," +
					"iPrice float," +
					"iName VARCHAR2(20)," +
					"CONSTRAINT IT_pk PRIMARY KEY (itemID, transactionNum)" +
					")";
		}
		else{
			throw new StatementParserException("No such table name exists!");
		}
       return createStatement;
	}
	
	public String getDropStatement() throws StatementParserException{
		return "DROP TABLE " + table + " CASCADE CONSTRAINTS";
	}
	
	public String getAlterStatement() throws StatementParserException{
		String alterStatement = "alter table " + table + " modify ";
		return alterStatement;
	}
}
