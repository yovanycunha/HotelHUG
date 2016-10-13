package cartao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Premium implements TipoDeCartaoIF{
	private static final double DESCONTO_PREMIUM = 0.9;
	private static final double BONUS_PREMIUM = 0.3; 
	private static final double ADICIONAL_PREMIUM = 10;
	private static final double TAXA_CONVERTE_FIDELIDADE = 0.3;
	private static final double TAXA_CONVERTE_FIDELIDADE_BONUS = 0.2;
	
	@Override
	public double desconto(double valorGasto) {
		double desconto = valorGasto * DESCONTO_PREMIUM;
		BigDecimal arredondado =  new BigDecimal(desconto).setScale(2, RoundingMode.HALF_UP);
		return arredondado.doubleValue();
	}

	@Override
	public double bonus() {
		return BONUS_PREMIUM;
	}

	@Override
	public double adicionalDesconto() {
		return 0;
	}

	@Override
	public double adicionalBonus() {
		return ADICIONAL_PREMIUM;
	}

	@Override
	public double pagaDividasGastos(double qtdPontos) {
		double valorConvertido = TAXA_CONVERTE_FIDELIDADE * qtdPontos;
		double adicional = ((int)(qtdPontos/10)) * TAXA_CONVERTE_FIDELIDADE_BONUS;
		double valorFinal = valorConvertido + adicional;
		return valorFinal;
	}

}
