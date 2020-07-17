package com.deptempspringmvcjdbcapp.model;

import org.springframework.stereotype.Component;

@Component
public class Employee {
	private int empId;
	private String empName;
	private String dateOfBirth;
	private String mailId;
	private long mobileNo;
	private float salary;
	private int deptEmpId;
	private String companyName;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getDeptEmpId() {
		return deptEmpId;
	}
	public void setDeptEmpId(int deptEmpId) {
		this.deptEmpId = deptEmpId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public Employee() {
		super();
	}
	
	public Employee(int empId, String empName, String dateOfBirth, String mailId,int deptEmpId, long mobileNo, float salary,
			 String companyName) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.dateOfBirth = dateOfBirth;
		this.mailId = mailId;
		this.mobileNo = mobileNo;
		this.salary = salary;
		this.deptEmpId = deptEmpId;
		this.companyName = companyName;
	}
	
	

}
