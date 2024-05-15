package peopleDB.repository;

import peopleDB.exception.FailedToSave;
import peopleDB.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static peopleDB.UtilityMethods.UtilMethods.getDOB;
import static peopleDB.UtilityMethods.UtilMethods.getEmployeeFromResultSet;

public class EmployeeRepository {

    public static final String SAVE_EMPLOYEE_SQL = "INSERT INTO PEOPLE (FIRST_NAME,LAST_NAME,DOB) VALUES(?, ?, ?)";
    private final String FIND_BY_ID = "SELECT ID , FIRST_NAME , LAST_NAME , DOB, SALARY FROM PEOPLE WHERE ID = ?";
    private Connection connection;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;

    }

    public Employee save(Employee employee) throws FailedToSave{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_EMPLOYEE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setTimestamp(3, getDOB(employee.getDOB()));
            int recordsAffected = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                employee.setId(id);
                System.out.println(employee);
            }
            System.out.printf("Records Affected: %d%n",recordsAffected);
        } catch (SQLException e) {
            throw new FailedToSave("Tried to save an Employee" + employee);
        }

        return employee;
    }



    public Optional<Employee> findById(Long id) {
        Employee employee= null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                employee = getEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(employee);

    }



    public long count() {
        long totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM PEOPLE");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                 totalRecord = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalRecord;
    }

    public void delete(Employee employee) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PEOPLE WHERE ID = ?");
            preparedStatement.setLong(1,employee.getId());
            int affectedRecord = preparedStatement.executeUpdate();
            System.out.println(affectedRecord);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Employee ...employees) {
        try {
            String ids = Arrays.stream(employees).map(Employee::getId).map(String::valueOf).collect(joining(","));
            Statement statement = connection.createStatement();
            int affectedRecordCount = statement.executeUpdate("DELETE FROM PEOPLE WHERE ID IN (:ids)".replace(":ids", ids));
            System.out.println(affectedRecordCount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PEOPLE SET FIRST_NAME = ? , LAST_NAME = ? , DOB = ? , SALARY = ? WHERE ID = ?");
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setTimestamp(3,getDOB(employee.getDOB()));
            preparedStatement.setBigDecimal(4,employee.getSalary());
            preparedStatement.setLong(5,employee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
