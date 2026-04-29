package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {//calsse auxiliar responsalvel por instaciar os DAO, atraves de operações statics
    public static SellerDao createSellerDao(){//retorna uma interface
        return new SellerDaoJDBC();//instacia uma implementação, nao expoem a implementação somente a interface.
    }
}
