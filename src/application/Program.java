package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args){
        SellerDao sellerDao = DaoFactory.createSellerDao();//voce esconde aonde esta a implementação, e so mostra a interface, injeção de depedência
        System.out.println("=== TEST 1 seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2 seller findById ===");
        Departament departament = new Departament(2,null);
        List<Seller> list = sellerDao.findByDepartment(departament);
        for (Seller oj: list){
            System.out.println(oj);
        }
        System.out.println("\n=== TEST 3 seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj: list){
            System.out.println(obj);
        }
        System.out.println("\n=== TEST 4 seller INSERT ===");
        Seller seller1 = new Seller(null,"Greg","greg@gmail.com", new Date(),4000.00,departament);//aproveitou o objeto departament
        sellerDao.insert(seller1);//passa o parametro da interface
        System.out.println("Inserted! new id: "+ seller1.getId());
    }
}
