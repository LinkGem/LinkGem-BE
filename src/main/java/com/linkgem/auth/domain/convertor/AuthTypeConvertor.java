package com.linkgem.auth.domain.convertor;

import com.linkgem.domain.auth.AuthType;
import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthTypeConvertor implements AttributeConverter<AuthType, String> {
    @Override
    public String convertToDatabaseColumn(AuthType attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public AuthType convertToEntityAttribute(String dbData) {
        return Stream.of(AuthType.values()).filter(m -> m.name().equals(dbData)).findAny().orElse(null);
    }
}
