package controle.util;

public class GeradorDeIndentificadores
{
    private long identificador = 0;
    
    public long pegarNovoIdentificador()
    {
        long valorRetornado = identificador;
        identificador++;
        
        return valorRetornado;
    }
}
