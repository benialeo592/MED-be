package med.be.peoplemanagement.command;

import lombok.RequiredArgsConstructor;
import med.be.peoplemanagement.repository.CustomJpaConnector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CommandFactory<T> {

    public final int GET_BY_ID = 0;
    public final int POST_ENTITY = 1;
    public final int DELETE_ENTITY = 2;
    public final int FILTER_ENTITY = 3;

    public Command create(int commandCode, Object...params){

        return switch (commandCode) {
            case GET_BY_ID -> new GetByIdCommand<>((long) params[0], (JpaRepository<T, Long>) params[1]);
            case POST_ENTITY -> new CreateEntityCommand<>((T) params[0], (JpaRepository<T, Long>) params[1]);
            case DELETE_ENTITY -> new DeleteEntityCommand<>((T) params[0], (JpaRepository<T, Long>) params[1]);
            case FILTER_ENTITY -> new FilterEntityCommand<>(convertToGenericObject( (List<T>) params[0]), (CustomJpaConnector<T>) params[1]);
            default -> null;
        };
    }

    private Object[][] convertToGenericObject(List<T> objects){

        if(!objects.isEmpty()){
            T foundObject = objects.stream().findFirst().get();
            Class<?> clazz = foundObject.getClass();
            Field[] fields = clazz.getDeclaredFields();
            final Object[][] converted = new Object[objects.size()][fields.length];

            for(int i = 0; i < objects.size(); i++){
                int k = 0;
                for(Field field : fields){
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(objects.get(i));
                        converted[i][k] =   fieldValue;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    k++;
                }
            }

            return converted;
        }
        return new Object[0][0];
    }

}
