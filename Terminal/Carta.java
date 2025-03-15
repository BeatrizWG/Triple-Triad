package projeto;

public class Carta {
	private String nome;
	private int valorSuperior;
	private int valorInferior;
	private int valorEsquerda;
	private int valorDireita;
	private Jogador proprietario;
	private String tipo;
	private String cor;

	public Carta(String nome, int valorSuperior, int valorInferior, int valorEsquerda, int valorDireita, String tipo) {
		this.nome = nome;
		this.valorSuperior = valorSuperior;
		this.valorInferior = valorInferior;
		this.valorEsquerda = valorEsquerda;
		this.valorDireita = valorDireita;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getValorSuperior() {
		return valorSuperior;
	}

	public void setValorSuperior(int valorSuperior) {
		this.valorSuperior = valorSuperior;
	}

	public int getValorInferior() {
		return valorInferior;
	}

	public void setValorInferior(int valorInferior) {
		this.valorInferior = valorInferior;
	}

	public int getValorEsquerda() {
		return valorEsquerda;
	}

	public void setValorEsquerda(int valorEsquerda) {
		this.valorEsquerda = valorEsquerda;
	}

	public int getValorDireita() {
		return valorDireita;
	}

	public void setValorDireita(int valorDireita) {
		this.valorDireita = valorDireita;
	}

	public Jogador getProprietario() {
		return proprietario;
	}

	public void setProprietario(Jogador proprietario) {
		this.proprietario = proprietario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public void incrementarValores() {
		if (this.valorSuperior < 10) this.valorSuperior++;
		if (this.valorInferior < 10) this.valorInferior++;
		if (this.valorEsquerda < 10) this.valorEsquerda++;
		if (this.valorDireita < 10) this.valorDireita++;
	}



}
