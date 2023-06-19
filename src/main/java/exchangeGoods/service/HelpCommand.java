package exchangeGoods.service;

/**
 * Help 역할의 Command 구현 클래스
 */
public class HelpCommand implements Command {

  private final HelpCommandService helpCommandService;

  public HelpCommand(HelpCommandService helpCommandService) {
    this.helpCommandService = helpCommandService;
  }

  @Override
  public String execute(String code) {
    return helpCommandService.getHelpInformation();
  }
}
