package modelo.excecoes;

@SuppressWarnings("serial")
public class EntradaIncorretaExcetion extends RuntimeException
{
    public static final String ANDAR_DESTINO_INCORRETO = "Andar destino incorreto!";

    public EntradaIncorretaExcetion(String msg)
    {
        super(msg);
    }
}
