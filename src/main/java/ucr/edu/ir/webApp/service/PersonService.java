package ucr.edu.ir.webApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ucr.edu.ir.webApp.dao.PersonDao;
import ucr.edu.ir.webApp.model.Person;

import java.util.List;

@Service
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("fakeDao") PersonDao personDao) {
        this.personDao = personDao;
    }
}
