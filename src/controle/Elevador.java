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
        Integer andarDestino = null;

        while (!(andarDestinoAbaixo < 0 && andarDestinoAcima >= SCE.quantidade_andares))
        {
            andarDestino = escolherAndarComRequisicaoMaisProximo(andarDestinoAcima, andarDestinoAbaixo);

            if (andarDestino != null) return andarDestino;

            if (andarDestinoAcima < SCE.quantidade_andares
                    && SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAcima).isEmpty())
            {
                andarDestinoAcima = andarDestinoAcima + 1;
            }

            if (andarDestinoAbaixo >= 0 && SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAbaixo).isEmpty())
            {
                andarDestinoAbaixo = andarDestinoAbaixo - 1;
            }
        }

        return null;
    }

    private Integer escolherAndarComRequisicaoMaisProximo(int andarDestinoAcima, int andarDestinoAbaixo)
    {
        Integer andarDestino = null;

        if (andarDestinoAcima < SCE.quantidade_andares && verificarSeAndarTemRequisicao(andarDestinoAcima))
        {
            andarDestino = andarDestinoAcima;
        }

        if (andarDestinoAbaixo >= 0 && verificarSeAndarTemRequisicao(andarDestinoAbaixo))
        {
            if (andarDestino != null)
            {
                return obterAndarComMaisPessoasEsperando(andarDestinoAbaixo, andarDestino);
            }

            andarDestino = andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private int obterAndarComMaisPessoasEsperando(int andarDestinoAbaixo, Integer andarDestino)
    {
        if (SCE.requisicoes_ordenadas_por_andar.get(andarDestinoAbaixo).size() > SCE.requisicoes_ordenadas_por_andar
                .get(andarDestino).size())
        {
            return andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private boolean verificarSeAndarTemRequisicao(int andar)
    {
        return !SCE.requisicoes_ordenadas_por_andar.get(andar).isEmpty();
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
