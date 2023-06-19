package exchangeGoods;

import common.ShareTreatsApp;
import java.io.BufferedReader;
import java.io.IOException;

public class ExchangeGoodsApp extends ShareTreatsApp {

  private static final String USAGE_INFORMATION = """
      상품 교환 서비스입니다.
      입력을 하시면 결과가 출력됩니다.
      이용 방법이 궁금하시면 HELP를 입력해주세요. 대소문자 구분은 없습니다.
      """;

  private final ExchangeGoodsController exchangeGoodsController;
  private final BufferedReader br;

  public ExchangeGoodsApp(ExchangeGoodsController controller, BufferedReader br) {
    this.exchangeGoodsController = controller;
    this.br = br;
  }

  @Override
  public void run() throws IOException {
    System.out.println(USAGE_INFORMATION);

    while(true) {
      String input = br.readLine();
      try {
        String result = exchangeGoodsController.run(input);
        if(result.equals("EXIT")) return;
        System.out.println(result);
      }
      catch (RuntimeException e) {
        System.out.println("현재 입력 : " + input);
        System.out.println(e.getMessage());
      }
    }
  }
}
