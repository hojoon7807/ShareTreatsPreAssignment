package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("HelpCommandService 테스트")
class HelpCommandServiceTest {

  HelpCommandService helpCommandService = new HelpCommandService();

  final String HELP_INFORMATION = """
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

  @Nested
  class getHelpInformation_메서드는 {
    @Test
    void 안내문구를_반환한다 () {
      String helpInformation = helpCommandService.getHelpInformation();

      assertThat(helpInformation).isEqualTo(HELP_INFORMATION);
    }
  }
}