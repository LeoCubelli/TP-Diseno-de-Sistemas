package model.Converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

//Cortesía del seminario
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
  //Todo lo que encuentres como LocalDate, llevalo a Date.

  @Override
  public Date convertToDatabaseColumn(LocalDate locDate) {
    return locDate == null ? null : Date.valueOf(locDate);
  }

  @Override
  public LocalDate convertToEntityAttribute(Date sqlDate) {
    return sqlDate == null ? null : sqlDate.toLocalDate();
  }
}