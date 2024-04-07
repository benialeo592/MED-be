package med.be.peoplemanagement.controller;

import lombok.RequiredArgsConstructor;
import med.be.peoplemanagement.service.EmployeeService;
import org.openapitools.api.EmployeeApi;
import org.openapitools.model.Employee;
import org.openapitools.model.EmployeeFilters;
import org.openapitools.model.Employees;
import org.openapitools.model.SuccessMessage;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<Employee> getEmployee(Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @Override
    public ResponseEntity<SuccessMessage> deleteEmployee(Long id) {
        return ResponseEntity.ok(employeeService.deleteEntity(id));
    }
    @Override
    public ResponseEntity<SuccessMessage> postEmployee(Employee employee) {
        return ResponseEntity.ok(employeeService.createEntity(employee));
    }
    @Override
    public ResponseEntity<Employees> getEmployees(EmployeeFilters employeeFilters) {
        return ResponseEntity.ok(employeeService.getByFilters(employeeFilters));
    }
}
