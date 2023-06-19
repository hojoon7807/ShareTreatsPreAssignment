package exchangeGoods.service;

/**
 * Claim 역할의 Command 구현 클래스
 */
public class ClaimCommand implements Command {
  private final ClaimCommandService claimCommandService;

  public ClaimCommand(ClaimCommandService claimCommandService) {
    this.claimCommandService = claimCommandService;
  }

  @Override
  public String execute(String request) {
    String[] split = request.split(" ");
    return claimCommandService.claimGoods(new ClaimServiceDto(split[1], split[2]));
  }
}
