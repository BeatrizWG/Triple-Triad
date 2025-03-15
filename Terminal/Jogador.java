package projeto;

import java.util.List;

public class Jogador {

	private String nome;
	private int pontuacao;
	private List<Carta> cartas;
	private String cor;

	public Jogador(String nome, String cor) {
		this.nome = nome;
		this.pontuacao = 5;
		this.cor = cor;
	}

	public String getNome() {
		return nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public void removerCarta(int posicao) {
		this.cartas.remove(posicao);
	}

	public void modificarPontuacao(int valor) {
		this.pontuacao += valor;
	}

	public Carta pegarCarta(int posicao) {
		return cartas.get(posicao);
	}

	public String getCor() {
		return cor;
	}

	public void exibirCartas() {
		System.out.println("Cartas de " + this.nome + ":");
		for (Carta carta : cartas) {
			System.out.println("NOME: " + carta.getNome() + " - TIPO: " + carta.getTipo());
			String valorS = (carta.getValorSuperior() < 10) ? Integer.toString(carta.getValorSuperior()) : "A";
			String valorE = (carta.getValorEsquerda() < 10) ? Integer.toString(carta.getValorEsquerda()) : "A";
			String valorD = (carta.getValorDireita() < 10) ? Integer.toString(carta.getValorDireita()) : "A";
			String valorI = (carta.getValorInferior() < 10) ? Integer.toString(carta.getValorInferior()) : "A";
			System.out.printf(carta.getCor() + "  %s\n%s   %s\n  %s %s\n", valorS, valorE, valorD, valorI, "\u001B[0m");
		}
	}
	
	public boolean jogarCarta(int posicao, int numCarta, Tabuleiro tabuleiro) {

		if (numCarta < 0 || numCarta >= getCartas().size()) {
			System.out.println("Número de carta inválido");
			return false;
		}

		if (tabuleiro.getPosicoesDisponiveis().contains(posicao)) {
			Carta cartaJogada = pegarCarta(numCarta);
			removerCarta(Integer.valueOf(numCarta));

			tabuleiro.posicionarCarta(cartaJogada, posicao);
			return true;

		} else {
			System.out.println("Posição inválida.");
			return false;
		}
	}		
}
