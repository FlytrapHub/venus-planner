package com.flytrap.venusplanner.api.member.infrastructure.converter;

import com.flytrap.venusplanner.api.member.domain.OAuthPlatformType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OAuthPlatformTypeConverter implements AttributeConverter<OAuthPlatformType, String> {

    @Override
    public String convertToDatabaseColumn(OAuthPlatformType type) {
        if (type == null) {
            return null;
        }
        return type.name();
    }

    @Override
    public OAuthPlatformType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OAuthPlatformType.fromName(dbData);
    }
}
