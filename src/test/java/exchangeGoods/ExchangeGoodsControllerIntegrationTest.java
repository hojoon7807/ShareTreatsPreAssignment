package exchangeGoods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import exchangeGoods.exception.InvalidGoodsCodeException;
import exchangeGoods.exception.InvalidInputException;
import exchangeGoods.model.ExchangeStatus;
import exchangeGoods.model.Goods;
import exchangeGoods.model.Store;
import exchangeGoods.storage.GoodsStorage;
import exchangeGoods.storage.StoreStorage;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ExchangeGoodsController 통합테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ExchangeGoodsControllerIntegrationTest {

  private ExchangeGoodsController exchangeGoodsController;

  @BeforeEach
  void setup() {
    ExchangeGoodsCommandManager exchangeGoodsCommandManager = new ExchangeGoodsCommandManager();
    exchangeGoodsController = new ExchangeGoodsController(exchangeGoodsCommandManager,
        new ExchangeGoodsInputValidator());
  }

  @Nested
  class run_메서드는 {

    @Nested
    class 입력이_30자를_초과하면 {

      final String OVER_30_LETTERS = "CHECK 123123123 123123123 123123123";
      final String MESSAGE = "요청 입력은 최대 30글자 입니다.";


      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(OVER_30_LETTERS))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 입력이_없으면 {

      final String EMPTY_INPUT = "";
      final String MESSAGE = "아무런 입력이 없습니다. 원하는 요청을 입력해주세요";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(EMPTY_INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 입력이_공백으로만_되어있다면 {

      final String EMPTY_INPUT = "  ";
      final String MESSAGE = "아무런 입력이 없습니다. 원하는 요청을 입력해주세요";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(EMPTY_INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 잘못된_명령어_입력이_주어지면 {

      final String INVALID_COMMAND_INPUT = "COMMAND";
      final String MESSAGE = "잘못된 요청입니다. HELP 요청으로 사용법을 확인해주세요";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(INVALID_COMMAND_INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상품코드가_없는_CHECK_입력이_주어지면 {

      final String EMPTY_GOODS_CODE_CHECK = "CHECK";
      final String MESSAGE = "상품코드 입력이 없습니다. CHECK 요청은 CHECK [상점코드] 형식으로 입력되어야 합니다.";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(EMPTY_GOODS_CODE_CHECK))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상품코드가_9자리_이하인_CHECK_입력이_주어지면 {

      final String UNDER_NINE_LETTERS_GOODS_CODE_CHECK = "CHECK 123123";
      final String MESSAGE = "잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(UNDER_NINE_LETTERS_GOODS_CODE_CHECK))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상품코드가_숫자문자열이_아닌_CHECK_입력이_주어지면 {

      final String INVALID_FORMAT_GOODS_CODE_CHECK = "CHECK 123123abs";
      final String MESSAGE = "잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(INVALID_FORMAT_GOODS_CODE_CHECK))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 두가지_이상의_상품코드_CHECK_입력이_주어지면 {

      final String MULTIPLE_GOODS_CODE_CHECK = "CHECK 123123123 123123121";
      final String MESSAGE = "한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {

        assertThatThrownBy(() -> exchangeGoodsController.run(MULTIPLE_GOODS_CODE_CHECK))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 이미_사용한_상품코드_CHECK_입력이_주어지면 {

      final String EXCHANGED_GOODS_CODE_CHECK = "CHECK 100000014";

      @Test
      void EXCHANGED_상태의_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(EXCHANGED_GOODS_CODE_CHECK);
        assertThat(resultMessage).isEqualTo(ExchangeStatus.EXCHANGED.getMessage());
      }
    }

    @Nested
    class 존재하지_않는_상품코드_CHECK_입력이_주어지면 {

      final String NOT_EXIST_GOODS_CODE_CHECK = "CHECK 110000014";

      @Test
      void EXCHANGED_상태의_메세지를_반환한다() {
        assertThatThrownBy(() -> exchangeGoodsController.run(NOT_EXIST_GOODS_CODE_CHECK))
            .isInstanceOf(InvalidGoodsCodeException.class)
            .hasMessageContaining("110000014");
      }
    }

    @Nested
    class 교환되지_않은_상품코드_CHECK_입력이_주어지면 {

      final String NOT_EXCHANGED_GOODS_CODE_CHECK = "CHECK 100000000";

      @Test
      void VALID_상태의_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(NOT_EXCHANGED_GOODS_CODE_CHECK);
        assertThat(resultMessage).isEqualTo(ExchangeStatus.VALID.getMessage());
      }
    }

    @Nested
    class HELP_입력이_주어지면 {

      final String HELP = "HELP";
      final String HELP_MESSAGE = """
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

      @Test
      void 서비스_이용안내_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(HELP);
        assertThat(resultMessage).isEqualTo(HELP_MESSAGE);
      }
    }

    @Nested
    class 다른_입력이_포함된_HELP_입력이_주어지면 {

      final String HELP_WITH_OTHER_WORD = "HELP check";
      final String HELP_MESSAGE = """
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

      @Test
      void 서비스_이용안내_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(HELP_WITH_OTHER_WORD);
        assertThat(resultMessage).isEqualTo(HELP_MESSAGE);
      }
    }

    @Nested
    class 대소문자가_섞인_HELP_입력이_주어지면 {

      final String HELP_COMPOSED_LOWERCASE_UPPERCASE = "HELP check";
      final String HELP_MESSAGE = """
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

      @Test
      void 서비스_이용안내_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(HELP_COMPOSED_LOWERCASE_UPPERCASE);
        assertThat(resultMessage).isEqualTo(HELP_MESSAGE);
      }
    }

    @Nested
    class EXIT_입력이_주어지면 {

      final String EXIT = "EXIT";

      @Test
      void EXIT_문자열을_반환한다() {
        String resultMessage = exchangeGoodsController.run(EXIT);
        assertThat(resultMessage).isEqualTo(EXIT);
      }
    }

    @Nested
    class 상점코드와_상품코드가_없는_CLAIM_입력이_주어지면 {

      final String CLAIM_WITHOUT_GOODS_CODE_AND_STORE_CODE = "CLAIM";
      final String MESSAGE = "상점코드, 상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITHOUT_GOODS_CODE_AND_STORE_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상품코드가_없는_CLAIM_입력이_주어지면 {

      final String CLAIM_WITHOUT_GOODS_CODE = "CLAIM asdvsd";
      final String MESSAGE = "상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITHOUT_GOODS_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상품코드가_9자리_이하인_CLAIM_입력이_주어지면 {

      final String CLAIM_WITH_INVALID_FORMAT_GOODS_CODE = "CLAIM asdvsd 10000000";
      final String MESSAGE = "잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITH_INVALID_FORMAT_GOODS_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 숫자문자열이_아닌_상품코드_CLAIM_입력이_주어지면 {

      final String CLAIM_WITH_INVALID_FORMAT_GOODS_CODE = "CLAIM asdvsd 10000000a";
      final String MESSAGE = "잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITH_INVALID_FORMAT_GOODS_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 상점코드가_6자리_이하인_CLAIM_입력이_주어지면 {

      final String CLAIM_WITH_INVALID_FORMAT_STORE_CODE = "CLAIM asdvs 100000000";
      final String MESSAGE = "잘못된 상점코드 입니다. a-z, A-z로만 구성된 6자리의 상점코드를 입력해주세요";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITH_INVALID_FORMAT_STORE_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 영어문자열이_아닌_상점코드_CLAIM_입력이_주어지면 {

      final String CLAIM_WITH_INVALID_FORMAT_STORE_CODE = "CLAIM asdvs1 100000000";
      final String MESSAGE = "잘못된 상점코드 입니다. a-z, A-z로만 구성된 6자리의 상점코드를 입력해주세요";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITH_INVALID_FORMAT_STORE_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 두가지_이상의_상품코드_CLAIM_입력이_주어지면 {

      final String CLAIM_WITH_MULTIPLE_GOODS_CODE = "CLAIM asdvsd 1000 100001";
      final String MESSAGE = "한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.";

      @Test
      void InvalidInputException_예외가_발생한다() {
        assertThatThrownBy(
            () -> exchangeGoodsController.run(CLAIM_WITH_MULTIPLE_GOODS_CODE))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage(MESSAGE);
      }
    }

    @Nested
    class 이미_사용한_상품코드_CLAIM_입력이_주어지면 {

      final String EXCHANGED_GOODS_CODE_CLAIM = "CLAIM abcdef 100000014";

      @Test
      void EXCHANGED_상태의_메세지를_반환한다() {
        String resultMessage = exchangeGoodsController.run(EXCHANGED_GOODS_CODE_CLAIM);
        assertThat(resultMessage).isEqualTo(ExchangeStatus.EXCHANGED.getMessage());
      }
    }

    @Nested
    class 존재하지_않는_상품코드_CLAIM_입력이_주어지면 {

      final String NOT_EXIST_GOODS_CODE_CLAIM = "CLAIM abcsed 110000014";

      @Test
      void EXCHANGED_상태의_메세지를_반환한다() {
        assertThatThrownBy(() -> exchangeGoodsController.run(NOT_EXIST_GOODS_CODE_CLAIM))
            .isInstanceOf(InvalidGoodsCodeException.class)
            .hasMessageContaining("110000014");
      }
    }

    @Nested
    class 유효한_CLAIM_입력이_주어지면 {

      final String VALID_CLAIM = "CLAIM %s %s";
      final String GOODS_CODE = "100000001";
      final String STORE_CODE = "abcsed";

      Optional<Store> findSavedStore(String storeCode) {
        StoreStorage storeStorage = new StoreStorage();
        return storeStorage.findByCode(storeCode);
      }

      Optional<Goods> findExchangedGoods(String goodsCode) {
        GoodsStorage goodsStorage = new GoodsStorage();
        return goodsStorage.findByCode(goodsCode);
      }

      /**
       * 교환 처리 후 상점이 저장되었는지와, 상점에 교환한 상품코드가 있는지를 확인합니다.
       */
      @Test
      void 성공적으로_상품코드를_교환변경하고_상점에_사용한_상품코드를_추가한다 () {
        String resultMessage = exchangeGoodsController.run(
            String.format(VALID_CLAIM, STORE_CODE, GOODS_CODE));

        assertAll(
            () -> assertThat(resultMessage).contains(GOODS_CODE, STORE_CODE),
            () -> assertThat(findSavedStore(STORE_CODE))
                .isPresent()
                .hasValueSatisfying(store ->
                    assertThat(store.getUsedGoodsCodes()).contains(GOODS_CODE)),
            () -> assertThat(findExchangedGoods(GOODS_CODE))
                .isPresent()
                .hasValueSatisfying(goods ->
                    assertThat(goods.getStatus()).isEqualTo(ExchangeStatus.EXCHANGED))
        );


      }
    }
  }

}
