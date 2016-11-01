package br.com.inbanker.gcm;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.inbanker.entidades.Transacao;

@Path("/notification")
public class GcmService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private testGcm test = new testGcm();
	
	@GET
	@Path("/sendNotification/{tipo}/{token}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String gcmTest(Transacao trans,@PathParam("token") String token) {
		String result = null;
		
		String title = null;
		String message = null;
		
		if(trans.getStatus_transacao().equals(Transacao.AGUARDANDO_RESPOSTA)){
			title = "Solicitação de emprestimo";
			message = "Você está recebendo um pedido de empréstimo de "+trans.getNome_usu1();
		}else if(trans.getStatus_transacao().equals(Transacao.PEDIDO_ACEITO)){
			title = "Resposta do emprestimo";
			message = "Seu pedido de emprestimo foi aceito por "+trans.getNome_usu2();
		}else if(trans.getStatus_transacao().equals(Transacao.PEDIDO_RECUSADO)){
			title = "Resposta do emprestimo";
			message = "Seu pedido de emprestimo foi recusado por "+trans.getNome_usu2();
		}else if(trans.getStatus_transacao().equals(Transacao.CONFIRMADO_RECEBIMENTO)){
			title = "Confirmação recebimento";
			message = "Seu amigo(a) "+trans.getNome_usu1()+" confirmou o recebimento do valor pedido para emprestimo.";
		}else if(trans.getStatus_transacao().equals(Transacao.QUITACAO_SOLICITADA)){
			title = "Solicitação de quitação";
			message = "Seu amigo(a) "+trans.getNome_usu1()+" esta solicitando que você confirme a quitação do emprestimo pedido por ele.";
		}else if(trans.getStatus_transacao().equals(Transacao.RESP_QUITACAO_SOLICITADA_CONFIRMADA)){
			title = "Resposta de quitação";
			message = "Seu amigo(a) "+trans.getNome_usu2()+" confirmou a solicitação de quitação de emprestimo que você enviou.";
		}else if(trans.getStatus_transacao().equals(Transacao.RESP_QUITACAO_SOLICITADA_RECUSADA)){
			title = "Resposta de quitação";
			message = "Seu amigo(a) "+trans.getNome_usu2()+" recusou a solicitação de quitação de emprestimo que você enviou.";
		}
		
		try {
			result = test.sendAndroidNotification(token, message, title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
