package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args){
        SellerDao sellerDao = DaoFactory.createSellerDao();//voce esconde aonde esta a implementação, e so mostra a interface, injeção de depedência
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

    }
}
