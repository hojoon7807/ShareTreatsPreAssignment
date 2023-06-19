package exchangeGoods.service;

/**
 * Check 역할의 Command 구현 클래스
 */
public class CheckCommand implements Command {

  private final CheckCommandService checkCommandService;

  public CheckCommand(CheckCommandService checkCommandService) {
    this.checkCommandService = checkCommandService;
  }

  @Override
  public String execute(String request) {
    String goodsCode = request.split(" ")[1];
    return checkCommandService.checkGoods(goodsCode);
  }
}
