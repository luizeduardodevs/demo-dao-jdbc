package model.dao;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);//insert receber um Seller,vai inserir no banco de dados o objeto que for enviado como parametro
    void update(Seller obj);
    void deleteById(Integer id);
    Seller findById(Integer id);//retorna um Seller,vai receber um id e fazer uma consulta no banco de dados com esse id
    List<Seller> findAll();
}
