package cartao;

public class Padrao implements TipoDeCartaoIF{
	private static final double DESCONTO_PADRAO = 1.0; // sem desconto
	private static final double BONUS_PADRAO = 0.1;
	private static final double TAXA_CONVERTE_FIDELIDADE = 0.1;
	
	@Override
	public double desconto(double valorgasto) {
		return valorgasto;
	}

	@Override
	public double bonus() {
		return BONUS_PADRAO;
	}

	@Override
	public double adicionalDesconto() {
		return 0;
	}

	@Override
	public double adicionalBonus() {
		return 0;
	}

	@Override
	public double pagaDividasGastos(double qtdPontos) {
		return TAXA_CONVERTE_FIDELIDADE * qtdPontos;
	}

}
