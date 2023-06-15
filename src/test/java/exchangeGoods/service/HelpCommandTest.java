package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("HelpCommand 테스트")
class HelpCommandTest {

  @InjectMocks
  HelpCommand helpCommand;

  @Mock
  HelpCommandService helpCommandService;

  @Nested
  class execute_메서드는 {

    final String HELP_INFORMATION = "사용법 안내입니다";

    @Test
    void 안내_문자열을_반환한다(){
      when(helpCommandService.getHelpInformation()).thenReturn(HELP_INFORMATION);

      assertThat(helpCommand.execute("INPUT")).isEqualTo(HELP_INFORMATION);
    }
  }
}