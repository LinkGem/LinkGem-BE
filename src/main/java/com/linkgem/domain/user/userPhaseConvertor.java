package com.linkgem.domain.user;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class userPhaseConvertor implements AttributeConverter<UserPhase,String> {

  @Override
  public String convertToDatabaseColumn(UserPhase attribute) {
    if(Objects.isNull(attribute)) return null;
    return attribute.getPhase();
  }

  @Override
  public UserPhase convertToEntityAttribute(String dbData) {
    return Stream.of(UserPhase.values()).filter(m -> m.getPhase().equals(dbData)).findAny().orElse(null);
  }
}
