package controle;

import org.junit.Assert;
import org.junit.Test;

import util.UnitTest;

public class ElevadorUnitTest extends UnitTest
{
    private static final String REQUISICAO_NOS_ANDARES_ZERO_E_UM = "test/controle/dataset/requisicao_nos_andares_zero_e_um";
    private static final String REQUISICAO_APENAS_NO_ANDAR_UM = "test/controle/dataset/requisicao_apenas_no_andar_um";
    
    @Test
    public void testBuscarAndarComRequisicao_AndarAtualComRequisicao_DeveRetornarAndarAtual() throws Exception
    {
        int andarAtual = 1;
        int identificadorElevador = 0;
        Elevador elevador = new Elevador(identificadorElevador, andarAtual);
        SCE.main(prepararEntrada(REQUISICAO_NOS_ANDARES_ZERO_E_UM));
        
        int andar = elevador.buscarAndarComRequisicao();
        
        Assert.assertEquals(andarAtual, andar);
    }
    
    @Test
    public void testBuscarAndarComRequisicao_RequisicaoApenasNoAndarUm_ElevadorNoAndarZero_DeveRetornarAndarUm() throws Exception
    {
        int andarAtual = 0;
        int identificadorElevador = 0;
        Elevador elevador = new Elevador(identificadorElevador, andarAtual);
        SCE.main(prepararEntrada(REQUISICAO_APENAS_NO_ANDAR_UM));
        
        Integer andar = elevador.buscarAndarComRequisicao();
        
        Assert.assertEquals(1, andar.intValue());
    }
    
}
