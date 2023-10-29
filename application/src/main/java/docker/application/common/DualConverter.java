package docker.application.common;

import docker.domain.ipr.model.DualClass;
import jakarta.persistence.AttributeConverter;

public class DualConverter implements AttributeConverter<DualClass, String> {
    private static final String SEPARATOR = "_";


    @Override
    public String convertToDatabaseColumn(DualClass attribute) {
        if (attribute == null)
            return null;
        var builder = new StringBuilder();

        if (attribute.getStringField() != null && !attribute.getStringField().isBlank())
            builder.append(attribute.getStringField());

        builder.append(SEPARATOR);

        if (attribute.getLongField() != null)
            builder.append(attribute.getLongField());

        return builder.toString();
    }

    @Override
    public DualClass convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank())
            return null;

        var fields = dbData.split(SEPARATOR);
        if (fields.length == 0)
            return null;

        return new DualClass(fields[0].isBlank() ? null : fields[0], fields.length < 2 || fields[1].isBlank() ? null : Long.parseLong(fields[1]));
    }
}
