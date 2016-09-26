package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import restaurante.ComparaNome;
import restaurante.Prato;
import restaurante.Refeicao;
import restaurante.RefeicaoCompleta;
import util.ValidacaoDeDados;
import exceptions.CadastraPratoException;
import exceptions.CadastraRefeicaoCompletaException;
import exceptions.CadastraRefeicaoException;
import exceptions.CadastroException;
import exceptions.ConsultaRestauranteException;
import exceptions.DadoInvalidoException;
import exceptions.StringInvalidaException;


/**
 * Classe responsavel controlar tudo o que acontece com as refeicoes e seus pratos.
 * @author Ariann Farias, Luan Rocha, Nilton Ginani, Yovany Cunha - Turma 03
 *
 */
public class RestauranteController {

	private List<Refeicao> cardapio;
	private ValidacaoDeDados validacao;

	public RestauranteController() {
		this.cardapio = new ArrayList<Refeicao>();
		this.validacao = new ValidacaoDeDados();
	}

	/**
	 * Metodo que cadastra/adiciona o prato no cardapio(Lista de pratos) do restaurante.
	 * passa seus parametros por uma serie de verificacoes que examinam se os dados passados estao corretos.
	 * @param nome
	 * @param preco
	 * @param descricao
	 * @throws CadastraPratoException
	 * @throws StringInvalidaException
	 */
	public void cadastraPrato(String nome, double preco, String descricao)
			throws CadastraPratoException, StringInvalidaException {
		Prato novoPrato;
		try {
			validacao.verificaPreco(preco);
			novoPrato = new Prato(nome, preco, descricao);
			cardapio.add(novoPrato);
		} catch (StringInvalidaException e) {
			throw new CadastraPratoException(e.getMessage());
		}
	}

	/**
	 * Metodo que busca um prato no cardapio a partir de seu nome.
	 * @param nome
	 * @return Retorna o prato caso exista ou nulo se nao existir.
	 * @throws StringInvalidaException 
	 */
	private Refeicao buscaRefeicao(String nome) throws StringInvalidaException {
		for (Refeicao prato : cardapio) {
			if (nome.equalsIgnoreCase(prato.getNome())) {
				return prato;
			}
		}
		throw new StringInvalidaException("So eh possivel cadastrar refeicoes com pratos ja cadastrados.");
	}

	/**
	 * Metodo que busca e retorna uma informacao de um atributo passado, para o prato passado.
	 * passa seus parametros por uma serie de verificacoes que examinam se os dados passados estao corretos.
	 * @param nome
	 * @param atributo
	 * @return Retorna uma String, contendo uma informacao a partir do preco ou da descricao da refeicao.
	 * @throws ConsultaRestauranteException 
	 */
	public String consultaRestaurante(String nome, String atributo) throws ConsultaRestauranteException {
		try {
			validacao.verificaNomePrato(nome);
			Refeicao refeicao = buscaRefeicao(nome);
			String info = "";
			switch (atributo.toUpperCase()) {
			case "PRECO":
				DecimalFormat df = new DecimalFormat("#0.00");
				info += "R$" + df.format(refeicao.calculaPreco());
				info = info.replace('.', ',');
				break;
			case "DESCRICAO":
				info += refeicao.getDescricao();
			}
			return info;
			
		} catch (StringInvalidaException e) {
			throw new ConsultaRestauranteException(e.getMessage());
		}
	}
	
	public String consultaMenuRestaurante(){
		String info = "";
		
		for (int i = 0; i < (this.cardapio.size()-1); i++){
			info += cardapio.get(i).getNome();
			info += ";";
		}
		info += cardapio.get(cardapio.size()-1).getNome();
		
		return info;
	}

	/**
	 * Metodo que cadastra uma nova refeicao na lista de refeicoes.
	 * passa seus parametros por uma serie de verificacoes que examinam se os dados passados estao corretos.
	 * @param nome
	 * @param descricao
	 * @param componentes
	 * @throws CadastraRefeicaoException
	 * @throws CadastraRefeicaoCompletaException
	 */
	public void cadastraRefeicao(String nome, String descricao, String componentes) throws CadastraRefeicaoException, CadastraRefeicaoCompletaException {
		RefeicaoCompleta novaRefeicaoCompleta;
		try {
			validacao.verificaNomeRefeicao(nome);
			validacao.verficaDescricaoRefeicao(descricao);
			validacao.verificaComponente(componentes);
			novaRefeicaoCompleta = new RefeicaoCompleta(nome, descricao);
			String[] ingredientes = componentes.split(";");
			for (String comp : ingredientes) {
				Refeicao refeicao = buscaRefeicao(comp);
				if (!(refeicao == null)) {
					novaRefeicaoCompleta.adicionaPrato((Prato) refeicao);
				}
			}
			validacao.verificaRefeicaoCompleta(componentes);
			cardapio.add(novaRefeicaoCompleta);
		} catch (StringInvalidaException e) {
			throw new CadastraRefeicaoException(e.getMessage());
		} catch (DadoInvalidoException e) {
			throw new CadastraRefeicaoCompletaException(e.getMessage());
		}
	}
	
	public void ordenaMenu(String tipoOrdenacao){
		if (tipoOrdenacao.equalsIgnoreCase("nome")){
			this.ordenaCardapioPorNome();
		}
		else if (tipoOrdenacao.equalsIgnoreCase("preco")){
			this.ordenaCardapioPorPreco();
		}
	}

	private void ordenaCardapioPorPreco(){
		Collections.sort(this.cardapio);
	}
	
	private void ordenaCardapioPorNome(){
		Collections.sort(this.cardapio, new ComparaNome());
	}
	
	

}
