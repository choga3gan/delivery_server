/**
 * @package     com/choga3gan/delivery/order/domain
 * @class       PriceConverter
 * @description 가격 변수 제어를 위한 컨버터
 *
 * @author      leehanbeen
 * @since
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 *  2025.11.04   leehanbeen       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.order.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PriceConverter implements AttributeConverter<Price, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Price attribute) {
        // Price 객체를 DB 컬럼(Integer)으로 변환
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public Price convertToEntityAttribute(Integer dbData) {
        // DB 컬럼(Integer)을 Price 객체로 변환
        return dbData == null ? null : new Price(dbData);
    }
}
