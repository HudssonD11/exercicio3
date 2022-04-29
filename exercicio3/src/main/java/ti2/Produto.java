package ti2;

import java.io.Serializable;

public class Produto implements Serializable  
{
	private static final long serialVersionUID = 1L;
	private String palavra;
	private int tamanho;
	private String classificacao;
	
	public Produto()
	{
		palavra = "";
		tamanho = 0;
		classificacao = "";
	}
	
	public Produto(String palavra, String classificacao)
	{
		setPalavra(palavra);
		setClassificacao(classificacao);
	}
	
	public String getPalavra()
	{
		return palavra;
	}
	
	public String getClassificacao()
	{
		return classificacao;
	}
	
	public int getTamanho()
	{
		return palavra.length();
	}
	
	public void setPalavra(String newPalavra)
	{
		palavra = newPalavra;
		tamanho = newPalavra.length();
	}

	public void setClassificacao(String newclass)
	{
		classificacao = newclass;
	}

	@Override
	public String toString()
	{
		return ("Palavra: "+palavra+"   letras: "+tamanho+"   classificação: "+classificacao);
	}
	
/*	@Override 
	public boolean equals(Object obj)
	{
		return (this.palavra.equals(obj.getPalavra()) && this.classificacao.equals(obj.getClassificacao()));
	}
	*/
}
