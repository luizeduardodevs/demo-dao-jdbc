package db;

public class DbException extends RuntimeException{//foi feita derivado da runtime
    private static final long serialVersionUID = 1L;

    public DbException(String msg){
        super(msg);
    }
}
