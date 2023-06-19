package exchangeGoods.model;

/**
 * 상품의 교환상태를 나타냅니다.
 */
public enum ExchangeStatus {
  VALID("사용가능한 상품코드입니다."),
  EXCHANGED("이미 교환된 상품코드입니다.");

  private final String message;
  ExchangeStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
