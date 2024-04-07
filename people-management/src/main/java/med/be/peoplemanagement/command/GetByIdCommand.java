package med.be.peoplemanagement.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class GetByIdCommand<T> implements Command{

    private final long id;
    private final JpaRepository<T, Long> repository;

    @Override
    public T execute() {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
    }
}
