package model.dao;

import model.entities.Departament;

import java.util.List;

public interface DepartamentDao {
    void insert(Departament obj);//insert receber um departamento,vai inserir no banco de dados o objeto que for enviado como parametro
    void update(Departament obj);
    void deleteById(Integer id);
    Departament findById(Integer id);//retorna um departamento,vai receber um id e fazer uma consulta no banco de dados com esse id
    List<Departament> findAll();//retorna todos
}
