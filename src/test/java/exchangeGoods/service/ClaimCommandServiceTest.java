package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.model.ExchangeStatus;
import exchangeGoods.model.Goods;
import exchangeGoods.model.Store;
import exchangeGoods.storage.Storage;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("ClaimCommandService 테스트")
class ClaimCommandServiceTest {
  ClaimCommandService claimCommandService;
  @Mock
  Storage storeStorage;
  @Mock
  Storage goodsStorage;

  final String GOODS_CODE = "123123123";
  final String STORE_CODE = "ababab";

  @BeforeEach
  void setup() {
    claimCommandService = new ClaimCommandService(storeStorage, goodsStorage);
  }

  @Nested
  class claimGoods_메소드는 {

    @Nested
    class 요청받은_상품코드가_존재하지_않으면 {

      @Test
      void InvalidGoodsCodeException_예외가_발생한다() {
        when(goodsStorage.findByCode(GOODS_CODE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> claimCommandService.claimGoods(createClaimServiceDto()))
            .isInstanceOf(InvalidGoodsCodeException.class);
      }
    }

    @Nested
    class 요청받은_상품코드가_이미_사용되었다면 {

      @Test
      void EXCHANGED_상태의_메세지를_반환한다() {
        Goods givenGoods = new Goods(GOODS_CODE, ExchangeStatus.EXCHANGED);
        when(goodsStorage.findByCode(GOODS_CODE)).thenReturn(Optional.of(givenGoods));

        String message = claimCommandService.claimGoods(createClaimServiceDto());
        assertThat(message).isEqualTo(ExchangeStatus.EXCHANGED.getMessage());
      }
    }

    @Nested
    class 요청받은_상품코드가_교환가능하고_코드를_처음사용하는_상점이면 {

      @Test
      void 상품을_교환하고_완료_메세지를_반환한다() {
        Goods givenGoods = new Goods(GOODS_CODE, ExchangeStatus.VALID);
        when(goodsStorage.findByCode(GOODS_CODE)).thenReturn(Optional.of(givenGoods));
        when(storeStorage.findByCode(STORE_CODE)).thenReturn(Optional.empty());
        String message = claimCommandService.claimGoods(createClaimServiceDto());
        assertThat(message)
            .isEqualTo("상품코드: %s 가 상점코드: %s 에서 사용되었습니다.", GOODS_CODE, STORE_CODE);
      }
    }

    @Nested
    class 요청받은_상품코드가_교환가능하고_코드사용기록이_있는_상점이면 {

      @Test
      void 상품을_교환하고_완료_메세지를_반환한다() {
        Goods givenGoods = new Goods(GOODS_CODE, ExchangeStatus.VALID);
        Store givenStore = new Store(STORE_CODE);
        when(goodsStorage.findByCode(GOODS_CODE)).thenReturn(Optional.of(givenGoods));
        when(storeStorage.findByCode(STORE_CODE)).thenReturn(Optional.of(givenStore));
        String message = claimCommandService.claimGoods(createClaimServiceDto());
        assertThat(message)
            .isEqualTo("상품코드: %s 가 상점코드: %s 에서 사용되었습니다.", GOODS_CODE, STORE_CODE);
      }
    }

    private ClaimServiceDto createClaimServiceDto() {
      return new ClaimServiceDto(STORE_CODE, GOODS_CODE);
    }
  }
}