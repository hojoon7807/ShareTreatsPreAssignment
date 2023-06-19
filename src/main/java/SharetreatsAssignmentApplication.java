import common.ServiceName;
import common.ShareTreatsApp;
import exchangeGoods.ExchangeGoodsApp;
import exchangeGoods.ExchangeGoodsCommandManager;
import exchangeGoods.ExchangeGoodsController;
import exchangeGoods.ExchangeGoodsInputValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SharetreatsAssignmentApplication {

  private static Map<ServiceName, ShareTreatsApp> service = new HashMap<>();
  private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final String USAGE_INFORMATION = """
      ShareTreats 사전과제 서비스입니다. 실행을 원하는 서비스의 키워드를 입력하시면 해당 서비스가 실행됩니다.
      입력 키워드의 대소문자 구분은 없습니다.
      상품교환 서비스: EXCHANGEGOODS
      빠칭코 상품 뽑기 서비스: PACHINKO
      회사 조직 인원수 파악 서비스: HEADCOUNTOFDEPT
      종료: EXIT
      """;

  private static final String INVALID_INPUT = "잘못된 입력입니다.";

  static {
    service.put(ServiceName.EXCHANGEGOODS, new ExchangeGoodsApp(
        new ExchangeGoodsController(new ExchangeGoodsCommandManager(),
            new ExchangeGoodsInputValidator()), br));
  }

  public static void main(String[] args) throws IOException {

    while (true) {
      System.out.println(USAGE_INFORMATION);
      String input = br.readLine().toUpperCase();

      if (input.equals("EXIT")) {
        return;
      }
      try {
        ServiceName serviceName = findService(input);
        service.get(serviceName).run();
      } catch (IllegalArgumentException e) {
        System.out.println(INVALID_INPUT);
        continue;
      }
    }

  }

  public static ServiceName findService(String serviceName) {
    return ServiceName.valueOf(serviceName);
  }

}
