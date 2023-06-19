package exchangeGoods;

import exchangeGoods.model.ExchangeCommandType;
import exchangeGoods.service.CheckCommand;
import exchangeGoods.service.CheckCommandService;
import exchangeGoods.service.ClaimCommand;
import exchangeGoods.service.ClaimCommandService;
import exchangeGoods.service.Command;
import exchangeGoods.service.CommandRequestDto;
import exchangeGoods.service.HelpCommand;
import exchangeGoods.service.HelpCommandService;
import exchangeGoods.storage.GoodsStorage;
import exchangeGoods.storage.StoreStorage;
import java.util.Map;

/**
 * 해당 명령 타입을 찾아서 실행시키는 인보커 클래스
 */
public class ExchangeGoodsCommandManager {

  private final Map<ExchangeCommandType, Command> factory;

  /**
   * 객채를 생성하며 Command 들을 초기화합니다.
   */
  public ExchangeGoodsCommandManager() {
    this(
        Map.of(
            ExchangeCommandType.HELP, new HelpCommand(new HelpCommandService()),
            ExchangeCommandType.CHECK,
            new CheckCommand(new CheckCommandService(new GoodsStorage())),
            ExchangeCommandType.CLAIM,
            new ClaimCommand(new ClaimCommandService(new StoreStorage(), new GoodsStorage()))
        )
    );
  }

  /**
   * 의존성 주입을 위한 생성자
   * @param factory
   */
  public ExchangeGoodsCommandManager(Map<ExchangeCommandType, Command> factory) {
    this.factory = factory;
  }

  /**
   * 커맨드의 실행 결과를 반환하는 메서드
   *
   * @param commandRequestDto
   * @return 실행 결과 메세지를 반환합니다
   */
  public String getResult(CommandRequestDto commandRequestDto) {
    return findCommand(commandRequestDto.commandType()).execute(commandRequestDto.request());
  }

  /**
   * 명령어 타입에 맞는 커맨드를 찾는 메서드
   *
   * @param commandType
   * @return 해당 커맨드를 반환합니다
   */
  private Command findCommand(ExchangeCommandType commandType) {
    return factory.get(commandType);
  }
}
