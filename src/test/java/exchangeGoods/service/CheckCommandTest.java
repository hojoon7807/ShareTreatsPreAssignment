package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.model.ExchangeStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CheckCommand 테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CheckCommandTest {

  @InjectMocks
  CheckCommand checkCommand;

  @Mock
  CheckCommandService checkCommandService;

  final String INPUT = "CHECK 123123123";

  @Nested
  class execute_메서드는 {

    @Nested
    class 상품코드가_교환가능하면 {

      @Test
      void VALID의_메세지_문자열을_반환한다() {
        when(checkCommandService.checkGoods(anyString())).thenReturn(
            ExchangeStatus.VALID.getMessage());

        assertThat(checkCommand.execute(INPUT)).isEqualTo(ExchangeStatus.VALID.getMessage());
      }
    }

    @Nested
    class 상품코드가_교환불가능하면 {

      @Test
      void EXCHANGED의_메세지_문자열을_반환한다() {
        String goodsCode = INPUT.split(" ")[1];
        when(checkCommandService.checkGoods(goodsCode)).thenReturn(
            ExchangeStatus.EXCHANGED.getMessage());

        assertThat(checkCommand.execute(INPUT)).isEqualTo(ExchangeStatus.EXCHANGED.getMessage());
      }
    }

    @Nested
    class 상품코드가_존재하지않는다면 {

      @Test
      void InvalidGoodsCodeException_예외가_발생한다() {
        String goodsCode = INPUT.split(" ")[1];
        when(checkCommandService.checkGoods(goodsCode)).thenThrow(
            new InvalidGoodsCodeException(goodsCode));

        assertThatThrownBy(() -> checkCommand.execute(INPUT))
            .isInstanceOf(InvalidGoodsCodeException.class)
            .hasMessage(goodsCode + ": 유효하지 않은 상품코드입니다.");
      }
    }
  }
}