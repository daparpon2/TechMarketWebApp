package es.conselleria.daparpon.techmarket.utils;

import javax.validation.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 08/07/2018.
 *
 * @author Cesardl
 */
public final class TechMarketValidator<T> {

    private Validator validator;

    public TechMarketValidator() {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public Map<String, String> validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        return violations.stream().collect(
                Collectors.toMap(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
}
