package exchangeGoods;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.exception.InvalidInputException;
import exchangeGoods.service.CommandRequestDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExchangeGoodsControllerTest {

  @InjectMocks
  ExchangeGoodsController exchangeGoodsController;

  @Mock
  ExchangeGoodsCommandManager exchangeGoodsCommandManager;

  @Mock
  ExchangeGoodsInputValidator validator;

  @Nested
  class run_메서드는 {

    @Nested
    class 입력이_유효하지_않으면 {

      final String INVALID_INPUT = "INVALID";

      @Test
      void InvalidInputException_예외가_발생하고_Command가_실행되지_않는다() {
        when(validator.validateInput(INVALID_INPUT)).thenThrow(new InvalidInputException());
        assertAll(
            () -> assertThatThrownBy(() -> exchangeGoodsController.run(INVALID_INPUT)).isInstanceOf(
                InvalidInputException.class),
            () -> verify(exchangeGoodsCommandManager, never()).getResult(any())

        );
      }
    }

    @Nested
    class 입력이_유효하고_상점코드가_존재하지_않으면 {

      final String INVALID_INPUT = "CHECK 123123123";

      @Test
      void InvalidGoodsCodeException_예외가_발생한다() {
        when(validator.validateInput(INVALID_INPUT)).thenReturn(true);
        when(exchangeGoodsCommandManager.getResult(any(CommandRequestDto.class)))
            .thenThrow(new InvalidGoodsCodeException("CODE"));
        assertAll(
            () -> assertThatThrownBy(() -> exchangeGoodsController.run(INVALID_INPUT)).isInstanceOf(
                InvalidGoodsCodeException.class),
            () -> verify(exchangeGoodsCommandManager, times(1)).getResult(any())
        );
      }
    }

    @Nested
    class 입력이_유효하고_상점코드가_존재하면 {

      final String VALID_INPUT = "CHECK 123123123";
      final String MESSAGE = "MESSAGE";

      @Test
      void 결과메시지를_반환한다() {
        when(validator.validateInput(VALID_INPUT)).thenReturn(true);
        when(exchangeGoodsCommandManager.getResult(any(CommandRequestDto.class)))
            .thenReturn(MESSAGE);
        assertAll(
            () -> assertThat(exchangeGoodsController.run(VALID_INPUT)).isEqualTo(MESSAGE),
            () -> verify(exchangeGoodsCommandManager, times(1)).getResult(any()),
            () -> verify(validator, times(1)).validateInput(any())
        );
      }
    }
  }

}