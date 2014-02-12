package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBExecution {

	private Connection dbConnection;
	private List<Statement> statements;
	private Statement alter;
	private Statement create;
	private Statement drop;
	private Statement select;
//	private PreparedStatement query;
//	private PreparedStatement update;
//	private PreparedStatement insert;
//	private PreparedStatement delete;


	public DBExecution(Connection dbConnection) {

		this.dbConnection = dbConnection;
		try {
			prepareStatements();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void prepareStatements() throws SQLException {

		statements = new ArrayList<Statement>();

		create = createStatement();

		alter = createStatement();

		drop = createStatement();

		select = createStatement();

//		query = createStatement("SELECT ? FROM ? WHERE ?");

//		update = createStatement("UPDATE ? SET ? WHERE ?");

//		insert = createStatement("INSERT INTO ? ?");

//		delete = createStatement("DELETE FROM ? WHERE ?");

	}

	private PreparedStatement createStatement(String statementSyntax) throws SQLException {
		PreparedStatement statement = dbConnection.prepareStatement(statementSyntax);
		statements.add(statement);
		return statement;
	}

	private Statement createStatement() throws SQLException {
		Statement statement = dbConnection.createStatement();
		statements.add(statement);
		return statement;
	}

	public int createTable(String statement) throws SQLException {

		return create.executeUpdate(statement);

	}


	public int dropTable(String statement) throws SQLException {

		return drop.executeUpdate(statement);

	}


	public void addColumn(String tableName, String columnName) throws SQLException {

		alter.executeUpdate("ALTER TABLE " + tableName + " ADD " + columnName + " varchar(32);");

	}

	public void deleteColumn(String tableName, String columnName) throws SQLException {

		alter.executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";");

	}

	/**
	
	public void insertRecord(String firstName, String lastName, Integer , String , String email) throws SQLException {

		insert.setString(1, Name);

		insert.setString(2, );

		insert.setInt(3, );

		insertRecord.setString(4, );

		insertRecord.setString(5, );

		insertRecord.executeUpdate();

	}

	public ResultSet getRecords(String columnNames, String tableNames) throws SQLException {

		return select.executeQuery("SELECT " + columnNames + " From " + tableNames + ";");

	}

	public void deleteRecord(String , String ) throws SQLException {

		deleteRecord.setString(1, );

		deleteRecord.setString(2, );

		deleteRecord.executeUpdate();

	}

	public ResultSet getRecordsAccordingEgn(String ) throws SQLException {

		recordsAccordingEgn.setString(1, );

		return recordsAccordingEgn.executeQuery();

	}

	public ResultSet getRecordsAccordingNameStartingWith(String startCharacters) throws SQLException {

		recordsAccordingName.setString(1, startCharacters + "%");

		return recordsAccordingName.executeQuery();

	}

	public ResultSet getRecordsAccordingNameContaining(String charSequence) throws SQLException {

		recordsAccordingName.setString(1, "%" + charSequence + "%");

		return recordsAccordingName.executeQuery();

	}

	public ResultSet getRecordsAccordingNameEndingOn(String endCharacter) throws SQLException {

		recordsAccordingName.setString(1, "%" + endCharacter);

		return recordsAccordingName.executeQuery();

	}

	public void update(String , String ) throws SQLException {

		updateEmail.setString(1, );

		updateEmail.setString(2, );

		updateEmail.executeUpdate();

	}
	*/

	public void close() throws SQLException {

		if (dbConnection != null) {

			dbConnection.close();

		}

		for (Statement statement : statements) {

			if (statement != null) {

				statement.close();

			}
		}
	}
}

