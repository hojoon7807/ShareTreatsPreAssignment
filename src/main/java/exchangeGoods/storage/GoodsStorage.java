package exchangeGoods.storage;

import exchangeGoods.model.ExchangeStatus;
import exchangeGoods.model.Goods;
import java.util.HashMap;
import java.util.Optional;

/*
Goods 저장소 역할을 하는 클래스
초기 데이터 20개를 초기화하고 storage를 핸들링할 클래스 입니다.
 */
public final class GoodsStorage implements Storage<Goods, String>{

  private final static HashMap<String, Goods> storage;

  static {
    storage = new HashMap<>();
    init();
  }
  private static void init() {
    storage.put("100000000", new Goods("100000000", ExchangeStatus.VALID));
    storage.put("100000001", new Goods("100000001", ExchangeStatus.VALID));
    storage.put("100000002", new Goods("100000002", ExchangeStatus.VALID));
    storage.put("100000003", new Goods("100000003", ExchangeStatus.VALID));
    storage.put("100000004", new Goods("100000004", ExchangeStatus.VALID));
    storage.put("100000005", new Goods("100000005", ExchangeStatus.VALID));
    storage.put("100000006", new Goods("100000006", ExchangeStatus.VALID));
    storage.put("100000007", new Goods("100000007", ExchangeStatus.VALID));
    storage.put("100000008", new Goods("100000008", ExchangeStatus.VALID));
    storage.put("100000009", new Goods("100000009", ExchangeStatus.VALID));
    storage.put("100000010", new Goods("100000010", ExchangeStatus.EXCHANGED));
    storage.put("100000011", new Goods("100000011", ExchangeStatus.EXCHANGED));
    storage.put("100000012", new Goods("100000012", ExchangeStatus.EXCHANGED));
    storage.put("100000013", new Goods("100000013", ExchangeStatus.EXCHANGED));
    storage.put("100000014", new Goods("100000014", ExchangeStatus.EXCHANGED));
    storage.put("100000015", new Goods("100000015", ExchangeStatus.EXCHANGED));
    storage.put("100000016", new Goods("100000016", ExchangeStatus.EXCHANGED));
    storage.put("100000017", new Goods("100000017", ExchangeStatus.EXCHANGED));
    storage.put("100000018", new Goods("100000018", ExchangeStatus.EXCHANGED));
    storage.put("100000019", new Goods("100000019", ExchangeStatus.EXCHANGED));
  }

  @Override
  public void save(Goods entity) {
    storage.put(entity.getCode(), entity);
  }

  @Override
  public Optional<Goods> findByCode(String goodsCode) {
    return Optional.ofNullable(storage.getOrDefault(goodsCode, null));
  }
}
