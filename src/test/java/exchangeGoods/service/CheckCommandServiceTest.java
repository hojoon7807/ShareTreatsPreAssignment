package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.model.ExchangeStatus;
import exchangeGoods.model.Goods;
import exchangeGoods.storage.Storage;
import java.util.Optional;
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
@DisplayName("CheckCommandService 테스트")
class CheckCommandServiceTest {

  @InjectMocks
  CheckCommandService checkCommandService;

  @Mock
  Storage storage;

  @Nested
  class 메서드는 {

    final String GOODS_CODE = "123123123";

    @Nested
    class 요청_상품코드가_존재하지_않으면 {

      @Test
      void InvalidGoodsCodeException_예외가_발생한다() {
        when(storage.findByCode(GOODS_CODE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> checkCommandService.checkGoods(GOODS_CODE)).isInstanceOf(
            InvalidGoodsCodeException.class).hasMessage("%s: 유효하지 않은 상품코드입니다.", GOODS_CODE);
      }
    }

    @Nested
    class 요청_상품코드가_이미_교환되었다면 {

      @Test
      void EXCHANGED_상태의_메세지를_출력한다() {
        Goods exchangedGoods = new Goods(GOODS_CODE, ExchangeStatus.EXCHANGED);
        when(storage.findByCode(GOODS_CODE)).thenReturn(Optional.of(exchangedGoods));

        String message = checkCommandService.checkGoods(GOODS_CODE);
        assertThat(message).isEqualTo(ExchangeStatus.EXCHANGED.getMessage());
      }
    }

    @Nested
    class 요청_상품코드가_교환되지않았다면 {

      @Test
      void VALID_상태의_메세지를_출력한다() {
        Goods exchangedGoods = new Goods(GOODS_CODE, ExchangeStatus.VALID);
        when(storage.findByCode(GOODS_CODE)).thenReturn(Optional.of(exchangedGoods));

        String message = checkCommandService.checkGoods(GOODS_CODE);
        assertThat(message).isEqualTo(ExchangeStatus.VALID.getMessage());
      }
    }
  }
}