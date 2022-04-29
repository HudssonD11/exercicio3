package ti2;

import java.sql.*;

public class ProdutoDAO 
{
	private Connection conexao;
	
	public ProdutoDAO()
	{
		conexao = null;
	}
	
	public boolean conectar()
	{
		String driverName = "Org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "palavras";
		int porta = 5432;
		String url = "jdbc:postgresql://"+serverName+":"+porta+"/"+mydatabase;
		String username = "ti2cc";
		String password = "senha";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexao efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexao NAO efetuada com o postgres -- Driver nao encontrado -- " + e.getMessage());         
		} catch (SQLException e) {
			System.err.println("Conexao NAO efetuada com o postgres -- " + e.getMessage());         			
		}
		
		return status;
	}

	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirPalavra(Produto palavra) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO palavras (palavra, tamanho, classificacao) "
					       + "VALUES ("+palavra.getPalavra()+ ", '" + palavra.getTamanho() + "', '"  
					       + palavra.getClassificacao() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarPalavra(Produto palavra) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE palavras SET palavra = '" + palavra.getPalavra() + "', tamanho = '"  
				       + palavra.getTamanho() + "', classificacao = '" + palavra.getClassificacao() + "'"
					   + " WHERE palavra = " + palavra.getPalavra();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirPalavra(String chave) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM palavras WHERE palavra = " + chave);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Produto[] getProdutos() {
		
		Produto[] palavras = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM palavras");		
	         if(rs.next()){
	             rs.last();
	             palavras = new Produto[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                palavras[i] = new Produto(rs.getString("palavra"), rs.getString("classificacao"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return palavras;
	}

	public Produto getProduto(String chave) {
		
		Produto palavra = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM palavras WHERE palavra = "+chave);		
	         if(rs.next()){
	             rs.beforeFirst();

                palavra = new Produto(rs.getString("palavra"), rs.getString("classificacao"));
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return palavra;
	}
}
