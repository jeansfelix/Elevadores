package controle;

public class ContadorDeTempo
{
    private Long tempoInicial;
    
    public void iniciarMarcacaoDeTempo()
    {
        tempoInicial = System.currentTimeMillis();
    }
    
    public Double finalizarMarcacaoDeTempo()
    {
        Long tempoFinal = System.currentTimeMillis();
        
        return (Double) ((tempoFinal - tempoInicial) / 1000.0);
    }
}
