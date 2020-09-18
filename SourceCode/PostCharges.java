package payrollDesign;
import java.util.Scanner;
import java.sql.*;

public class PostCharges {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the Employee ID:");
		String post_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String query = "select Union_charge FROM Employee WHERE ID=" + "'"+post_ID+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				double old_union_charge = rs.getDouble("Union_charge");
				System.out.println("Do you want to clear the old charges ( y / n )?:");
				String ans = in.nextLine();
				if("y".equals(ans)) old_union_charge = 0;
				System.out.println("Enter the charges to be added to the union charge:");
				double amount = in.nextDouble();
				double new_union_charge = old_union_charge + amount;
				String temp = "UPDATE Employee SET Union_charge = "+
				 new_union_charge + " WHERE ID=" + "'"+post_ID+"'";
				stmt.executeUpdate(temp);
				System.out.println("Charges Added Succesfully!");

			}
			else System.out.println("Not an Employee!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}