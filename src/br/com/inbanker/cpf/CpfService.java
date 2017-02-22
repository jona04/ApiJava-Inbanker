package br.com.inbanker.cpf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


@Path("/consulta")
public class CpfService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	@GET
	@Path("/{cpf}")
	@Produces(MediaType.TEXT_PLAIN)
	public String verificaUsuarioCadastro(@PathParam("cpf") String cpf) {
		HttpResponse<JsonNode> response = null;
		try {
			// These code snippets use an open-source library. http://unirest.io/java
			response = Unirest.get("https://consulta-situacao-cpf-cnpj.p.mashape.com/consultaSituacaoCPF?cpf="+cpf)
			.header("X-Mashape-Key", "wi4OIBCGtmmsh9NygsXtnbPxU4hJp1iiiXkjsnIOtTr5JUhQqH")
			.asJson();
			
			//System.out.println("oi = "+response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.getBody().toString();
	}
}
