package exchangeGoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import exchangeGoods.exception.InvalidGoodsCodeException;
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
@DisplayName("ClaimCommand 테스트")
class ClaimCommandTest {

  @InjectMocks
  ClaimCommand claimCommand;

  @Mock
  ClaimCommandService claimCommandService;

  @Nested
  class execute_메서드는 {

    final String INPUT = "CLAIM abcdef 111111111";
    final String GOODS_CODE = "111111111";
    @Nested
    class 입력한_상품코드가_존재하지_않는_코드라면 {

      @Test
      void InvalidGoodsCodeException_예외가_발생한다 () {
        when(claimCommandService.claimGoods(any(ClaimServiceDto.class))).thenThrow(
            new InvalidGoodsCodeException(GOODS_CODE));

        assertThatThrownBy(() -> claimCommand.execute(INPUT)).isInstanceOf(
            InvalidGoodsCodeException.class);
      }
    }

    @Nested
    class 유효한_입력이_주어지면 {

      final String MESSAGE = "CLAIM";
      @Test
      void 교환_결과를_반환한다 () {
        when(claimCommandService.claimGoods(any(ClaimServiceDto.class))).thenReturn(MESSAGE);

        assertThat(claimCommand.execute(INPUT)).isEqualTo(MESSAGE);
      }
    }
  }
}