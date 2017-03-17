package br.com.inbanker.gcm;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.inbanker.entidades.Transacao;

@Path("/notification")
public class GcmService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private GcmDao gcm = new GcmDao();
	
	@POST
	@Path("/sendNotification/{token}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String gcmTest(Transacao trans,@PathParam("token") String token) {
		String result = null;
		
		String title = null;
		String message = null;
		
		if(trans.getStatus_transacao().equals(String.valueOf(Transacao.AGUARDANDO_RESPOSTA))){
			title = "Solicitação de Empréstimo";
			message = "Você está recebendo um pedido de empréstimo de "+trans.getNome_usu1();
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.PEDIDO_ACEITO))){
			title = "Resposta do Emprestimo";
			message = "Seu pedido de empréstimo foi aceito por "+trans.getNome_usu2();
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.PEDIDO_RECUSADO))){
			title = "Resposta do Empréstimo";
			message = "Seu pedido de empréstimo foi recusado por "+trans.getNome_usu2();
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.CONFIRMADO_RECEBIMENTO))){
			title = "Confirmação Recebimento";
			message = "Seu amigo(a) "+trans.getNome_usu1()+" confirmou o recebimento do valor pedido para empréstimo.";
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.QUITACAO_SOLICITADA))){
			title = "Solicitação de Quitação";
			message = "Seu amigo(a) "+trans.getNome_usu1()+" esta solicitando que você confirme a quitação do empréstimo pedido por ele.";
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.RESP_QUITACAO_SOLICITADA_CONFIRMADA))){
			title = "Resposta de Quitação";
			message = "Seu amigo(a) "+trans.getNome_usu2()+" confirmou a solicitação de quitação de empréstimo que você enviou.";
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.RESP_QUITACAO_SOLICITADA_RECUSADA))){
			title = "Resposta de Quitação";
			message = "Seu amigo(a) "+trans.getNome_usu2()+" recusou a solicitação de quitação de empréstimo que você enviou.";
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.ENVIO_CANCELADO_ANTES_RESPOSTA))){
			title = "Pedido Cancelado";
			message = trans.getNome_usu1()+" cancelou o pedido de empréstimo que enviou a você.";
		}else if(trans.getStatus_transacao().equals(String.valueOf(Transacao.ENVIO_CANCELADO_ANTES_RECEBIMENTO))){
			title = "Pedido Cancelado";
			message =  trans.getNome_usu1()+" cancelou o pedido de empréstimo que enviou a você.";
		}
		
		try {
			result = gcm.sendAndroidNotification("notificacao",trans,token, message, title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
