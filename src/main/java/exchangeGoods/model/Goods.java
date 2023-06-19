package exchangeGoods.model;

import lombok.Getter;

@Getter
public class Goods {
  private String code;
  private ExchangeStatus status;

  public Goods(String code, ExchangeStatus status) {
    this.code = code;
    this.status = status;
  }

  public void exchangeGoods(){
    status = ExchangeStatus.EXCHANGED;
  }
}
