package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    //conexão com o banco de dados
    private Connection conn; //assim ele fica a disposção em qualquer lugar dessa classe
    public SellerDaoJDBC(Connection conn){//injenção de dependencia
        this.conn=conn;
    }


    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st =null;
        ResultSet rs = null; // se esse resultset estiver sendo apontado para posição zero, ele não conter objeto
        try {
            //retornando um vendedor por ID
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE seller.id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();//o resultado vai cair dentro do resultSet
            if(rs.next()){// esse rs.next e para testar se veio algum resultado, se caso a consulta rs acima retornou algum valor.
                Departament dep = new Departament();
                dep.setId(rs.getInt("DepartmentId"));//acessa a coluna que esta dentro da tabela passando o nome da coluna
                dep.setName(rs.getString("DepName"));
                Seller obj = new Seller();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                obj.setEmail(rs.getString("Email"));
                obj.setBaseSalary(rs.getDouble("BaseSalary"));
                obj.setBirthDate(rs.getDate("BirthDate"));
                obj.setDepartament(dep);// a associação e feita por objetos, no caso o objetp criado atraves da classe Departament, que foi instanciado em cima.
                //dentro da classe SELLER tem a injenção de departamento.
                return obj;//retorna o objeto seller
            }
            return null;//se não retorna nada, vai cair como nulo
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
