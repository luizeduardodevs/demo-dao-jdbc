package model.entities;

import java.io.Serializable;
import java.util.Objects;
//implementar serializable, serve para os objetos serem transformados em sequencias de bytes para serem salvos em arquivos e trafegados em redes.
public class Departament implements Serializable {
    private static final long serialVersionUID = 1l;
    private Integer id; //GERAR ATRIBUTOS
    private String name;
    public Departament(){}//GERAR CONSTRUTORES

    public Departament(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {//GERAR GETTERS E SETTERS
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {// GERAR HASHCODE E EQUALS
        if (o == null || getClass() != o.getClass()) return false;
        Departament that = (Departament) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {//GERAR TOSTRING, FACILIDADE DE IMPRIMIR OS VALORES NO TESTES
        return "Departament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
