package dat.daos;

import javax.naming.Context;
import java.util.List;

public interface IDAO<T> {

    T create(T dto);

    List<T> getAll();

    T getById(Long id);

    T update(Long id, T dto);

    void delete(Long id);
}
