package ucr.edu.ir.webApp.dao;

public interface PersonDao {
    int insertPerson(UUID id, Person person);

    default int addPerson(Person person){
        UUID id = UUID.randomUUID();
        return insertPerson(id, person)
    }
}
