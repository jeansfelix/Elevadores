package modelo;

public class Requisicao
{
    long identificador;
    int  andar;

    public Requisicao(long identificador, int andar)
    {
        this.identificador = identificador;
        this.andar = andar;
    }

    public long getIdentificador()
    {
        return identificador;
    }

    public int getAndar()
    {
        return andar;
    }
}
