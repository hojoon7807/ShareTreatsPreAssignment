package exchangeGoods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import exchangeGoods.model.ExchangeCommandType;
import exchangeGoods.service.Command;
import exchangeGoods.service.CommandRequestDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ExchangeGoodsCommandManager 단위 테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ExchangeGoodsCommandManagerTest {

  ExchangeGoodsCommandManager manager;

  @Nested
  class getResult_메서드는 {

    @Nested
    class CHECK_요청_DTO가_주어지면 {

      Command mockCommand;
      final String CHECK_EXECUTE = "CHECK";

      @BeforeEach
      void checkSetUp() {
        Map<ExchangeCommandType, Command> mockFactory = new HashMap<>();
        mockCommand = mock(Command.class);
        mockFactory.put(ExchangeCommandType.CHECK, mockCommand);
        manager = new ExchangeGoodsCommandManager(mockFactory);
      }

      @Test
      void Command의_실행결과_문자열을_반환한다() {
        CommandRequestDto commandRequestDto = createCommandRequestDto(ExchangeCommandType.CHECK,
            "");
        when(mockCommand.execute(commandRequestDto.request())).thenReturn(CHECK_EXECUTE);
        assertThat(manager.getResult(commandRequestDto)).isEqualTo(CHECK_EXECUTE);
      }
    }

    @Nested
    class CLAIM_요청_DTO가_주어지면 {

      Command mockCommand;
      final String CLAIM_EXECUTE = "CLAIM";

      @BeforeEach
      void claimSetUp() {
        Map<ExchangeCommandType, Command> mockFactory = new HashMap<>();
        mockCommand = mock(Command.class);
        mockFactory.put(ExchangeCommandType.CLAIM, mockCommand);
        manager = new ExchangeGoodsCommandManager(mockFactory);
      }

      @Test
      void Command의_실행결과_문자열을_반환한다() {
        CommandRequestDto commandRequestDto = createCommandRequestDto(ExchangeCommandType.CLAIM,
            "");
        when(mockCommand.execute(commandRequestDto.request())).thenReturn(CLAIM_EXECUTE);
        assertThat(manager.getResult(commandRequestDto)).isEqualTo(CLAIM_EXECUTE);
      }
    }

    @Nested
    class HELP_요청_DTO가_주어지면 {

      Command mockCommand;
      final String HELP_EXECUTE = "HELP";

      @BeforeEach
      void helpSetUp() {
        Map<ExchangeCommandType, Command> mockFactory = new HashMap<>();
        mockCommand = mock(Command.class);
        mockFactory.put(ExchangeCommandType.HELP, mockCommand);
        manager = new ExchangeGoodsCommandManager(mockFactory);
      }

      @Test
      void Command의_실행결과_문자열을_반환한다() {
        CommandRequestDto commandRequestDto = createCommandRequestDto(ExchangeCommandType.HELP,
            "");
        when(mockCommand.execute(commandRequestDto.request())).thenReturn(HELP_EXECUTE);
        assertThat(manager.getResult(commandRequestDto)).isEqualTo(HELP_EXECUTE);
      }
    }
  }

  CommandRequestDto createCommandRequestDto(ExchangeCommandType type, String input) {
    return new CommandRequestDto(type, input);
  }

}