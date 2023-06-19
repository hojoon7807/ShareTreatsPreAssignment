package exchangeGoods;

import common.ShareTreatsApp;
import exchangeGoods.model.ExchangeCommandType;
import exchangeGoods.service.CommandRequestDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 상품교환 서비스 입력을 받아 결과를 출력합니다.
 */
public class ExchangeGoodsController {

  private final ExchangeGoodsCommandManager commandManager;
  private final ExchangeGoodsInputValidator validator;

  public ExchangeGoodsController(ExchangeGoodsCommandManager commandManager,
      ExchangeGoodsInputValidator validator) {
    this.commandManager = commandManager;
    this.validator = validator;
  }

  /**
   * 입력을 받아 해당 입력에 맞는 로직을 실행하고 결과를 출력합니다. 예외가 던져지면 그 예외를 출력합니다.
   *
   * @throws IOException
   * @throws exchangeGoods.exception.InvalidInputException
   * @throws exchangeGoods.exception.InvalidGoodsCodeException
   */

  public String run(String input) {
    String result = "";
    if (validator.validateInput(input)) {
      String[] splitInput = input.split(" ");
      ExchangeCommandType commandType = findCommandType(splitInput[0].toUpperCase());

      if (commandType.equals(ExchangeCommandType.EXIT)) {
        return "EXIT";
      }
      result = commandManager.getResult(new CommandRequestDto(commandType, input));

    }
    return result;
  }

  /**
   * 입력받은 명령어에 해당하는 커맨드 타입을 찾습니다.
   *
   * @param command
   * @return 문자열에 해당하는 ExchangeCommandType을 반환
   */
  public static ExchangeCommandType findCommandType(String command) {
    return ExchangeCommandType.valueOf(command);
  }
}
