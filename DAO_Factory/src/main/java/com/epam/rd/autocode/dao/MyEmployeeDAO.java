package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class MyEmployeeDAO implements EmployeeDao {
    private static final String SELECT_BY_ID = "select * from employee where id = ?";
    private static final String SELECT_BY_MANAGER = "select * from employee where manager = ?";
    private static final String SELECT_BY_DEPARTMENT = "select * from employee where department = ?";
    private static final String SELECT_ALL = "select * from employee";
    private static final String INSERT = "insert into employee values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "delete from employee where id = ?";

    private static final String ID_COLUMN_NAME = "id";
    private static final String FIRST_NAME_COLUMN_NAME = "firstname";
    private static final String LAST_NAME_COLUMN_NAME = "lastname";
    private static final String MIDDLE_NAME_COLUMN_NAME = "middlename";
    private static final String POSITION_COLUMN_NAME = "position";
    private static final String MANAGER_COLUMN_NAME = "manager";
    private static final String HIREDATE_COLUMN_NAME = "hiredate";
    private static final String SALARY_COLUMN_NAME = "salary";
    private static final String DEPARTMENT_COLUMN_NAME = "department";

    private static final int ID_PARAM = 1;
    private static final int FIRSTNAME_PARAM = 2;
    private static final int LASTNAME_PARAM = 3;
    private static final int MIDDLENAME_PARAM = 4;
    private static final int POSITION_PARAM = 5;
    private static final int MANAGER_PARAM = 6;
    private static final int HIREDATE_PARAM = 7;
    private static final int SALARY_PARAM = 8;
    private static final int DEPARTMENT_PARAM = 9;

    @Override
    public Optional<Employee> getById(BigInteger id) {
        Employee employee = null;

        try (final Connection connection = ConnectionSource.instance().createConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(ID_PARAM, id.intValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    employee = getEmployee(resultSet);
                }
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionSource.instance().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(getEmployee(resultSet));
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return employees;
    }

    @Override
    public Employee save(Employee employee) {
        Employee insertedEmployee = null;

        try (final Connection connection = ConnectionSource.instance().createConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setInt(ID_PARAM, employee.getId().intValue());
            preparedStatement.setString(FIRSTNAME_PARAM, employee.getFullName().getFirstName());
            preparedStatement.setString(LASTNAME_PARAM, employee.getFullName().getLastName());
            preparedStatement.setString(MIDDLENAME_PARAM, employee.getFullName().getMiddleName());
            preparedStatement.setString(POSITION_PARAM, employee.getPosition().toString());
            preparedStatement.setInt(MANAGER_PARAM, employee.getManagerId().intValue());
            preparedStatement.setDate(HIREDATE_PARAM, Date.valueOf(employee.getHired()));
            preparedStatement.setDouble(SALARY_PARAM, employee.getSalary().doubleValue());
            preparedStatement.setInt(DEPARTMENT_PARAM, employee.getDepartmentId().intValue());

            if (getById(employee.getId()).isPresent()) {
                delete(employee);
            }

            int countOfRows = preparedStatement.executeUpdate();

            if (countOfRows > 0) {
                insertedEmployee = employee;
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return insertedEmployee;
    }

    @Override
    public void delete(Employee employee) {
        try (final Connection connection = ConnectionSource.instance().createConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(ID_PARAM, employee.getId().intValue());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Employee> getByDepartment(Department department) {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionSource.instance().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_DEPARTMENT)) {

            preparedStatement.setInt(ID_PARAM, department.getId().intValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getEmployee(resultSet));
                }
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return employees;
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionSource.instance().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_MANAGER)) {

            preparedStatement.setInt(ID_PARAM, employee.getId().intValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getEmployee(resultSet));
                }
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return employees;
    }

    private Employee getEmployee(ResultSet rs) throws SQLException {
        BigInteger id = rs.getBigDecimal(ID_COLUMN_NAME).toBigInteger();
        FullName fullName = getFullName(rs.getString(FIRST_NAME_COLUMN_NAME),
                rs.getString(LAST_NAME_COLUMN_NAME),
                rs.getString(MIDDLE_NAME_COLUMN_NAME));
        Position position = Position.valueOf(rs.getString(POSITION_COLUMN_NAME));
        LocalDate hired = rs.getDate(HIREDATE_COLUMN_NAME).toLocalDate();
        BigDecimal salary = rs.getBigDecimal(SALARY_COLUMN_NAME);

        BigDecimal tmpManagerId = rs.getBigDecimal(MANAGER_COLUMN_NAME);
        BigInteger managerId = BigInteger.ZERO;

        if (tmpManagerId != null) {
            managerId = tmpManagerId.toBigInteger();
        }

        BigDecimal tmpDepartmentId = rs.getBigDecimal(DEPARTMENT_COLUMN_NAME);
        BigInteger departmentId = BigInteger.ZERO;

        if (tmpDepartmentId != null) {
            departmentId = tmpDepartmentId.toBigInteger();
        }

        return new Employee(id, fullName, position, hired, salary, managerId, departmentId);
    }

    private FullName getFullName(String firstname, String lastname, String middlename) {
        return new FullName(firstname, lastname, middlename);
    }
}
