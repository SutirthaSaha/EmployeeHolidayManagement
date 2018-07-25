package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;


public class Employee {
    private String name,date,currentBalance,daysCompleted,monthsCompleted,designation;
    private Integer empId;

    public Employee(String name, String date, String currentBalance, String daysCompleted, String monthsCompleted, String designation, Integer empId) {
        this.name = name;
        this.date = date;
        this.currentBalance = currentBalance;
        this.daysCompleted = daysCompleted;
        this.monthsCompleted = monthsCompleted;
        this.designation = designation;
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getDaysCompleted() {
        return daysCompleted;
    }

    public void setDaysCompleted(String daysCompleted) {
        this.daysCompleted = daysCompleted;
    }

    public String getMonthsCompleted() {
        return monthsCompleted;
    }

    public void setMonthsCompleted(String monthsComplted) {
        this.monthsCompleted = monthsComplted;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
}
