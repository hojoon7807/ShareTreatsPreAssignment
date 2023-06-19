package exchangeGoods.storage;

import java.util.Optional;

public interface Storage <T,ID>{
  Optional<T> findByCode(ID code);

  void save(T entity);
}
