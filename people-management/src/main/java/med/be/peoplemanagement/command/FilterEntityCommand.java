package med.be.peoplemanagement.command;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import med.be.peoplemanagement.repository.CustomJpaConnector;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;


@RequiredArgsConstructor
public class FilterEntityCommand<T> implements Command {

    private final Object[][] filters;
    private final CustomJpaConnector<T> repository;

    @Override
    public T execute() {
        Specification<T> spec = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

             Arrays.stream(filters).forEach(filter->{

                String fieldName = filter[0].toString();
                FilterOperation operation = FilterOperation.valueOf(filter[1].toString());
                Object value = filter[2];

                Predicate filterPredicate = buildPredicate(fieldName, operation, value, root, criteriaBuilder);

                predicates.add(filterPredicate);

            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return (T) repository.findAll(spec);

    }

    private Predicate buildPredicate(String fieldName, FilterOperation operation, Object value, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return switch (operation) {
            case EQUAL -> criteriaBuilder.equal(root.get(fieldName), value);
            case NOT_EQUAL -> criteriaBuilder.notEqual(root.get(fieldName), value);
            case GREATER_THAN -> criteriaBuilder.greaterThan(root.get(fieldName), (Comparable) value);
            case LESS_THAN -> criteriaBuilder.lessThan(root.get(fieldName), (Comparable) value);
            default -> throw new IllegalArgumentException("Unsupported operation: " + operation);
        };
    }
    private enum FilterOperation {
        EQUAL("EQUAL"),
        NOT_EQUAL("NOT_EQUAL"),
        GREATER_THAN("GREATER_THAN"),
        LESS_THAN("LESS_THAN");

        private final String value;

        FilterOperation(String value) {
            this.value = value;
        }

        private String getValue(){
            return this.value;
        }
    }
}
