package peopleDB.UtilityMethods;

import peopleDB.model.Employee;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UtilMethods {


    public static ZonedDateTime getZonedDateTime(ResultSet rs) throws SQLException {
        return ZonedDateTime.of(rs.getTimestamp("DOB").toLocalDateTime(), ZoneId.of("+0"));
    }
    public static Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        long employeeID = rs.getLong("ID");
        String firstName = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");
        ZonedDateTime dob = getZonedDateTime(rs);
        BigDecimal salary = rs.getBigDecimal("SALARY");
        return new Employee(employeeID,firstName,lastName,dob,salary);
    }

    public static Timestamp getDOB(ZonedDateTime employeeDOB) {
        return Timestamp.valueOf(employeeDOB.withZoneSameInstant(ZoneId.of("+0")).toLocalDateTime());
    }
}
