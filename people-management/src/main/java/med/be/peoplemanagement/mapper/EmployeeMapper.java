package med.be.peoplemanagement.mapper;

import med.be.peoplemanagement.entity.EmployeeEntity;
import med.be.peoplemanagement.entity.RoleEntity;
import org.openapitools.model.Employee;
import org.openapitools.model.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeMapper {

    public EmployeeEntity map(Employee employee){
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fiscalCode(employee.getFiscalCode())
                .email(employee.getEmail())
                .birthDate(LocalDate.parse(employee.getBirthDate()))
                .roleEntity(RoleEntity.builder()
                        .id(employee.getRole().getId())
                        .roleName(employee.getRole().getRoleName())
                        .build())
                .build();
    }

    public Employee map(EmployeeEntity employeeEntity){
        return new Employee()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .fiscalCode(employeeEntity.getFiscalCode())
                .email(employeeEntity.getEmail())
                .birthDate(employeeEntity.getBirthDate().toString())
                .role(new Role().id(employeeEntity.getRoleEntity().getId())
                                .roleName(employeeEntity.getRoleEntity().getRoleName()));
    }
}
