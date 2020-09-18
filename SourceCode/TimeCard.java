package payrollDesign;
import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;

public class TimeCard {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the Employee ID:");
		String Timecard_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");


			java.util.Date date=new java.util.Date();
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
			java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());


			Statement stmt=con.createStatement();
			String query = "select * FROM Employee WHERE ID=" + "'"+Timecard_ID+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				String query2 = "select * FROM in_out_record WHERE ID=" + "'"+Timecard_ID+
				"' AND Date='" + sqlDate + "'";
				ResultSet rs2 = stmt.executeQuery(query2);

				if(rs2.next()) {
					java.sql.Time in_time = rs2.getTime("In_time");
					java.util.Date in_time_java = new java.util.Date(in_time.getTime());

					long diff = date.getTime() - in_time_java.getTime();
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffMinutes = diff / (60 * 1000) % 60;
					double tot_time=(double)diffMinutes/60 + diffHours;

					PreparedStatement ps=con.prepareStatement("update in_out_record set Out_time=? ,Tot_time ="
						+ tot_time + " where ID="
						+ "'" + Timecard_ID + "'" + "and Date=?");
					ps.setTimestamp(1,sqlTime);
					ps.setDate(2,sqlDate);
					ps.executeUpdate();
					System.out.println("Succesfully logged out!");
				}
				else {
					PreparedStatement ps=con.prepareStatement("insert into in_out_record (ID,Date,In_time) values("
						+ "'" + Timecard_ID + "'"+",?,?)");
					ps.setDate(1,sqlDate);
					ps.setTimestamp(2,sqlTime);
					ps.executeUpdate();
					System.out.println("Succesfully logged in!");
				}
			}
			else System.out.println("Not an Employee!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}