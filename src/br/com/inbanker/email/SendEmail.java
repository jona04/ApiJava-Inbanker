package br.com.inbanker.email;

import br.com.inbanker.dao.UsuarioDAO;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Properties;
import java.util.Random;

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
	@Path("/mensagem/{mensagem}/{assunto}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmailMensagem(Usuario usu,@PathParam("mensagem") String mensagem,@PathParam("assunto") String assunto){
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
       
              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse("atendimento@inbanker.com.br");  
              message.setRecipients(Message.RecipientType.TO, toUser);
              
              message.setContent(textEmailMensagem(mensagem,assunto,usu.getNome(),usu.getEmail(),usu.getCpf()), "text/html; charset=utf-8");
              message.setSubject("Mensagem aplicativo Inbanker");//Assunto

              Transport.send(message);
              System.out.println("Feito!!!");
              resposta = "feito";
         } catch (Exception e) {
        	 e.printStackTrace();
        }
       
        return resposta;
	}
	
	@POST
	@Path("/novasenha")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmailNovaSenha(Usuario usu){
		String resposta = null;
		
		 String nova_senha = generateRandomString();
		 String edit_senha = null;
		 usu.setSenha(md5(nova_senha));
		 try{
			 UsuarioDAO.novaSenhaUsuarioByCPF(usu);
			 edit_senha = "ok";
		 }catch(Exception e){
			 
		 }
		
		if(edit_senha == "ok"){
			
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
	       
	              Message message = new MimeMessage(session);
	              message.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente
	
	              Address[] toUser = InternetAddress //Destinatário(s)
	                         .parse(usu.getEmail());  
	              message.setRecipients(Message.RecipientType.TO, toUser);
	              
	              message.setContent(textEmailNovaSenha(usu,nova_senha), "text/html; charset=utf-8");
	              message.setSubject("Nova senha InBanker");//Assunto
	
	              Transport.send(message);
	              System.out.println("Feito!!!");
	              resposta = "feito";
	        } catch (Exception e) {
	        	 e.printStackTrace();
	        }
		}
        return resposta;
	}
	
	
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
              
              message.setContent(textEmailCadastro(usu.getNome()), "text/html; charset=utf-8");
              message.setSubject("Cadastro realizado no Inbanker");//Assunto

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
      			
                  message_user1.setSubject("Voce confirmou o recebimento do valor emprestado");//Assunto
                  message_user1.setContent(textEmailFormalizaContrato(transacao.getNome_usu1(), transacao.getNome_usu2(),transacao.getId_contrato()), "text/html; charset=utf-8");

                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user1);
                  
                  message_user2.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser2 = InternetAddress //Destinatário(s)
                             .parse(email_user2);  
                  message_user2.setRecipients(Message.RecipientType.TO, toUser2);
      			
                  message_user2.setSubject("Seu amigo confirmou recebimento do valor emprestado");//Assunto
                  message_user2.setContent(textEmailFormalizaContrato(transacao.getNome_usu2(),transacao.getNome_usu1(),transacao.getId_contrato()), "text/html; charset=utf-8");

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
                  message_user1.setContent(textEmailReciboFinalizado(transacao.getNome_usu1(),transacao.getNome_usu2(),transacao.getId_recibo(),transacao.getId_contrato()), "text/html; charset=utf-8");

                  /**Método para enviar a mensagem criada*/
                  Transport.send(message_user1);
                  
                  message_user2.setFrom(new InternetAddress("no-reply@inbanker.com.br","InBanker F. Consulting")); //Remetente

                  Address[] toUser2 = InternetAddress //Destinatário(s)
                             .parse(email_user2);  
                  message_user2.setRecipients(Message.RecipientType.TO, toUser2);
      			
                  message_user2.setSubject("Voce confirmou o recebimento do valor para quitação");//Assunto
                  message_user2.setContent(textEmailReciboFinalizado(transacao.getNome_usu2(),transacao.getNome_usu1(),transacao.getId_recibo(),transacao.getId_contrato()), "text/html; charset=utf-8");
                  
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
	
	/*@POST
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
              Transport.send(message_user2);
              
              resposta = "feito";
              
              System.out.println("Feito!!!");
         } catch (Exception e) {
        	 e.printStackTrace();
        }
        
        return resposta;
  
	}*/
	
	public String textEmailCadastro(String nome){
		
		String texto ="<html>"
			+"<body style='width: 500px;'>"
			+"<h1 style='color:green' align='center'>Seja Bem Vind@!</h1>"
			
			+"<p>Olá, <strong>"+nome+"</strong>, tudo bem?</p>"
			
			+"<p>Você acabou de criar uma conta <strong>InBanker</strong>! Obrigado por se cadastrar no <strong>InBanker</strong>, o único sistema de ajuda mútua do Brasil.</p>"
			
			+"<p>Por ele, você poderá emprestar e solicitar empréstimos aos seus amigos, formalizando contratos de mútuo sempre com muita segurança e sem burocracia. </p>"
			
			+"<div style='border: solid gray;margin-top: 50px;padding: 5px'>"
				+"<img src='https://s10.postimg.org/oclpysywp/Captura_de_Tela_2017_03_20_s_10_51_33.png' style='float: left; width:40px;padding:10px'>"
				+"<p style=''>Mantenha sempre seus dados em segurança, não revele seus dados de acesso à ninguém. Nós nunca pediremos sua senha. Caso suspeite que alguém esteja usando sua conta, entre em contato conosco imediatamente.</p>"
			+"</div>"
			+"<p style='color: gray;font-size: 12x' align='center'>Esse email é automático e não deve ser respondido</p>"
			+"</body>"
			+"</html>";
		
		return texto;
	} 
	
