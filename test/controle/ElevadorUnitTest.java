package controle;

import org.junit.Assert;
import org.junit.Test;

public class ElevadorUnitTest extends UnitTest
{
    private static final String DOIS_ANDARES_IGUALMENTE_PROXIMOS_COM_REQUISICOES = "test/controle/dataset/dois_andares_igualmente_proximos_com_requisicoes";
    private static final String REQUISICAO_NOS_ANDARES_ZERO_E_UM                 = "test/controle/dataset/requisicao_nos_andares_zero_e_um";
    private static final String REQUISICAO_APENAS_NO_ANDAR_UM                    = "test/controle/dataset/requisicao_apenas_no_andar_um";

    @Test
    public void testBuscarAndarComRequisicao_AndarAtualComRequisicao_DeveRetornarAndarAtual() throws Exception
    {
        int andarAtual = 1;
        int identificadorElevador = 0;
        SCE sce = new SCE();
        sce.lerArgumentos(prepararEntrada(REQUISICAO_NOS_ANDARES_ZERO_E_UM));

        Elevador elevador = new Elevador(identificadorElevador, andarAtual, sce.getMonitorSCE());

        elevador.irAteAndarComRequisicao();

        Assert.assertEquals(andarAtual, elevador.getAndarAtual());
    }

    @Test
    public void testBuscarAndarComRequisicao_RequisicaoApenasNoAndarUm_ElevadorNoAndarZero_DeveRetornarAndarUm()
            throws Exception
    {
        int andarAtual = 0;
        int identificadorElevador = 0;
        SCE sce = new SCE();
        sce.lerArgumentos(prepararEntrada(REQUISICAO_APENAS_NO_ANDAR_UM));

        Elevador elevador = new Elevador(identificadorElevador, andarAtual, sce.getMonitorSCE());

        elevador.irAteAndarComRequisicao();


        Assert.assertEquals(1, elevador.getAndarAtual());
    }

    @Test
    public void testBuscarAndarComRequisicao_DoisAndaresIgualmenteProximos_DeveRetornarAndarComMaiorNumeroDeRequisicoes()
            throws Exception
    {
        int identificadorElevador = 0;
        int andarAtual = 1;
        int andarComMaiorNumeroDeRequisicoes = 0;
        SCE sce = new SCE();
        sce.lerArgumentos(prepararEntrada(DOIS_ANDARES_IGUALMENTE_PROXIMOS_COM_REQUISICOES));

        Elevador elevador = new Elevador(identificadorElevador, andarAtual, sce.getMonitorSCE());

        elevador.irAteAndarComRequisicao();

        Assert.assertEquals(andarComMaiorNumeroDeRequisicoes, elevador.getAndarAtual());
    }

    @Test
    public void testRun_UmElevadorUmaRequisicaoParOAndarDois_ElevadorDeveTerminarNoAndarDois() throws Exception
    {
        int identificadorElevador = 0;
        int andarAtual = 1;
        SCE sce = new SCE();
        sce.lerArgumentos(prepararEntrada(REQUISICAO_APENAS_NO_ANDAR_UM));

        Elevador elevador = new Elevador(identificadorElevador, andarAtual, sce.getMonitorSCE());

        elevador.run();

        Assert.assertEquals(2, elevador.getAndarAtual());
    }
}
