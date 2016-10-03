package cartao;

public class Vip implements TipoDeCartaoIF{
	private static final double DESCONTO_VIP = 0.85;
	private static final double BONUS_VIP = 0.5;
	private static final double ADICIONAL_VIP = 10;
	private static final double TAXA_CONVERTE_FIDELIDADE = 0.7;
	private static final double TAXA_CONVERTE_FIDELIDADE_BONUS = 0.5;
	
	@Override
	public double desconto() {
		return DESCONTO_VIP;
	}

	@Override
	public double bonus() {
		return BONUS_VIP;
	}

	@Override
	public double adicionalDesconto() {
		return ADICIONAL_VIP;
	}

	@Override
	public double adicionalBonus() {
		return 0;
	}

	@Override
	public double pagaDividasGastos(double qtdPontos) {
		double valorConvertido = TAXA_CONVERTE_FIDELIDADE * qtdPontos;
		double adicional = (int)(valorConvertido/10) * TAXA_CONVERTE_FIDELIDADE_BONUS;
		return valorConvertido + adicional;
	}

}
