package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    //conexão com o banco de dados
    private Connection conn; //assim ele fica a disposção em qualquer lugar dessa classe
    public SellerDaoJDBC(Connection conn){//injenção de dependencia
        this.conn=conn;
    }


    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                "INSERT INTO seller "
                +"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                +"VALUES"
                +"(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);//retorna o id do novo vendedor inserido
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));//pra buscar a data. instanciado uma data
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartament().getId());//acessa o departamento e depois pegar o id do departamento
        int rowsAffected = st.executeUpdate();
        if(rowsAffected>0){
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);//PEGA O VALOR DO ID GERADO
                obj.setId(id);//atribuindo o id acima dentro do argumento la do SELLER OBJ
            }
            DB.closeResultSet(rs);//pode fechar dentro dos if pq ele foi criado apenas aqui
        }else {
            throw new DbException("ERRO inesperad nenhuma linha afetada");
        }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller "
                    +"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                    + "WHERE Id = ?");
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));//pra buscar a data. instanciado uma data
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartament().getId());//acessa o departamento e depois pegar o id do departamento
            st.setInt(6,obj.getId());// pegar o id do vendedor
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
                    "DELETE FROM seller WHERE Id = ? ");//caso nao exista o id ele roda normalmente.
            st.setInt(1,id);
            int rows = st.executeUpdate();
            if(rows == 0){//mostra que deu erro e que nao exista nenhum id desse
                throw new DbException("No delected Id ");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
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
            st.setInt(1,id);//busca por id da pessoa.
            rs = st.executeQuery();//o resultado vai cair dentro do resultSet
            if(rs.next()){// esse rs.next e para testar se veio algum resultado, se caso a consulta rs acima retornou algum valor.
                Departament dep = instantiateDepartment(rs);
                Seller obj = instantieSeller(rs,dep);
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

    private Seller instantieSeller(ResultSet rs, Departament dep) throws SQLException {//propagando exceção
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartament(dep);// a associação e feita por objetos, no caso o objetp criado atraves da classe Departament, que foi instanciado em cima.
        return obj;
    }

    private Departament instantiateDepartment(ResultSet rs)throws SQLException {
        Departament dep = new Departament();
        dep.setId(rs.getInt("DepartmentId"));//acessa a coluna que esta dentro da tabela passando o nome da coluna
        dep.setName(rs.getString("DepName"));//necessrio tratar exceção de resultSet,porem ela ja ta sendo tratada no catch
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st =null;
        ResultSet rs = null; // se esse resultset estiver sendo apontado para posição zero, ele não contem objeto
        try {
            //retornando um vendedor por ID
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId = department.Id "
                            +"ORDER BY Name ");
            rs = st.executeQuery();//o resultado vai cair dentro do resultSet
            List<Seller> list = new ArrayList<>();
            //controlado a nao repetição de departamento, pois cada vendedor so tem um departamento.
            Map<Integer,Departament>map =new HashMap<>();
            while (rs.next()){// uso do while pra percorre enquanto tiver um próximo valor
                //testando se ele ja departamento ja existe
                Departament dep =map.get(rs.getInt("DepartmentId"));//aqui retorna os valores guardado dentro do map

                //na primeira vez que comecar a ler vai cair no if, porque o dep ainda esta vazio
                //e nas outras leituras vai cair dentro do map.get;
                if(dep == null){
                    dep = instantiateDepartment(rs);//cria o objeto
                    map.put(rs.getInt("DepartmentId"), dep);//joga denro do map
                }

                Seller obj = instantieSeller(rs,dep);
                //dentro da classe SELLER tem a injenção de departamento.
                list.add(obj);//retorna o objeto seller
            }
            return list;//se não retorna nada, vai cair como nulo
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findByDepartment(Departament departament) {//pesquisa por ID é uma lista
        PreparedStatement st =null;
        ResultSet rs = null; // se esse resultset estiver sendo apontado para posição zero, ele não contem objeto
        try {
            //retornando um vendedor por ID
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE DepartmentId = ? "
                    +"ORDER BY Name ");
                    st.setInt(1,departament.getId()); //è o ID do argumento passado
            rs = st.executeQuery();//o resultado vai cair dentro do resultSet
            List<Seller> list = new ArrayList<>();
            //controlado a nao repetição de departamento, pois cada vendedor so tem um departamento.
            Map<Integer,Departament>map =new HashMap<>();

            while (rs.next()){// uso do while pra percorre enquanto tiver um próximo valor
                //testando se ele ja departamento ja existe
                Departament dep =map.get(rs.getInt("DepartmentId"));//aqui retorna os valores guardado dentro do map

                //na primeira vez que comecar a ler vai cair no if, porque o dep ainda esta vazio
                //e nas outras leituras vai cair dentro do map.get;
                if(dep == null){
                    dep = instantiateDepartment(rs);//cria o objeto
                    map.put(rs.getInt("DepartmentId"), dep);//joga denro do map
                }

                Seller obj = instantieSeller(rs,dep);
                //dentro da classe SELLER tem a injenção de departamento.
                list.add(obj);//retorna o objeto seller
            }
            return list;//se não retorna nada, vai cair como nulo
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}
