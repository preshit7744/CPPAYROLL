package payrollDesign;
import java.util.Scanner;
import java.sql.*;

public class PostSalesReceipt {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the ID of the Employee you want to post the receipt to:");
		String post_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String query = "select Amount_to_be_paid,Commision_rate FROM Employee WHERE ID=" + "'"+post_ID+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				double com_rate = rs.getDouble("Commision_rate");
				double old_rem_pay = rs.getDouble("Amount_to_be_paid");
				System.out.println("Enter the amount of the receipt :");
				double amount = in.nextDouble();
				double new_rem_pay = old_rem_pay + com_rate * amount / 100;
				String temp = "UPDATE Employee SET Amount_to_be_paid = "+
				 new_rem_pay + " WHERE ID=" + "'"+post_ID+"'";
				stmt.executeUpdate(temp);
				System.out.println("Comission Added Succesfully!");

			}
			else System.out.println("Not an Employee!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}