package exchangeGoods.exception;

/**
 * 유호하지 않은 상품코드를 처리하는 떄 던집니다.
 */
public class InvalidGoodsCodeException extends RuntimeException{

  private static final String MESSAGE = "%s: 유효하지 않은 상품코드입니다.";
  public InvalidGoodsCodeException(String goodsCode) {
    super(String.format(MESSAGE, goodsCode));
  }
}
