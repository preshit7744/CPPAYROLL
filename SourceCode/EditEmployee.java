package payrollDesign;
import java.util.Scanner;
import java.sql.*;
import java.util.HashMap;

public class EditEmployee {
	private static HashMap<Integer, String> map;
	private static String changedValue;
	private static Scanner in = new Scanner(System.in);
	private static void GenerateMap() {
		map = new HashMap<Integer, String>();
		map.put(1,"Name");
		map.put(2,"Contact_number");
		map.put(3,"Employee_type");
		map.put(4,"Union_charge");
		map.put(5,"Salary_monthly");
		map.put(6,"Salary_perhr");
		map.put(7,"Commision_rate");
		map.put(8,"Payment_method");
	}
	private static int changes() {
		changedValue = "";
		System.out.println("Select what you want to update:");
		System.out.println("1.Name");
		System.out.println("2.Contact number");
		System.out.println("3.Employee type");
		System.out.println("4.Union charge");
		System.out.println("5.Salary (monthly)");
		System.out.println("6.Salary (perh)");
		System.out.println("7.Commision rate");
		System.out.println("8.Payment method");
		Integer ret = in.nextInt();
		in.nextLine();
		changedValue = in.nextLine();
		return ret;
	}
	public static void main(String args[]) {
		System.out.println("Enter the ID of the Employee you want to edit:");
		String edit_ID = in.nextLine();
		GenerateMap();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();

			Integer change = changes();

			String temp="";
			if(change == 1 || change == 2)temp = "UPDATE Employee SET " + map.get(change)+ "=" 
						+ "'" + changedValue + "'" + " WHERE ID=" + "'"+edit_ID+"'";
			else temp = "UPDATE Employee SET " + map.get(change)+ "=" 
						+ changedValue+ " WHERE ID=" + "'"+edit_ID+"'";
			stmt.executeUpdate(temp);
			System.out.println("Updated Succesfully!");


			// while(change != 9) {
			// 	if(change < 0 || change > 9) {
			// 		System.out.println("INVALID INPUT, TRY AGAIN!");
			// 		change = changes();
			// 		continue;
			// 	}
			// 	else {
			// 		String temp="";
			// 		if(change == 1 || change == 2)temp = "UPDATE Employee SET " + map.get(change)+ "=" 
			// 					+ "'" + changedValue + "'" + " WHERE ID=" + "'"+edit_ID+"'";
			// 		else temp = "UPDATE Employee SET " + map.get(change)+ "=" 
			// 					+ changedValue+ " WHERE ID=" + "'"+edit_ID+"'";
			// 		stmt.executeUpdate(temp);
			// 		System.out.println("Updated Succesfully!");
			// 		change = changes();
			// 	}
			// }
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}