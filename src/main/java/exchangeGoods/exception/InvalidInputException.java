package exchangeGoods.exception;

/**
 * 입력이 유효하지 않을 때 던집니다.
 */
public class InvalidInputException extends RuntimeException{

  public InvalidInputException() {
    super();
  }

  public InvalidInputException(String message) {
    super(message);
  }
}
