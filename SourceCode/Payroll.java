package payrollDesign;
import java.util.Scanner;
import java.sql.*;
import java.util.Calendar;

public class Payroll{
	private static void Process_payments(Payment emp,String ID) {
		java.util.Date date=new java.util.Date();
		Calendar cal = Calendar.getInstance();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String temp = "select * FROM Employee WHERE ID=" + "'"+ID+"'";
			ResultSet rs = stmt.executeQuery(temp);
			if(rs.next()) {
				double amt = rs.getDouble("Amount_to_be_paid");
				int emp_type = rs.getInt("Employee_type");
				int pay_mode = rs.getInt("Payment_method");
				if(dayOfMonth == 1) amt -= emp.monthly_charge();
				if(true || (emp_type == 1 && date.getDay() == 5) || (emp_type == 2 && dayOfMonth == 1)){
					System.out.println("You are paid an amount of:" + amt + "by method" + pay_mode);
					String q1 = "select * FROM last_paid WHERE ID=" + "'"+ID+"'";
					ResultSet rs1 = stmt.executeQuery(q1);
					if(rs1.next()) {
						stmt.executeUpdate("UPDATE last_paid SET Date='" + sqlDate + "' WHERE ID=" +"'"+ID+"'");
					}
					else {
						stmt.executeUpdate("INSERT INTO last_paid VALUES " + "('" + 
							ID+"'," + "'"+ sqlDate + "')");
					}
					stmt.executeUpdate("UPDATE Employee SET Amount_to_be_paid=0 WHERE ID=" +"'"+ID+"'");
					amt = 0;
				}
				System.out.print("The company has to pay : Rs. " + amt + " last payment was :");
				ResultSet rs2 = stmt.executeQuery("select * FROM last_paid WHERE ID=" + "'"+ID+"'");
				if(rs2.next()) {
					java.sql.Date DATE_paid = rs2.getDate("Date");
					java.util.Date DATE_java = new java.util.Date(DATE_paid.getDate());
					System.out.println(DATE_java);
				}
			}
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
	private static void update_payments(Payment emp , String ID) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String temp1 = "select * FROM Employee WHERE ID=" + "'"+ID+"'";
			ResultSet rs1 = stmt.executeQuery(temp1);
			if(rs1.next()) {
				double old_amt = rs1.getDouble("Amount_to_be_paid");
				double new_amt = old_amt + emp.get_todays_pay();
				String temp2 = "UPDATE Employee SET Amount_to_be_paid = "+
				 new_amt + " WHERE ID=" + "'"+ID+"'";
				stmt.executeUpdate(temp2);
			}
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the Employee ID:");
		String payroll_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String query = "select * FROM Employee WHERE ID=" + "'"+payroll_ID+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				Payment emp;
				double m_sal = rs.getDouble("Salary_monthly");
				double h_sal = rs.getDouble("Salary_perhr");
				double chrge = rs.getDouble("Union_charge");
				int emp_type = rs.getInt("Employee_type");
				if(emp_type == 1) emp = new EmployeeTypeA(payroll_ID,m_sal,h_sal,chrge);
				else emp = new EmployeeTypeB(payroll_ID,m_sal,h_sal,chrge);

				update_payments(emp,payroll_ID);
				Process_payments(emp,payroll_ID);
			}
			else System.out.println("Not an Employee!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}

interface Payment {
	double get_todays_pay();
	double monthly_charge();
}

class PaymentInfo {
	private double monthlysalary;
	private double hourlysalary;
	private double charge;
	PaymentInfo(double monthlysalary,double hourlysalary,double charge) {
		this.monthlysalary = monthlysalary;
		this.hourlysalary = hourlysalary;
		this.charge = charge;
	}
	protected double cut_monthly_charges() {
		return this.charge;
	}
	protected double getmonthlysalary() {
		return this.monthlysalary;
	}
	protected double gethourlysalary() {
		return this.hourlysalary;
	}
}

class EmployeeTypeA extends PaymentInfo implements Payment{
	private String ID;
	EmployeeTypeA(String ID,double monthlysalary,double hourlysalary,double charge) {
		super(monthlysalary,hourlysalary,charge);
		this.ID = ID;
	}
	public double get_todays_pay() {
		double todays_amount = 0;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");

			java.util.Date date=new java.util.Date();
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());

			Statement stmt=con.createStatement();
			String query = "select * FROM in_out_record WHERE ID=" + "'"+ID+
				"' AND Date='" + sqlDate + "'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				double time_worked = rs.getDouble("Tot_time");
				todays_amount = gethourlysalary()*time_worked;
				if(time_worked > 8) todays_amount  *= 1.5;
			}
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
		return todays_amount;
	} 
	public double monthly_charge() {
		return super.cut_monthly_charges();
	}
}

class EmployeeTypeB extends PaymentInfo implements Payment{
	private String ID;
	EmployeeTypeB(String ID,double monthlysalary,double hourlysalary,double charge) {
		super(monthlysalary,hourlysalary,charge);
		this.ID = ID;
	}
	public double get_todays_pay() {
		return getmonthlysalary()/30;
	}
	public double monthly_charge() {
		return super.cut_monthly_charges();
	}
}