package exchangeGoods.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Store {
  private String storeCode;
  private List<String> usedGoodsCodes;

  public Store(String storeCode) {
    this.storeCode = storeCode;
    this.usedGoodsCodes = new ArrayList<>();
  }

  public void addUsedGoodsCode(String goodsCode) {
    usedGoodsCodes.add(goodsCode);
  }
}