public String textEmailFormalizaContrato(String nome1,String nome2,String id_contrato){
		
		String texto ="<html>"
			+"<body style='width: 500px;'>"
			
			+"<p>Olá, <strong>"+nome1+"</strong>, tudo bem?</p>"
			
			+"<p>Você e "+nome2+" formalizaram o Contrato de Mútuo através do nosso sistema <strong>InBanker</strong>.</p>"
			
			+"<p> <strong>ID Contrato: "+id_contrato+"</strong></p>"
			
			+"<p>Clique no link abaixo para visualizar o Contrato e o Comprovante de recebimento. </p>"
			+"<p><a style='background-color: #4CAF50;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display:inline-block;font-size: 16px;margin: 4px 2px;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);cursor: pointer;' href='http://www.inbanker.com.br/contrato/"+id_contrato+"' target='_blank'>Contrato</a> <a style='background-color: #008CBA;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display:inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);' href='http://www.inbanker.com.br/recibo/"+id_contrato+"' target='_blank'>Recibo</a></p>"
			
			+"<p>Acompanhe seu contrato pelo aplicativo ou site <a href='http://www.inbanker.com.br/login' target='_blank'>www.inbanker.com.br/login</a> e deixe conosco a administração do mesmo.</p>"
			
			+"<p>Alguma dúvida? <a href='http://www.inbanker.com.br' target='_blank'>Clique aqui</a>, ou entre em contato com a gente por e-mail, chat ou telefone.</p>"
			
			
			+"<ul style='font-size: 14px;margin-top: 50px'> <strong>Informaçoes importantes: </strong>"
				+"<li>Verifique os termos de uso e a política de privacidade da nossa empresa através do nosso site ou aplicativo.</li>"
				+"<li>As obrigações geradas pelo contrato são válidas e podem acarretar em ação de cobrança judicial caso haja o atraso no pagamento da divida.</li>"
			+"</ul>"
			
			+"<div style='border: solid gray;margin-top: 50px;padding: 5px'>"
				+"<img src='https://s10.postimg.org/oclpysywp/Captura_de_Tela_2017_03_20_s_10_51_33.png' style='float: left; width:40px;padding:10px'>"
				+"<p style=''>Mantenha sempre seus dados em segurança, não revele seus dados de acesso à ninguém. Nós nunca pediremos sua senha. Caso suspeite que alguém esteja usando sua conta, entre em contato conosco imediatamente.</p>"
			+"</div>"
			+"<p style='color: gray;font-size: 12x' align='center'>Esse email é automático e não deve ser respondido</p>"
			+"</body>"
			+"</html>";
		
		return texto;
	} 

	public String textEmailReciboFinalizado(String nome1,String nome2,String id_recibo,String id_contrato){
		
		String texto ="<html>"
				+"<body style='width: 500px;'>"
	
				+"<p>Olá, <strong>"+nome1+"</strong>, tudo bem?</p>"
	
		+"<p>O Contrato de Mútuo com "+nome2+", foi quitado e finalizado através do nosso sistema <strong>InBanker</strong>.</p>"
	
		+"<p> <strong>Contrato: "+id_contrato+"</strong><br><strong>Recibo de quitação: "+id_recibo+"</strong></p>"
	
		+"<p>Clique no link abaixo para visualizar o Contrato e o Comprovante de quitacao. </p>"
		+"<p><a style='background-color: #4CAF50;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display:inline-block;font-size: 16px;margin: 4px 2px;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);cursor: pointer;' href='http://www.inbanker.com.br/contrato/"+id_contrato+"' target='_blank'>Contrato</a> <a style='background-color: #008CBA;border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none;display:inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);' href='http://www.inbanker.com.br/recibo-quitacao/"+id_recibo+"' target='_blank'>Recibo</a></p>"
		
		+"<p>Acompanhe seu contrato pelo aplicativo ou site <a href='http://www.inbanker.com.br/login' target='_blank'>www.inbanker.com.br/login</a> e deixe conosco a administração do mesmo.</p>"
		
		+"<p>Alguma dúvida? <a href='http://www.inbanker.com.br' target='_blank'>Clique aqui</a>, ou entre em contato com a gente por e-mail, chat ou telefone.</p>"
		
		
		+"<ul style='font-size: 14px;margin-top: 50px'> <strong>Informaçoes importantes: </strong>"
			+"<li>Verifique os termos de uso e a política de privacidade da nossa empresa através do nosso site ou aplicativo.</li>"
			+"<li>As obrigações geradas pelo contrato são válidas e podem acarretar em ação de cobrança judicial caso haja o atraso no pagamento da divida.</li>"
		+"</ul>"
		
		+"<div style='border: solid gray;margin-top: 50px;padding: 5px'>"
			+"<img src='https://s10.postimg.org/oclpysywp/Captura_de_Tela_2017_03_20_s_10_51_33.png' style='float: left; width:40px;padding:10px'>"
			+"<p style=''>Mantenha sempre seus dados em segurança, não revele seus dados de acesso à ninguém. Nós nunca pediremos sua senha. Caso suspeite que alguém esteja usando sua conta, entre em contato conosco imediatamente.</p>"
		+"</div>"
		+"<p style='color: gray;font-size: 12x' align='center'>Esse email é automático e não deve ser respondido</p>"
		+"</body>"
		+"</html>";
		
		return texto;
	} 
	public String textEmailMensagem(String mensagem,String assunto,String nome,String email,String cpf){
		String texto ="<html>"
				+"<body style='width: 500px;'>"
	
				+"<p>Enviado por: <strong>"+nome+"</strong></p>"
				+"<p>CPF: <strong>"+cpf+"</strong></p>"
				+"<p>Email: <strong>"+email+"</strong></p>"
				+"<p>Assunto: <strong>"+assunto+"</strong></p>"
	
		+"<p></p>"
		+"<p>"+mensagem+"</p>"
		+"<p></p>"
	
		
		+"<p style='color: gray;font-size: 12x' align='center'>Esse foi enviado atraves do aplicativo inbanker</p>"
		+"</body>"
		+"</html>";
		
		return texto;
	}
	
	private static final String CHAR_LIST =
	        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    private static final int RANDOM_STRING_LENGTH = 10;
	     
    /**
     * This method generates random string
     * @return
     */
    public String generateRandomString(){
         
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
	    
	
	public String textEmailNovaSenha(Usuario usu,String nova_senha){
		String texto ="<html>"
				+"<body style='width: 500px;'>"
	
				+"<p>Olá, <strong>"+usu.getNome()+"</strong>, tudo bem?</p>"

		+"<p></p>"
	
		+"<p>Foi solicitado uma nova senha através do aplicativo InBanker com o seu CPF.</p>"
		+"<p>Nova senha:<strong>"+ nova_senha +"</strong</p>"
		
		+"<p></p>"
		+"<p>Recomendamos ao entrar no aplicativo, por questões de segurança, alterar a nova senha gerada.</p>"
		
		
		+"<div style='border: solid gray;margin-top: 50px;padding: 5px'>"
			+"<img src='https://s10.postimg.org/oclpysywp/Captura_de_Tela_2017_03_20_s_10_51_33.png' style='float: left; width:40px;padding:10px'>"
			+"<p>Mantenha sempre seus dados em segurança, não revele seus dados de acesso à ninguém. Nós nunca pediremos sua senha. Caso suspeite que alguém esteja usando sua conta, entre em contato conosco imediatamente.</p>"
		+"</div>"
		+"<p style='color: gray;font-size: 12x' align='center'>Esse email é automático e não deve ser respondido</p>"
		+"</body>"
		+"</html>";
		
		return texto;
	}
	
	public static String md5(String senha){
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		sen = hash.toString(16);			
		return sen;
	}
}
