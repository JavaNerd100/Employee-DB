package peopleDB.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import peopleDB.model.Employee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeRepoTests {


    private Connection connection;
    private EmployeeRepository employeeRepo;

    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:~/peopleTest".replace("~", System.getProperty("user.home")));
        connection.setAutoCommit(false);
        employeeRepo = new EmployeeRepository(connection);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void canSaveOneEmployee()  {
        Employee tom = new Employee("Tom","Smith", ZonedDateTime.of(1980,12,15,15,15,0,0, ZoneId.of("+4")));
        Employee savedEmployee= employeeRepo.save(tom);
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void canSaveTwoEmployee(){
        Employee alex = new Employee("Alex","Smith", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4")));
        Employee angela = new Employee("Angela","Yu", ZonedDateTime.of(1900,8,25,11,29,33,15, ZoneId.of("-6")));
        Employee savedEmployee1= employeeRepo.save(alex);
        Employee savedEmployee2= employeeRepo.save(angela);
        assertThat(savedEmployee1.getId()).isNotEqualTo(savedEmployee2.getId());

    }

    @Test
    public void canFindEmployeeById(){
        Employee savedEmployee = employeeRepo.save(new Employee("Test", "Test", ZonedDateTime.now()));
        Employee foundEmployee = employeeRepo.findById(savedEmployee.getId()).get();
        assertThat(foundEmployee).isEqualTo(savedEmployee);
    }

    @Test
    public void cannotFindEmployeeById(){
        Optional<Employee> employee = employeeRepo.findById(-1L);
        assertThat(employee).isEmpty();
    }

    @Test
    public void checkCount(){
        long startCount = employeeRepo.count();
         employeeRepo.save(new Employee("Test", "Test", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4"))));
         employeeRepo.save(new Employee("Test2", "Test", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4"))));
        long endCount = employeeRepo.count();
        assertThat(endCount).isEqualTo(startCount+2);

    }

    @Test
    public void canDelete(){
        Employee employee = new Employee("Test", "Test", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4")));
        employeeRepo.save(employee);
        long startCount = employeeRepo.count();
        employeeRepo.delete(employee);
        long endCount = employeeRepo.count();
        assertThat(endCount).isEqualTo(startCount-1);

    }

    @Test
    public void canDeleteMultiple(){
        Employee employee1 = new Employee("Test1", "Test", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4")));
        Employee employee2 = new Employee("Test2", "Test", ZonedDateTime.of(2000,11,30,15,30,33,0, ZoneId.of("+4")));
        employeeRepo.save(employee1);
        employeeRepo.save(employee2);
        long startCount = employeeRepo.count();
        employeeRepo.delete(employee1,employee2);
        long endCount = employeeRepo.count();
        assertThat(endCount).isEqualTo(startCount - 2);

    }

    @Test
    public void canUpdate(){
        Employee savedEmployee = employeeRepo.save(new Employee("Test1", "Test", ZonedDateTime.of(2000, 11, 30, 15, 30, 33, 0, ZoneId.of("+4"))));
        Employee employee1 = employeeRepo.findById(savedEmployee.getId()).get();
        savedEmployee.setSalary(new BigDecimal("75000.00"));
        employeeRepo.update(savedEmployee);
        Employee employee2 = employeeRepo.findById(savedEmployee.getId()).get();
        assertThat(employee2.getSalary()).isNotEqualTo(employee1.getSalary());
    }
}
