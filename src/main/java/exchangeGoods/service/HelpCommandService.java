package exchangeGoods.service;

public class HelpCommandService {

  private static final String HELP_INFORMATION = """
      ========================================================
      사용방법 안내입니다.
      ========================================================
      CHECK [상품코드]
      HELP
      CLAIM [상점코드] [상품코드]
      EXIT
      ========================================================
      예시
      CHECK 123123123
      HELP
      CLAIM abcABC 123123123
      EXIT
      ========================================================
      상품코드 체크와 교환은 요청마다 1개씩 가능합니다.
      명령어는 대소문자를 구분하지 않습니다. 각 문자 사이의 공백은 구분합니다.
      상점코드는 a-z A-Z로만 이루어진 6자리입니다.
      상품코드는 0-9로만 이루어진 9자리 입니다.
      ========================================================
      """;

  public String getHelpInformation(){
    return HELP_INFORMATION;
  }
}
