package exchangeGoods.service;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.model.Goods;
import exchangeGoods.storage.GoodsStorage;
import exchangeGoods.storage.Storage;

/**
 * 비지니스 로직을 실행할 CheckCommandService 클래스
 * 커맨드 리시버 역할을 합니다
 */
public class CheckCommandService {
  private final Storage<Goods, String> goodsStorage;

  public CheckCommandService(Storage goodsStorage) {
    this.goodsStorage = goodsStorage;
  }

  public String checkGoods(String goodsCode){
    Goods goods = goodsStorage.findByCode(goodsCode)
        .orElseThrow(() -> new InvalidGoodsCodeException(goodsCode));

    return goods.getStatus().getMessage();
  }
}
