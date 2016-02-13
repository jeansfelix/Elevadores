package controle;

import java.util.List;

import controle.util.GeradorLog;
import modelo.Requisicao;
import modelo.TuplaAndarRequisicoes;

public class Elevador implements Runnable
{
    private static final String PONTO  = ".";
    private static final String ESPACO = " ";
    private MonitorSCE          monitorSCE;
    private int                 identificador;
    private int                 andarAtual;
    private GeradorLog          geradorLog;

    public Elevador(int identificador, int andarInicial, MonitorSCE monitorSCE)
    {
        this.monitorSCE = monitorSCE;
        this.identificador = identificador;
        this.andarAtual = andarInicial;
        geradorLog = new GeradorLog("elevador_" + identificador);
    }

    @Override
    public void run()
    {
        while (monitorSCE.existemRequisicoes())
        {
            TuplaAndarRequisicoes andarRequisicoes = obterRequisicoesDoAndarMaisProximo();
            irAteAndarComRequisicaoEPegarPassageiros(andarRequisicoes);
            irParaAndaresRequisitados(andarRequisicoes.getRequisicoes());
        }

        geradorLog.fecharArquivos();
    }

    private TuplaAndarRequisicoes obterRequisicoesDoAndarMaisProximo()
    {
        TuplaAndarRequisicoes andarRequisicoes = monitorSCE.obterPessoasNoAndarComRequisicaoMaisProximo(andarAtual);

        return andarRequisicoes;
    }

    private void irParaAndaresRequisitados(List<Requisicao> requisicoesAtendidas)
    {
        for (Requisicao requisicao : requisicoesAtendidas)
        {
            irAteAndar(requisicao.getAndar());
            geradorLog.escreverLog(montarMsgIrAteAndarEDeixarPassageiro(requisicao));
        }
    }

    private void irAteAndar(int andar)
    {
        andarAtual = andar;
    }

    private void irAteAndarComRequisicaoEPegarPassageiros(TuplaAndarRequisicoes andarRequisicoes)
    {
        geradorLog.escreverLog(montarMsgElevadorDescobriuAndarComRequisicoes(andarRequisicoes.getAndar()));
        geradorLog.escreverLog(montarMsgIrAteAndar(andarRequisicoes.getAndar()));
        geradorLog.escreverLog(montarMsgPegarPassageiros(andarRequisicoes.getRequisicoes()));

        andarAtual = andarRequisicoes.getAndar();
    }

    private String montarMsgIrAteAndar(int andar)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO).append("se move até andar ").append(andar)
                .append(PONTO);

        return sb.toString();
    }

    private String montarMsgIrAteAndarEDeixarPassageiro(Requisicao requisicao)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append(montarMsgIrAteAndar(requisicao.getAndar())).append("Passageiro ")
                .append(requisicao.getIdentificador()).append(ESPACO).append("desce.");

        return sb.toString();
    }

    private String montarMsgElevadorDescobriuAndarComRequisicoes(int andar)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO).append("descobre que andar ").append(andar)
                .append(ESPACO).append("possui requisicao").append(PONTO);

        return sb.toString();
    }

    private String montarMsgPegarPassageiros(List<Requisicao> requisicoes)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO).append("pegou passageiros: [ ");

        for (Requisicao requisicao : requisicoes)
        {
            sb.append(requisicao.getIdentificador()).append(" ");
        }

        sb.append("]").append(PONTO);

        return sb.toString();
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
