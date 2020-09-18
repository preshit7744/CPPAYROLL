package payrollDesign;
import java.util.Scanner;
import java.sql.*;

public class DeleteEmployee {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the ID of the Employee you want to delete :");
		String del_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String temp = "DELETE FROM Employee WHERE ID=" + "'"+del_ID+"'";
			stmt.executeUpdate(temp);
			System.out.println("Deleted Succesfully!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}