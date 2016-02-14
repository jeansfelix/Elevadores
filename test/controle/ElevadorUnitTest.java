package controle;

import org.junit.Assert;
import org.junit.Test;

import controle.util.ContadorDeTempo;

public class ElevadorUnitTest extends UnitTest
{
    private static final String REQUISICAO_APENAS_NO_ANDAR_UM = "test/controle/dataset/requisicao_apenas_no_andar_um";

    @Test
    public void testRun_UmElevadorUmaRequisicaoParOAndarDois_ElevadorDeveTerminarNoAndarDois() throws Exception
    {
        int identificadorElevador = 0;
        int andarAtual = 1;
        SCE sce = new SCE();
        
        ContadorDeTempo.iniciarMarcacaoDeTempo();
        
        sce.lerArgumentos(REQUISICAO_APENAS_NO_ANDAR_UM);

        Elevador elevador = new Elevador(identificadorElevador, andarAtual, sce.getMonitorSCE());

        elevador.run();

        Assert.assertEquals(2, elevador.getAndarAtual());
    }
}
