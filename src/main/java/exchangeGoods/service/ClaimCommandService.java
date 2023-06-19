package exchangeGoods.service;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.model.ExchangeStatus;
import exchangeGoods.model.Goods;
import exchangeGoods.model.Store;
import exchangeGoods.storage.GoodsStorage;
import exchangeGoods.storage.Storage;
import exchangeGoods.storage.StoreStorage;

/**
 * 비지니스 로직을 실행할 ClaimCommandService 클래스
 * 커맨드 리시버 역할을 합니다
 */
public class ClaimCommandService {
  private static final String CLAIM_SUCCESS_MESSAGE = "상품코드: %s 가 상점코드: %s 에서 사용되었습니다.";
  private final Storage<Store, String> storeStorage;
  private final Storage<Goods, String> goodsStorage;

  public ClaimCommandService(Storage storeStorage, Storage goodsStorage) {
    this.storeStorage = storeStorage;
    this.goodsStorage = goodsStorage;
  }

  /**
   * 상품교환을 수행하는 메인 메서드
   * @param claimServiceDto
   * @return 상품교환 결과 메세지를 반환합니다
   */
  public String claimGoods(ClaimServiceDto claimServiceDto){
    String storeCode = claimServiceDto.storeCode();
    String goodsCode = claimServiceDto.goodsCode();

    Goods foundGoods = findGoods(goodsCode);

    if(isAlreadyExchanged(foundGoods)) {
      return foundGoods.getStatus().getMessage();
    };

    foundGoods.exchangeGoods();

    Store store = findStore(storeCode);
    store.addUsedGoodsCode(foundGoods.getCode());

    saveStore(store);

    return makeMessage(storeCode, goodsCode);
  }

  private void saveStore(Store store) {
    storeStorage.save(store);
  }

  private Goods findGoods(String goodsCode) {
    return goodsStorage.findByCode(goodsCode)
        .orElseThrow(() -> new InvalidGoodsCodeException(goodsCode));
  }

  private boolean isAlreadyExchanged(Goods foundGoods) {
    return foundGoods.getStatus().equals(ExchangeStatus.EXCHANGED);
  }

  private Store findStore(String storeCode) {
    return storeStorage.findByCode(storeCode)
        .orElseGet(() -> new Store(storeCode));
  }

  private String makeMessage(String storeCode, String goodsCode) {
    return String.format(CLAIM_SUCCESS_MESSAGE, goodsCode, storeCode);
  }
}
