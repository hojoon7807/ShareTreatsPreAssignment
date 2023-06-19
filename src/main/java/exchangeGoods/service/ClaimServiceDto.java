package exchangeGoods.service;

/**
 * ClaimCommandService에 전달되는 DTO 클래스
 * @param storeCode
 * @param goodsCode
 */
public record ClaimServiceDto (String storeCode, String goodsCode){

}
