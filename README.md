# FKPayrollDesign

## Setup Your Database

First login to mysql with username as "root" and password "".
Enter the following command
```bash
mysql -u root -p
```
Then create a new database named FKpayroll.
```bash
mysql> CREATE DATABASE FKpayroll
```
After then make the following tables - 
1. Employee
2. In/out record
3. Last payment
```bash
USE FKpayroll;

CREATE TABLE Employee (
	Name VARCHAR(100),
	ID VARCHAR(200),
	Contact_number VARCHAR(10),
	Employee_type INT,
	Union_charge DOUBLE,
	Salary_monthly DOUBLE,
	Salary_perhr DOUBLE,
	Commision_rate DOUBLE,
	Payment_method INT,
    Amount_to_be_paid DOUBLE
);

CREATE TABLE in_out_record(
    ID VARCHAR(200),
    Date DATE,
    In_time TIME,
    Out_time TIME,
    Tot_time DOUBLE
    );

CREATE TABLE last_paid(
    ID VARCHAR(200),
    Date DATE
);

```

## Setup Your JDBC-
Download mysql-connector-java-8.0.20.jar and put in the same folder as your binary files.

## Usage
1. To add a new employee : Run - AddNewEmployee.java
2. To delete an employee : Run - DeleteEmployee.java
3. To edit employee information : Run - EditEmployee.java
4. To Post sales receipt : Run - PostSalesReceipt.java
5. To post union charges/other monthly charges : Run - PostCharges.java
6. To notify employee's entry/exit : Run - TimeCard.java
7. To update your payroll information : Run - Payroll.java
