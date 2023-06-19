package exchangeGoods;

import exchangeGoods.exception.InvalidInputException;
import java.util.regex.Pattern;

/**
 * 서비스의 입력을 비지니스 로직을 실행전에 검증하는 Validator입니다.
 * 검증을 통해 붎필요한 데이터가 비지니스 로직에 들어가지 않도록 합니다.
 */
public class ExchangeGoodsInputValidator {

  // 숫자 문자열 9자리 검증을 위한 패턴
  private static final Pattern GOODS_CODE_PATTERN = Pattern.compile("\\d{9}");
  // a-z A-Z 6자리 문자열 검증을 위한 패턴
  private static final Pattern STORE_CODE_PATTERN = Pattern.compile("[a-zA-Z]{6}");

  /**
   * 검증을 하는 메인 메서드
   * @param input
   * @return 검증이 통과하면 true를 반환
   * @throws InvalidInputException
   *         아무런 입력이 없을시,
   *         입력이 30글자를 초과했을시,
   *         잘못된 명령어로 요청했을시
   */
  public boolean validateInput(String input) {
    if (input == null || input.isBlank()) {
      throw new InvalidInputException("아무런 입력이 없습니다. 원하는 요청을 입력해주세요");
    }

    if (input.length() > 30) {
      throw new InvalidInputException("요청 입력은 최대 30글자 입니다.");
    }

    // input을 양쪽의 공백을 제거해 2 부분으로 분리 ex) check 1010 10101 -> [check, 1010 10101]
    String[] parts = input.trim().split("\\s+", 2);

    String command = parts[0].toUpperCase();

    return switch (command) {
      case "CHECK" -> validateCheckCommand(parts);
      case "HELP" -> validateHelpCommand();
      case "CLAIM" -> validateClaimCommand(parts);
      case "EXIT" -> true;
      default -> {
        throw new InvalidInputException("잘못된 요청입니다. HELP 요청으로 사용법을 확인해주세요");
      }
    };
  }

  /**
   * CHECK 입력의 검증 메서드.
   * @param parts
   * @return  검증이 통과하면 true를 반환
   * @throws  InvalidInputException
   *          상품코드 입력이 없을시,
   *          한번에 여러개의 코드를 입력했을시,
   *          잘못된 형식의 상품코드를 입력했을시
   */
  private boolean validateCheckCommand(String[] parts) {
    if (parts.length == 1) {
      throw new InvalidInputException("상품코드 입력이 없습니다. CHECK 요청은 CHECK [상점코드] 형식으로 입력되어야 합니다.");
    }

    String goodsCodePart = parts[1];
    if (goodsCodePart.split(" ").length > 1) {
      throw new InvalidInputException("한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
    }
    if (!GOODS_CODE_PATTERN.matcher(goodsCodePart).matches()) {
      throw new InvalidInputException("잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
    }
    return true;
  }

  /**
   * HELP 입력의 검증 메서드
   * @return 검증이 통과하면 true를 반환
   */
  private boolean validateHelpCommand() {
    return true;
  }

  /**
   * CLAIM 입력의 검증 메서드
   * @param parts
   * @return  검증이 통과하면 true를 반환
   * @throws InvalidInputException
   *         요청 코드 입력이 없을시,
   *         한번에 여러가지의 상품코드를 입력했을시,
   *         잘못된 형식의 상점코드를 입력했을시,
   *         잘못된 형식의 상품코드를 입력했을시
   */
  private boolean validateClaimCommand(String[] parts) {
    if (parts.length == 1) {
      throw new InvalidInputException(
          "상점코드, 상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다");
    }

    String[] claimParts = parts[1].split("\\s+");

    if (claimParts.length == 1) {
      throw new InvalidInputException(
          "상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다");
    }
    if (claimParts.length > 2) {
      throw new InvalidInputException("한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
    }
    if (!STORE_CODE_PATTERN.matcher(claimParts[0]).matches()) {
      throw new InvalidInputException("잘못된 상점코드 입니다. a-z, A-z로만 구성된 6자리의 상점코드를 입력해주세요");
    }
    if (!GOODS_CODE_PATTERN.matcher(claimParts[1]).matches()) {
      throw new InvalidInputException("잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");

    }

    return true;
  }
}
