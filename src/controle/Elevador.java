package controle;

import java.util.List;

import modelo.Requisicao;

public class Elevador implements Runnable
{
    private MonitorSCE monitorSCE;
    private int        identificador;
    private int        andarAtual;

    public Elevador(int identificador, int andarInicial, MonitorSCE monitorSCE)
    {
        this.monitorSCE = monitorSCE;
        this.identificador = identificador;
        this.andarAtual = andarInicial;
    }

    @Override
    public void run()
    {
        while (monitorSCE.existemRequisicoes())
        {
            andarAtual = buscarAndarComRequisicao();

            List<Requisicao> requisicoesAtendidas = monitorSCE.atenderRequisicao(andarAtual);

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

        while (!(andarDestinoAbaixo < 0 && andarDestinoAcima >= monitorSCE.getQuantidadeDeAndares()))
        {
            andarDestino = escolherAndarComRequisicaoMaisProximo(andarDestinoAcima, andarDestinoAbaixo);

            if (andarDestino != null) return andarDestino;

            if (andarDestinoAcima < monitorSCE.getQuantidadeDeAndares()
                    && monitorSCE.getRequisicoesOrdenadasPorAndar().get(andarDestinoAcima).isEmpty())
            {
                andarDestinoAcima = andarDestinoAcima + 1;
            }

            if (andarDestinoAbaixo >= 0
                    && monitorSCE.getRequisicoesOrdenadasPorAndar().get(andarDestinoAbaixo).isEmpty())
            {
                andarDestinoAbaixo = andarDestinoAbaixo - 1;
            }
        }

        return null;
    }

    private Integer escolherAndarComRequisicaoMaisProximo(int andarDestinoAcima, int andarDestinoAbaixo)
    {
        Integer andarDestino = null;

        if (andarDestinoAcima < monitorSCE.getQuantidadeDeAndares() && verificarSeAndarTemRequisicao(andarDestinoAcima))
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
        if (monitorSCE.getRequisicoesOrdenadasPorAndar().get(andarDestinoAbaixo).size() > monitorSCE
                .getRequisicoesOrdenadasPorAndar().get(andarDestino).size())
        {
            return andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private boolean verificarSeAndarTemRequisicao(int andar)
    {
        return !monitorSCE.getRequisicoesOrdenadasPorAndar().get(andar).isEmpty();
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
