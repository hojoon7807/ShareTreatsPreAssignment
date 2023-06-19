package exchangeGoods.service;

import exchangeGoods.model.ExchangeCommandType;

public record CommandRequestDto(ExchangeCommandType commandType, String request) {

}

