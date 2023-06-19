package exchangeGoods.storage;

import exchangeGoods.model.Store;
import java.util.HashMap;
import java.util.Optional;

/*
Store 저장소 역할을 하는 클래스
storage를 핸들링할 클래스 입니다.
 */
public final class StoreStorage implements Storage<Store, String>{

  private static final HashMap<String, Store> storage = new HashMap<>();

  @Override
  public Optional<Store> findByCode(String storeCode) {
    return Optional.ofNullable(storage.getOrDefault(storeCode, null));
  }

  @Override
  public void save(Store store) {
    storage.put(store.getStoreCode(), store);
  }
}
