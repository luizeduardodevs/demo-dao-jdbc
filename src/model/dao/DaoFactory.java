package model.dao;

import db.DB;
import model.dao.impl.DepartamentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {//classe auxiliar responsalvel por instaciar os DAO, atraves de operações statics
    public static SellerDao createSellerDao(){//retorna uma interface
        return new SellerDaoJDBC(DB.getConnection());//instacia uma implementação, nao expoem a implementação somente a interface.
            //esse DB.getConnection só é pedido depois de implementar dentro da classe sellerdaoJDBC
            //passa a conexão do banco de dados como argumento
    }
    public static DepartamentDao createDepartmentDao(){
        return new DepartamentDaoJDBC(DB.getConnection());
    }
}
