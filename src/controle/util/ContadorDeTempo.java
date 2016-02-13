package controle.util;

public class ContadorDeTempo
{
    private static Long tempoInicial;

    public static void iniciarMarcacaoDeTempo()
    {
        tempoInicial = System.currentTimeMillis();
    }

    public static Long tempDecorrido()
    {
        Long tempoFinal = System.currentTimeMillis();

        return (tempoFinal - tempoInicial);
    }
}
