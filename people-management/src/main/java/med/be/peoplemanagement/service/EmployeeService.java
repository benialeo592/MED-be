package med.be.peoplemanagement.service;

import lombok.RequiredArgsConstructor;
import med.be.peoplemanagement.command.CommandFactory;
import med.be.peoplemanagement.entity.EmployeeEntity;
import med.be.peoplemanagement.mapper.EmployeeMapper;
import med.be.peoplemanagement.repository.EmployeeJpaConnector;
import org.openapitools.model.Employee;
import org.openapitools.model.EmployeeFilters;
import org.openapitools.model.Employees;
import org.openapitools.model.SuccessMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CommandFactory<EmployeeEntity> commandFactory;
    private final EmployeeJpaConnector employeeJpaConnector;
    private final EmployeeMapper employeeMapper;

    @Transactional(readOnly = true)
    public Employee getById(long id){
            EmployeeEntity foundEntity = commandFactory
                .create(commandFactory.GET_BY_ID, id, employeeJpaConnector )
                .execute();
            return employeeMapper.map(foundEntity);
    }

    @Transactional
    public SuccessMessage createEntity(Employee employee){
            EmployeeEntity createdEntity = commandFactory
                .create(commandFactory.POST_ENTITY, employeeMapper.map(employee), employeeJpaConnector)
                .execute();
            return new SuccessMessage()
                    .message(createdEntity.getId() + " CREATED");
    }
    @Transactional
    public SuccessMessage deleteEntity(long id){
        EmployeeEntity foundEntity = commandFactory
                .create(commandFactory.GET_BY_ID, id, employeeJpaConnector )
                .execute();
        EmployeeEntity removedEntity = commandFactory
                .create(commandFactory.DELETE_ENTITY, foundEntity, employeeJpaConnector)
                .execute();
        return new SuccessMessage().message(removedEntity.getId() + " REMOVED");
    }

    @Transactional(readOnly = true)
    public Employees getByFilters(EmployeeFilters filters){
        List<EmployeeEntity> employees = commandFactory
                .create(commandFactory.FILTER_ENTITY, filters.getItems(), employeeJpaConnector)
                .execute();
        return new Employees()
                .items(employees.stream()
                    .map(employeeMapper::map)
                    .toList());
    }

}
