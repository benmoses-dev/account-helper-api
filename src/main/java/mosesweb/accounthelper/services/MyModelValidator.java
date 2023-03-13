package mosesweb.accounthelper.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.models.Sale;

/**
 *
 * @author Ben Moses
 */
class MyModelValidator
{

    void validate(Sale sale) throws ConstraintViolationException
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    void validate(Customer customer) throws ConstraintViolationException
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
