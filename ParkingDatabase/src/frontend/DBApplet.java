package frontend;


import java.io.IOException;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import database.DBConnectionSingleton;
import database.DBExecution;
import database.StatementParser;

public class DBApplet extends java.applet.Applet implements ActionListener {


	// The query to execute
	static final String query = "select 'Hello JDBC: ' || sysdate from dual";


	// The button to push for executing the query
	Button execute_button;

	// The place where to dump the query result
	TextArea output;

	// The connection to the database
	Connection conn;

	Button create_button;
	Button drop_button;

	// Create the User Interface
	public void init ()
	{
		this.setLayout (new BorderLayout ());
		Panel p = new Panel ();
		p.setLayout (new FlowLayout (FlowLayout.LEFT));
		execute_button = new Button ("Hello JDBC");
		p.add (execute_button);
		execute_button.addActionListener (this);
		this.add ("North", p);
		output = new TextArea (10, 60);
		this.add ("Center", output);

		create_button = new Button ("create");
		p.add (create_button);
		create_button.addActionListener (this);
		
		drop_button = new Button ("drop");
		p.add (drop_button);
		drop_button.addActionListener (this);

		try {
			conn = DBConnectionSingleton.getConnect("pren", "pren@wpi.edu");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Do the work
	public void actionPerformed (ActionEvent ev)
	{
		if (ev.getSource() == execute_button)
		{
			try
			{

				if (conn == null)
				{
					System.out.println("Connection does not exist.");
				}

				Statement stmt = conn.createStatement ();

				output.append ("Executing query " + query + "\n");
				ResultSet rset = stmt.executeQuery (query);

				while (rset.next ())
					output.append (rset.getString (1) + "\n");

				output.append ("done.\n");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				output.append (e.getMessage () + "\n");
			}
		}

		if (ev.getSource() == create_button)
		{
			try
			{

				if (conn == null)
				{
					System.out.println("Connection does not exist.");
				}

				DBExecution executor = new DBExecution(conn);
				StatementParser parser = new StatementParser("Items");

				output.append ("Executing create " + parser.getCreateStatement() + "\n");
				int rows = executor.createTable(parser.getCreateStatement());
				output.append (rows + " rows updated");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				output.append (e.getMessage () + "\n");
			}
		}
		
		if (ev.getSource() == drop_button)
		{
			try
			{

				if (conn == null)
				{
					System.out.println("Connection does not exist.");
				}

				DBExecution executor = new DBExecution(conn);
				StatementParser parser = new StatementParser("Items");

				output.append ("Executing drop " + parser.getCreateStatement() + "\n");
				int rows = executor.dropTable(parser.getDropStatement());
				output.append (rows + " rows updated");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				output.append (e.getMessage () + "\n");
			}
		}
		
	}
}

