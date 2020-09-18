package payrollDesign;
import java.util.Scanner;
import java.sql.*;  

public class AddNewEmployee {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Employee emp = new Employee();
		System.out.println();
		System.out.println("Confirm details, are you sure you want to create this user (y/n):");
		String confirm = in.nextLine();
		if("y".equals(confirm)) {
			System.out.println("Your Flipkart ID is : ");
			System.out.println(emp.GetID());
			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/FKpayroll","root","");
				Statement stmt=con.createStatement();
				String temp = "INSERT INTO Employee VALUES " + "('" + 
				emp.GetName() + "','" + emp.GetID() + "','" + emp.GetContactNumber() + 
				"'," + emp.GetEmployeeType() + "," + emp.GetUnionCharge() + "," +
				emp.GetSalary() + "," + emp.GetSalaryPHr() + "," + emp.GetComissionRate() + 
				"," + emp.GetPaymentType() + "," + "0" + ")";
				stmt.executeUpdate(temp);
				con.close();
			}
			catch(Exception e){ 
				System.out.println(e);
			}
		}
	}
}
class Employee {
	private String companyID;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private int employeeType = 0;
	private int paymentType = 0;
	private double salary = 0;
	private double salaryPHr = 0;
	private double comissionRate = 0;
	private double unionCharge = 0;
	Employee() {
		EnterName();
		Details();
		EmployeeAndPaymentType();
		GenerateCID();
		SalaryDetails();
	}
	Scanner in = new Scanner(System.in);
	private void EnterName() {
		System.out.println("Enter your First Name :");
		this.firstName = in.nextLine();
		System.out.println("Enter your Last Name :");
		this.lastName = in.nextLine();
		System.out.println();
	}
	private void Details() {
		System.out.println("Enter your Contact Number:");
		this.contactNumber = in.nextLine();
		System.out.println();
	}
	private void EmployeeAndPaymentType() {
		System.out.println("Choose your Employee Type:");
		System.out.println("1. Paid by hour");
		System.out.println("2. Paid a flat salary");
		this.employeeType = in.nextInt();
		while(this.employeeType != 1 && this.employeeType != 2) {
			System.out.println("Invalid Choice, Choose again.");
			this.employeeType = in.nextInt();
		}

		System.out.println();
		System.out.println("Choose your Method of Payment:");
		System.out.println("1. Paychecks mailed to the postal address");
		System.out.println("2. Paychecks held for pickup by the paymaster");
		System.out.println("3. Paychecks be directly deposited into the bank account");
		this.paymentType = in.nextInt();
		while(this.paymentType != 1 && this.paymentType != 2 && this.paymentType != 3) {
			System.out.println("Invalid Choice, Choose again.");
			this.employeeType = in.nextInt();
		}
		System.out.println();
	}
	private void SalaryDetails() {
		if(this.employeeType == 1) {
			System.out.println("Enter per hour salary :");
			this.salaryPHr = in.nextDouble();
		}
		else {
			System.out.println("Enter monthly salary :");
			this.salary = in.nextDouble();
		}
		System.out.println("Enter Comission rate :");
		this.comissionRate = in.nextDouble();
		System.out.println("Enter Union Charge :");
		this.unionCharge = in.nextDouble();
	}
	private void GenerateCID() {
		this.companyID = this.firstName + "." + this.lastName + "." + this.contactNumber + "@flipkart.com";
	}
	public String GetID() {
		return this.companyID;
	}
	public String GetName() {
		return this.firstName + " " + this.lastName;
	}
	public String GetContactNumber() {
		return this.contactNumber;
	}
	public double GetSalary() {
		return this.salary;
	}
	public double GetSalaryPHr() {
		return this.salaryPHr;
	}
	public double GetComissionRate() {
		return this.comissionRate;
	}
	public double GetUnionCharge() {
		return this.unionCharge;
	}
	public int GetPaymentType() {
		return this.paymentType;
	}
	public int GetEmployeeType() {
		return this.employeeType;
	}

}