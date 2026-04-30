package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null; // um objeto que é do tipo connection que é do tipo JDBC, tem que ser do mysql;

    public static Connection getConnection(){
        if(conn == null){
            try{
            Properties props = loadProperties();//pegou as propriedades de dentro do db.properties
            String url = props.getProperty("dburl");// passa o nome dburl que foi definido no arquivo db.properties
            conn = DriverManager.getConnection(url,props); //Para ter a conexão com o banco de dados, essa e conexão com banco de dados
        } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }


    public static void closeConnection(){
        if (conn != null){
            try{
            conn.close();
        } catch (SQLException e) {//sqlexception e obrigado a tratar. por isso foi feita uma classe de exceção
                throw new DbException(e.getMessage());
 //Usa-se DbException porque ela e derivada de RuntimeException, então não se faz necessário colocar toda hoa um try-catch
            }
        }

    }



    private static Properties loadProperties(){//Properties é uma classe do java para ler arquivos de configuraçoes, no caso que esta dentro da "db.properties"
        try(FileInputStream fs = new FileInputStream("db.properties")){// abre o arquivo para leitura
            Properties props = new Properties();//cria um mapa de chave e valor
            props.load(fs); //faz a leitura do arquivo fs e guardar dentro do objeto props
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeStatement(Statement st){
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }//tratando as execeções de Statement e ResulSet.
        }
    }
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
