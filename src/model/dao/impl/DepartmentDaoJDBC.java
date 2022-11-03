package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement("INSERT INTO department" +
                            "(Name)" +
                            "VALUES" +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS

            );
            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! no rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(
                    "SELECT * FROM department " +
                            "WHERE department.Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                return this.instantiateDepartment(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("SELECT * FROM department");

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while(rs.next()){
                list.add(this.instantiateDepartment(rs));
            }

            return list;

        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }
}
