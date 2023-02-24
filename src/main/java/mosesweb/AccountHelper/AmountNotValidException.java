package mosesweb.AccountHelper;

import java.math.BigDecimal;

public class AmountNotValidException extends RuntimeException
{
    public AmountNotValidException(BigDecimal amount)
    {
        super("The sale amount " + amount + " is not valid. It must be greater than zero");
    }
}
