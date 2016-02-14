package modelo.excecoes;

@SuppressWarnings("serial")
public class ArgumentosIncorretos extends RuntimeException
{
    public ArgumentosIncorretos(String message)
    {
        super(message);
    }

}
