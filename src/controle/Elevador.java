package controle;

public class Elevador implements Runnable
{
    private int identificador;
    private int andarAtual;

    public Elevador(int identificador, int andarInicial)
    {
        this.identificador = identificador;
        this.andarAtual = andarInicial;
    }

    @Override
    public void run()
    {

    }

    protected Integer buscarAndarComRequisicao()
    {
        int andarDestinoAcima = andarAtual;
        int andarDestinoAbaixo = andarAtual;

        while (true)
        {
            if (andarDestinoAcima < SCE.quantidade_andares
                    && !SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAcima).isEmpty())
            {
                return andarDestinoAcima;
            }

            if (andarDestinoAbaixo >= 0 && !SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAbaixo).isEmpty())
            {
                return andarDestinoAbaixo;
            }

            if (andarDestinoAcima < SCE.quantidade_andares
                    && SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAcima).isEmpty())
            {
                andarDestinoAcima = andarDestinoAcima + 1;
            }

            if (andarDestinoAbaixo >= 0 && SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAbaixo).isEmpty())
            {
                andarDestinoAbaixo = andarDestinoAbaixo - 1;
            }

            if (andarDestinoAbaixo < 0 && andarDestinoAcima >= SCE.quantidade_andares)
            {
                break;
            }
        }

        return null;
    }

    public int getAndarAtual()
    {
        return andarAtual;
    }

    public int getIdentificador()
    {
        return identificador;
    }
}
