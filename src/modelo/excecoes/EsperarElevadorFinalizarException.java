package modelo.excecoes;

@SuppressWarnings("serial")
public class EsperarElevadorFinalizarException extends RuntimeException
{
    public EsperarElevadorFinalizarException(String message, Exception e)
    {
        super(message, e);
    }

}
