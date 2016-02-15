package controle;

import java.util.List;

import controle.util.GeradorLog;
import modelo.Requisicao;
import modelo.TuplaAndarRequisicoes;

public class Elevador implements Runnable
{
    private static final String PONTO  = ".";
    private static final String ESPACO = " ";

    private int                 identificador;
    private int                 andarAtual;
    private GeradorLog          geradorLog;
    private MonitorSCE          monitorSCE;

    public Elevador(int identificador, int andarInicial, MonitorSCE monitorSCE)
    {
        this.monitorSCE = monitorSCE;
        this.identificador = identificador;
        this.andarAtual = andarInicial;
        geradorLog = new GeradorLog(Main.diretorioDestino, "elevador_" + identificador);
    }

    @Override
    public void run()
    {
        while (monitorSCE.existemRequisicoes())
        {
            TuplaAndarRequisicoes andarRequisicoes = obterRequisicoesDoAndarMaisProximo();

            if (andarRequisicoes == null)
            {
                geradorLog.escreverLog("Acabaram as requisicoes.");
                break;
            }

            irAteAndarComRequisicaoEPegarPassageiros(andarRequisicoes);
            irParaAndaresRequisitados(andarRequisicoes.getRequisicoes());
        }

        geradorLog.fecharArquivos();
    }

    private TuplaAndarRequisicoes obterRequisicoesDoAndarMaisProximo()
    {
        geradorLog.escreverLog(montarMsgEncontrarAndarMaisProximoComRequisicoes());

        TuplaAndarRequisicoes andarRequisicoes = monitorSCE.obterPessoasNoAndarComRequisicaoMaisProximo(andarAtual);

        geradorLog.escreverLog(montarMsgEncontrouAndarComRequisicoes(andarRequisicoes.getAndar()));

        return andarRequisicoes;
    }

    private void irParaAndaresRequisitados(List<Requisicao> requisicoesAtendidas)
    {
        int andar = andarAtual;

        for (Requisicao requisicao : requisicoesAtendidas)
        {
            andar = requisicao.getAndar();
            irAteAndar(andar);
            geradorLog.escreverLog(montarMsgIrAteAndarEDeixarPassageiro(requisicao));
        }

        geradorLog.escreverLog(montarMsgElevadorParouNoAndar(andar));
    }

    private String montarMsgElevadorParouNoAndar(int andar)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO).append("para no andar ").append(andar)
                .append(PONTO);

        return sb.toString();
    }

    private void irAteAndar(int andar)
    {
        andarAtual = andar;
    }

    private void irAteAndarComRequisicaoEPegarPassageiros(TuplaAndarRequisicoes andarRequisicoes)
    {
        geradorLog.escreverLog(montarMsgIrAteAndar(andarRequisicoes.getAndar()));
        geradorLog.escreverLog(montarMsgPegarPassageiros(andarRequisicoes.getRequisicoes()));

        andarAtual = andarRequisicoes.getAndar();
    }

    private String montarMsgEncontrarAndarMaisProximoComRequisicoes()
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO)
                .append("procurando por andar mais proximo com pessoas na fila.");

        return sb.toString();
    }

    private String montarMsgEncontrouAndarComRequisicoes(Integer andar)
    {
        StringBuilder sb = new StringBuilder("");

        sb.append("Elevador ").append(identificador).append(ESPACO).append("encontrou andar ").append(andar)
                .append(ESPACO).append("com pessoas na fila.");

        return sb.toString();
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
