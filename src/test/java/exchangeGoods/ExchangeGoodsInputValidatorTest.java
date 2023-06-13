package exchangeGoods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import exchangeGoods.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ExchangeGoodsInputValidator 테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ExchangeGoodsInputValidatorTest {

  @Nested
  class validateInput_메서드는 {

    @Nested
    class 공백_문자열이_주어지면 {

      final String INPUT = " ";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("아무런 입력이 없습니다. 원하는 요청을 입력해주세요");
      }
    }

    @Nested
    class 빈_문자열이_주어지면 {

      final String INPUT = "";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("아무런 입력이 없습니다. 원하는 요청을 입력해주세요");
      }
    }

    @Nested
    class null이_주어지면 {

      final String INPUT = " ";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("아무런 입력이 없습니다. 원하는 요청을 입력해주세요");
      }
    }

    @Nested
    class 길이가_30이_초과하는_문자열이_주어지면 {

      final String INPUT = "1231231231231231232131231231231231232132132131231232132131232132132132321";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("요청 입력은 최대 30글자 입니다.");
      }
    }

    @Nested
    class 잘못된_명령어_문자열이_주어지면 {

      final String INPUT = "NON";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("잘못된 요청입니다. HELP 요청으로 사용법을 확인해주세요");
      }
    }

    @Nested
    class 상품코드가_없는_CHECK_문자열이_주어지면 {

      final String INPUT = "CHECK";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("상품코드 입력이 없습니다. CHECK 요청은 CHECK [상점코드] 형식으로 입력되어야 합니다.");
      }
    }

    @Nested
    class 한개개_이상의_상품코드를_입력한_CHECK_문자열이_주어지면 {

      final String INPUT = "CHECK 123123123 123123111";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
      }
    }

    @Nested
    class 형식에_맞지않는_상품코드를_입력한_CHECK_문자열이_주어지면 {

      final String INPUT = "CHECK 123123";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
      }
    }

    @Nested
    class 형식에_맞는_상품코드를_입력한_CHECK_문자열이_주어지면 {

      final String INPUT = "CHECK 123123123";
      @Test
      void true를_반환한다 (){
        assertThat(ExchangeGoodsInputValidator.validateInput(INPUT)).isTrue();
      }
    }

    @Nested
    class HELP_문자열이_주어지면 {

      final String INPUT = "HELP";
      @Test
      void true를_반환한다 (){
        assertThat(ExchangeGoodsInputValidator.validateInput(INPUT)).isTrue();
      }
    }

    @Nested
    class 상점코드와_상품코드가_비어있는_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("상점코드, 상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다");
      }
    }

    @Nested
    class 상품코드가_비어있는_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM abscse";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("상품코드 입력이 없습니다. CLAIM 요청은 CLAIM [상점코드] [상품코드] 형식으로 입력되어야 합니다");
      }
    }

    @Nested
    class 형식에_맞지않는_상점코드_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM abscs 123123123";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("잘못된 상점코드 입니다. a-z, A-z로만 구성된 6자리의 상점코드를 입력해주세요");
      }
    }

    @Nested
    class 형식에_맞지않는_상품코드_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM abscse 12312";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("잘못된 상품코드 입니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
      }
    }

    @Nested
    class 한개_이상의_상품코드_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM abscse 123121 123123";
      @Test
      void InvalidInputException_예외가_발생한다 (){
        assertThatThrownBy(() -> ExchangeGoodsInputValidator.validateInput(INPUT))
            .isInstanceOf(InvalidInputException.class)
            .hasMessage("한번에 1가지의 상품코드만 요청할 수 있습니다. 숫자로만 구성된 9자리의 상품코드를 입력해주세요.");
      }
    }

    @Nested
    class 형식에_맞는_상점코드와_상품코드를_입력한_CLAIM_문자열이_주어지면 {

      final String INPUT = "CLAIM abcdef 123123123";
      @Test
      void true를_반환한다 (){
        assertThat(ExchangeGoodsInputValidator.validateInput(INPUT)).isTrue();
      }
    }
  }
}