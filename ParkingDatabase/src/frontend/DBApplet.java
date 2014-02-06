package frontend;

/*
 * This sample applet just selects 'Hello World' and the date from the database
 *
 * One way to get this work using appletviewer or a web browser with the Java plugin
 * for jre 5 or jre 6
 *  
 * 1. compile this file using jdk5 and use a jre 5 plugin and ojdbc5.jar
 *    or using jdk 6 and a jre 6 plugin and ojdbc6.jar
 * 2. put ojdbc5.jar or ojdbc6.jar at the same directory as the codebase 
 *    indicated in JdbcApplet.htm file
 * 3. modify JdbcApplet.htm file to let codebase point to the directory
 *    where JdbcApplet.class and ojdbc5.jar or ojdbc6.jar exist.
 *    using syntax of "file:/path_of_JdbcApplet/"
 * 4. use policytool to grant AllPermission of byte-codes at the codebase 
 *    indicated in .htm file to the applet.
 *    the URL syntax for codebase in file ~/.java.policy is
 *    "file:/path_of_JdbcApplet/*". This is to get around of java security.
 *    You could choose other permissions instead of AllPermission.
 */

import java.io.IOException;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import database.DBConnectionSingleton;

public class DBApplet extends java.applet.Applet implements ActionListener {

 
  // The query to execute
  static final String query = "select 'Hello JDBC: ' || sysdate from dual";
  

  // The button to push for executing the query
  Button execute_button;

  // The place where to dump the query result
  TextArea output;

  // The connection to the database
  Connection conn;

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

        // See if we need to open the connection to the database
        if (conn == null)
        {
        	System.out.println("Connection does not exist.");
        }

        // Create a statement
        Statement stmt = conn.createStatement ();

        // Execute the query
        output.append ("Executing query " + query + "\n");
        ResultSet rset = stmt.executeQuery (query);

        // Dump the result
        while (rset.next ())
          output.append (rset.getString (1) + "\n");

        // We're done
        output.append ("done.\n");
      }
      catch (Exception e)
      {
        e.printStackTrace();
        // Oops
        output.append (e.getMessage () + "\n");
      }
    }
  }
}

