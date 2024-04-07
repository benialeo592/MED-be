package med.be.peoplemanagement.repository;

import med.be.peoplemanagement.entity.EmployeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaConnector extends CustomJpaConnector<EmployeeEntity>   {
}
