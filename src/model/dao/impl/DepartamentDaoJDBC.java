package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartamentDao;
import model.entities.Departament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentDaoJDBC implements DepartamentDao {
    // implementar a classe
    private Connection conn;
    public DepartamentDaoJDBC(Connection conn){this.conn=conn;}
    @Override
    public void insert(Departament obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO DEPARTMENT "
                    +"(Id, Name) "
                    +"VALUES "
                    +"(?,?)",
            Statement.RETURN_GENERATED_KEYS);
            st.setInt(1,obj.getId());
            st.setString(2, obj.getName());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected>0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Error inesperado");
            }
        } catch (SQLException e) {
            throw new DbException("Same ID "+ e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Departament obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE DEPARTMENT "
                    +"SET Name = ? "
                    +"WHERE id = ?");
            st.setString(1,obj.getName());
            st.setInt(2,obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?");
            st.setInt(1,id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0){
                System.out.println("Error of Delete");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Departament findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                "SELECT * FROM department WHERE ID = ? ");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Departament obj = new Departament();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Departament> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();
            List<Departament>list = new ArrayList<>();
            while(rs.next()){
                Departament dep = new Departament();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                list.add(dep);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    //incluir um metodo tem que mexer no dao factory
    //criar um program so pra eles
}
