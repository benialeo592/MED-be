package med.be.peoplemanagement.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class CreateEntityCommand<T> implements Command {

    private final T entity;
    private final JpaRepository<T, Long> repository;

    @Override
    public T execute() {
        return repository.save(entity);
    }
}
