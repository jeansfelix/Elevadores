package controle;

import org.junit.Assert;
import org.junit.Test;

public class MonitorSCEUnitTest extends UnitTest
{
    private static final String DOIS_ANDARES_IGUALMENTE_PROXIMOS_COM_REQUISICOES = "test/controle/dataset/dois_andares_igualmente_proximos_com_requisicoes";
    private static final String REQUISICAO_NOS_ANDARES_ZERO_E_UM                 = "test/controle/dataset/requisicao_nos_andares_zero_e_um";
    private static final String REQUISICAO_APENAS_NO_ANDAR_UM                    = "test/controle/dataset/requisicao_apenas_no_andar_um";

    @Test
    public void testBuscarAndarComRequisicao_AndarAtualComRequisicao_DeveRetornarAndarAtual() throws Exception
    {
        int andarAtual = 1;

        SCE sce = new SCE();
        sce.lerArgumentos(REQUISICAO_NOS_ANDARES_ZERO_E_UM);

        int andarRetornado = sce.getMonitorSCE().buscarAndarComRequisicaoMaisProximo(andarAtual);

        Assert.assertEquals(andarAtual, andarRetornado);
    }

    @Test
    public void testBuscarAndarComRequisicao_RequisicaoApenasNoAndarUm_ElevadorNoAndarZero_DeveRetornarAndarUm()
            throws Exception
    {
        int andarAtual = 0;

        SCE sce = new SCE();
        sce.lerArgumentos(REQUISICAO_APENAS_NO_ANDAR_UM);

        int andarRetornado = sce.getMonitorSCE().buscarAndarComRequisicaoMaisProximo(andarAtual);

        Assert.assertEquals(1, andarRetornado);
    }

    @Test
    public void testBuscarAndarComRequisicao_DoisAndaresIgualmenteProximos_DeveRetornarAndarComMaiorNumeroDeRequisicoes()
            throws Exception
    {
        int andarAtual = 1;
        int andarComMaiorNumeroDeRequisicoes = 0;
        SCE sce = new SCE();
        sce.lerArgumentos(DOIS_ANDARES_IGUALMENTE_PROXIMOS_COM_REQUISICOES);

        int andarRetornado = sce.getMonitorSCE().buscarAndarComRequisicaoMaisProximo(andarAtual);

        Assert.assertEquals(andarComMaiorNumeroDeRequisicoes, andarRetornado);
    }
}
