package br.com.inbanker.email;

import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;

import java.security.Security;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/enviaEmail")
public class SendEmail {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	@POST
	@Path("/cadastro")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmailCadastro(Usuario usu){
		String resposta = null;
		 try {
			Properties props = new Properties();
			
			/*
			props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        */
			
			//props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY); 
			//props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			//props.setProperty("mail.smtp.socketFactory.fallback","false");
			//props.setProperty("mail.smtp.socketFactory.port",port);
			//props.put("mail.smtp.startssl.enable", "true");
			
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.zoho.com");
			props.put("mail.smtp.port", "587");
			

			
			 
	        Session session = Session.getDefaultInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication("no-reply@inbanker.com.br", "inbanker2016");
	                     }
	                });
	        session.setDebug(true);
       

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse(usu.getEmail());  
              message.setRecipients(Message.RecipientType.TO, toUser);

              message.setSubject("Cadastro realizado no Inbanker");//Assunto
              message.setText(
            		  "Olá "+ usu.getNome() +"!\n "
            		  + "\n Seu cadastro foi ralizado com sucesso."
            		  + "\n Aproveite o InBanker!" 
            		  + "\n\n Att.: Equipe InBanker" 
            		  );
              Transport.send(message);
              System.out.println("Feito!!!");
              resposta = "feito";
         } catch (Exception e) {
        	 e.printStackTrace();
        }
       
        
        return resposta;
	}
	
	@POST
	@Path("/transacao/{email_user1}/{email_user2}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmailTransacao(Transacao transacao,@PathParam("email_user1") String email_user1,@PathParam("email_user2") String email_user2){
		String resposta = null;
		 try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.zoho.com");
			props.put("mail.smtp.port", "587");
			

			
			 
	        Session session = Session.getDefaultInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication("no-reply@inbanker.com.br", "inbanker2016");
	                     }
	                });
	        session.setDebug(true);
       

              Message message_user1 = new MimeMessage(session);
              Message message_user2 = new MimeMessage(session);
              
              //usuario 1 confirmou recebimento do valor que pedio para usuario 2 - 1° recibo
              if(Integer.parseInt(transacao.getStatus_transacao()) == Transacao.CONFIRMADO_RECEBIMENTO) {
            	  
            	  message_user1.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser1 = InternetAddress //Destinatário(s)
                             .parse(email_user1);  
                  message_user1.setRecipients(Message.RecipientType.TO, toUser1);
      			
                  message_user1.setSubject("Você confirmou o recebimento do valor emprestado");//Assunto
                  message_user1.setText(
                		  "Olá "+ transacao.getNome_usu1() +"!\n "
                		  + "\n Você confirmou o recebimento do valor de "+ transacao.getValor() +" solicitado ao " + transacao.getNome_usu2() + " pelo aplicativo InBanker."
                		  + "\n Você deverá realizar o pagamento de quitação da dívida pessoalmente e fora do aplicativo InBanker, porém para validar e dar continuidade ao processo "
                		  + "é necessário que seu amigo(a) " + transacao.getNome_usu2() + " confirme o recebimento do valor através do aplicativo"
                		  + " para dar continuação a trasação." 
                		  + "\n\n Att.: Equipe InBanker" 
                		  
                		  );
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user1);
                  
                  message_user2.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser2 = InternetAddress //Destinatário(s)
                             .parse(email_user2);  
                  message_user2.setRecipients(Message.RecipientType.TO, toUser2);
      			
                  message_user2.setSubject("Seu amigo confirmou recebimento do valor emprestado");//Assunto
                  message_user2.setText(
                		  "Olá "+ transacao.getNome_usu2() +"!\n "
                		  + "\n Seu amigo(a) "+transacao.getNome_usu1()+" confirmou o recebimento do valor de "+ transacao.getValor() +" solicitado à você através do aplicativo InBanker."
                		  + "\n Aguarde agora que ele realize a quitação do pedido de empréstimo."
                		  + "\n Você deverá receber o valor de quitação da dívida pessoalmente e fora do aplicativo InBanker, porém para validar e dar continuidade ao processo é necessário que você"
                		  + " confirme o recebimento do valor através do aplicativo."
                		  + "\n\n Att.: Equipe InBanker" 
                		  
                		  );
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user2);
                  
              }
              
            //usuario 1 confirmou recebimento do valor que pedio para usuario 2 - 1° recibo
              if(Integer.parseInt(transacao.getStatus_transacao()) == Transacao.RESP_QUITACAO_SOLICITADA_CONFIRMADA) {
            	  
            	  message_user1.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser1 = InternetAddress //Destinatário(s)
                             .parse(email_user1);  
                  message_user1.setRecipients(Message.RecipientType.TO, toUser1);
      			
                  message_user1.setSubject("Seu amigo confirmou o recebimento do valor para quitação");//Assunto
                  message_user1.setText(
                		  "Olá "+ transacao.getNome_usu1() +"!\n "
                		  + "\n Seu amigo confirmou o recebimento do valor de "+ transacao.getValor() +" para quitação do emprestimo solicitado por você"
                		  + " através do aplicativo InBanker."
                		  + "\n Portanto essa transação foi finalizada com sucesso."
                		  + "Visualize os detalhes dessa e de outras transações finalizadas através do seu histórico no aplicativo InBanker." 
                		  + "\n\n Att.: Equipe InBanker" 
                		  
                		  );
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user1);
                  
                  message_user2.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser2 = InternetAddress //Destinatário(s)
                             .parse(email_user2);  
                  message_user2.setRecipients(Message.RecipientType.TO, toUser2);
      			
                  message_user2.setSubject("Você confirmou o recebimento do valor para quitação");//Assunto
                  message_user2.setText(
                		  "Olá "+ transacao.getNome_usu2() +"!\n "
                		  + "\n Você confirmou o recebimento do valor de "+ transacao.getValor() +" para quitação do emprestimo solicitado pelo seu amigo(a) "+ transacao.getNome_usu1()
                		  + " através do aplicativo InBanker."
                		  + "\n Portanto essa transação foi finalizada co sucesso."
                		  + "Visualize os detalhes dessa e de outras transações finalizadas através do seu histórico no aplicativo InBanker." 
                		  + "\n\n Att.: Equipe InBanker" 
                		  
                		  );
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user2);
                  
              }
              
              System.out.println("Feito!!!");
              resposta = "feito";
         } catch (Exception e) {
        	 e.printStackTrace();
        }
        
        return resposta;
  
	}
	
	@POST
	@Path("/envioPedido/{email_user1}/{email_user2}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmailEnvioPedido(Transacao transacao,@PathParam("email_user1") String email_user1,@PathParam("email_user2") String email_user2){
		String resposta = null;
		 try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.zoho.com");
			props.put("mail.smtp.port", "587");
			

			
			 
	        Session session = Session.getDefaultInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication("no-reply@inbanker.com.br", "inbanker2016");
	                     }
	                });
	        session.setDebug(true);
       

              Message message_user1 = new MimeMessage(session);
              Message message_user2 = new MimeMessage(session);

        	  message_user1.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

              Address[] toUser1 = InternetAddress //Destinatário(s)
                         .parse(email_user1);  
              message_user1.setRecipients(Message.RecipientType.TO, toUser1);
  			
              message_user1.setSubject("Você enviou pedido de emprestimo");//Assunto
              message_user1.setText(
            		  "Olá "+ transacao.getNome_usu1() +"!\n "
            		  + "\n Você enviou um pedido empréstimo no valor de "+ transacao.getValor() +" para seu amigo(a) " + transacao.getNome_usu2() + " através aplicativo InBanker."
            		  + "\n A guarde seu amigo aceitar ou recusar o pedido."
            		  + "\n O valor pago pela taxa de serviço só será debitado de seu cartão caso seu amigo(a) " + transacao.getNome_usu2() + " aceito o pedido." 
            		  + "\n\n Att.: Equipe InBanker" 
            		  
            		  );
              /**Método para enviar a mensagem criada*/
              Transport.send(message_user1);
              
              message_user2.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

              Address[] toUser2 = InternetAddress //Destinatário(s)
                         .parse(email_user2);  
              message_user2.setRecipients(Message.RecipientType.TO, toUser2);
  			
              message_user2.setSubject("Você recebeu pedido de emprestimo");//Assunto
              message_user2.setText(
            		  "Olá "+ transacao.getNome_usu1() +"!\n "
            		  + "\n Você recebeu um pedido empréstimo no valor de "+ transacao.getValor() +" pelo seu amigo(a) " + transacao.getNome_usu1() + " através aplicativo InBanker."
            		  + "\n Entre no aplicativo e veja os detalhes da transação, para em seguida Aceitar ou Recusar o pedido."
            		  + "\n\n Att.: Equipe InBanker" 
            		  
            		  );
              /**Método para enviar a mensagem criada*/
              Transport.send(message_user2);
              
              resposta = "feito";
              
              System.out.println("Feito!!!");
         } catch (Exception e) {
        	 e.printStackTrace();
        }
        
        return resposta;
  
	}
	
}
