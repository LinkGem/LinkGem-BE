package com.linkgem.domain.user.convertor;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MailAuthConvertor implements AttributeConverter<Boolean,String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return Objects.nonNull(attribute) && attribute ? "Y" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return Objects.nonNull(dbData) && dbData.equals("Y");
	}
}
