package com.epam.rd.autocode.dao;

public class DaoFactory {
    public EmployeeDao employeeDAO() {
        return new MyEmployeeDAO();
    }

    public DepartmentDao departmentDAO() {
        return new MyDepartmentDAO();
    }
}
