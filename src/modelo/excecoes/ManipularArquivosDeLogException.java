package modelo.excecoes;

@SuppressWarnings("serial")
public class ManipularArquivosDeLogException extends RuntimeException
{
    public ManipularArquivosDeLogException(Exception e ,String msg)
    {
        super(msg, e);
    }
}
