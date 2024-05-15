package peopleDB.model;


import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest  {


    @Test
    public void twoEmployeeEquality(){
        Employee e1 = new Employee("E1", "Test1", ZonedDateTime.of(2000,12,21,12,0,0,0, ZoneId.of("+0")));
        Employee e2 = new Employee("E1", "Test1", ZonedDateTime.of(2000,12,21,12,0,0,0, ZoneId.of("+0")));
        assertThat(e1).isEqualTo(e2);
    }

    @Test
    public void twoEmployeeInEqual(){
        Employee e1 = new Employee("E1", "Test1", ZonedDateTime.of(2000,12,21,12,0,0,0, ZoneId.of("+0")));
        Employee e2 = new Employee("E2", "Test1", ZonedDateTime.of(2000,12,21,12,0,0,0, ZoneId.of("+0")));
        assertThat(e1).isNotEqualTo(e2);
    }

}