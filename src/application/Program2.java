import model.dao.DaoFactory;
import model.dao.DepartamentDao;
import model.dao.impl.DepartamentDaoJDBC;
import model.entities.Departament;

void main() {
    DepartamentDao departamentDao = DaoFactory.createDepartmentDao();
    System.out.println("==== Test 1 INSERT ====");
    Departament departament = new Departament(77,"Games");
    departamentDao.insert(departament);//processo de inserir mais um Departamento
    System.out.println("done!");

    System.out.println("==== Test 2 FindById ====");
    Departament departament1 = new Departament();
    departament1 = departamentDao.findById(7);
    System.out.println(departament1);


    System.out.println("==== Test 3 UPDATE ====");
    departament1 = departamentDao.findById(7);
    departament1.setName("Jogos");
    departamentDao.update(departament1);
    System.out.println("Completed");

    System.out.println("==== Test 4 Delete ====");
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter o id for delete");
    int id = sc.nextInt();
    departamentDao.deleteById(id);
    System.out.println();
    System.out.println("Delete Completed");

    System.out.println("==== Test 5 FindAll ====");
    List<Departament>list = departamentDao.findAll();
    for(Departament dep: list){
        System.out.println(dep);
    }



}