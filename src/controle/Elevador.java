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
            irAteAndarComRequisicao();
            List<Requisicao> requisicoesAtendidas = monitorSCE.obterPessoasNoAndar(andarAtual);
            moverSeParaAndaresRequisitados(requisicoesAtendidas);
        }
    }

    private void moverSeParaAndaresRequisitados(List<Requisicao> requisicoesAtendidas)
    {
        for (Requisicao requisicao : requisicoesAtendidas)
        {
            irAteAndar(requisicao.getAndar());
        }
    }

    private void irAteAndar(int andar)
    {
        andarAtual = andar;
    }

    protected void irAteAndarComRequisicao()
    {
        andarAtual = monitorSCE.buscarAndarComRequisicao(andarAtual);
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
