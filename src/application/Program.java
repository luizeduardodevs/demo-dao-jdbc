package application;

import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args){
        Departament obj = new Departament(1, "books");
        Seller seller = new Seller(21,"bob","bob@",new Date(),3000.00,obj);
        System.out.println(seller);


    }
}
