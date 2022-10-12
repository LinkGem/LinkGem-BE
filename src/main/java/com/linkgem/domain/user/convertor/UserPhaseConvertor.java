package com.linkgem.domain.user.convertor;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.linkgem.domain.user.UserPhase;

@Converter(autoApply = true)
public class UserPhaseConvertor implements AttributeConverter<UserPhase, String> {

    @Override
    public String convertToDatabaseColumn(UserPhase attribute) {
      if (Objects.isNull(attribute)) {
        return null;
      }
        return attribute.name();
    }

    @Override
    public UserPhase convertToEntityAttribute(String dbData) {
        return Stream.of(UserPhase.values()).filter(m -> m.name().equals(dbData)).findAny().orElse(null);
    }
}
