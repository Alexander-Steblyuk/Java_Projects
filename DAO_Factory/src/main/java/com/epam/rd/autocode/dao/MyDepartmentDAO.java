package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import lombok.extern.java.Log;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class MyDepartmentDAO implements DepartmentDao {
    private static final String SELECT_BY_ID = "select * from department where id = ?";
    private static final String SELECT_ALL = "select * from department";
    private static final String INSERT = "insert into department(id, name, location) values(?, ?, ?)";
    private static final String DELETE = "delete from department where id = ?";

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String LOCATION_COLUMN_NAME = "location";

    private static final int ID_PARAM = 1;
    private static final int NAME_PARAM = 2;
    private static final int LOCATION_PARAM = 3;



    @Override
    public Optional<Department> getById(BigInteger id) {
        Department department = null;

        try (final Connection connection = ConnectionSource.instance().createConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(ID_PARAM, id.intValue());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    department = getDepartment(resultSet);
                }
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return Optional.ofNullable(department);
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection connection = ConnectionSource.instance().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                departments.add(getDepartment(resultSet));
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return departments;
    }

    @Override
    public Department save(Department department) {
        Department insertedDepartment = null;

        try (final Connection connection = ConnectionSource.instance().createConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setInt(ID_PARAM, department.getId().intValue());
            preparedStatement.setString(NAME_PARAM, department.getName());
            preparedStatement.setString(LOCATION_PARAM, department.getLocation());

            if (getById(department.getId()).isPresent()) {
                delete(department);
            }

            int countOfRows = preparedStatement.executeUpdate();

            if (countOfRows > 0) {
                insertedDepartment = department;
            }

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }

        return insertedDepartment;
    }

    @Override
    public void delete(Department department) {
        try (final Connection connection = ConnectionSource.instance().createConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(ID_PARAM, department.getId().intValue());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    private Department getDepartment(ResultSet rs) throws SQLException {
        BigInteger id = rs.getBigDecimal(ID_COLUMN_NAME).toBigInteger();
        String name = rs.getString(NAME_COLUMN_NAME);
        String location = rs.getString(LOCATION_COLUMN_NAME);

        return new Department(id, name, location);
    }
}
