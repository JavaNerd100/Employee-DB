package peopleDB.model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private ZonedDateTime DOB;
    private BigDecimal salary = new BigDecimal("0");



    public Employee(Long id, String firstName, String lastName, ZonedDateTime DOB,BigDecimal salary) {
        this(id ,firstName,lastName,DOB);
        this.salary=salary;

    }


    public Employee(Long id, String firstName, String lastName, ZonedDateTime DOB) {
      this(firstName,lastName,DOB);
      this.id = id;
    }

    public Employee(String firstName, String lastName, ZonedDateTime DOB) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.DOB=DOB;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public ZonedDateTime getDOB() {
        return DOB;
    }

    public void setDOB(ZonedDateTime DOB) {
        this.DOB = DOB;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DOB=" + DOB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) &&
                Objects.equals(DOB.withZoneSameInstant(ZoneId.of("+0")), employee.DOB.withZoneSameInstant(ZoneId.of("+0")));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, DOB);
    }
}
