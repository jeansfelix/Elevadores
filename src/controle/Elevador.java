package controle;

import java.util.List;

import modelo.Requisicao;

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
        while (SCE.numeroDeRequisicoes > 0)
        {
            andarAtual = buscarAndarComRequisicao();

            List<Requisicao> requisicoesAtendidas = SCE.atenderRequisicao(andarAtual);

            for (Requisicao requisicao : requisicoesAtendidas)
            {
                andarAtual = requisicao.getAndar();
            }
        }
    }

    protected Integer buscarAndarComRequisicao()
    {
        int andarDestinoAcima = andarAtual;
        int andarDestinoAbaixo = andarAtual;
        Integer andarDestino = null;

        while (!(andarDestinoAbaixo < 0 && andarDestinoAcima >= SCE.quantidadeDeAndares))
        {
            andarDestino = escolherAndarComRequisicaoMaisProximo(andarDestinoAcima, andarDestinoAbaixo);

            if (andarDestino != null) return andarDestino;

            if (andarDestinoAcima < SCE.quantidadeDeAndares
                    && SCE.requisicoesOrdenadasPorAndar.get(andarDestinoAcima).isEmpty())
            {
                andarDestinoAcima = andarDestinoAcima + 1;
            }

            if (andarDestinoAbaixo >= 0 && SCE.requisicoesOrdenadasPorAndar.get(andarDestinoAbaixo).isEmpty())
            {
                andarDestinoAbaixo = andarDestinoAbaixo - 1;
            }
        }

        return null;
    }

    private Integer escolherAndarComRequisicaoMaisProximo(int andarDestinoAcima, int andarDestinoAbaixo)
    {
        Integer andarDestino = null;

        if (andarDestinoAcima < SCE.quantidadeDeAndares && verificarSeAndarTemRequisicao(andarDestinoAcima))
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
        if (SCE.requisicoesOrdenadasPorAndar.get(andarDestinoAbaixo).size() > SCE.requisicoesOrdenadasPorAndar
                .get(andarDestino).size())
        {
            return andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private boolean verificarSeAndarTemRequisicao(int andar)
    {
        return !SCE.requisicoesOrdenadasPorAndar.get(andar).isEmpty();
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
