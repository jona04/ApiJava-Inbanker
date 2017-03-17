package br.com.inbanker.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.inbanker.config.Conexao;
import br.com.inbanker.dao.UsuarioDAO;
import br.com.inbanker.entidades.NotificacaoContrato;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;
import br.com.inbanker.gcm.GcmDao;

public class SchedulerTest implements Job {
	
	private GcmDao gcm = new GcmDao();
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//System.out.println("JSF 2 + Quartz 2 example");

		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format_vencimento = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		SimpleDateFormat format_data_pedido = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		Date hoje = Calendar.getInstance().getTime();
		System.out.println("hoje" +hoje);
		
		List<Usuario> list = listUser();
		List<Transacao> list_trans = new ArrayList<Transacao>();
		
		for (Usuario list_user : list) {
			
			System.out.println("cpf" +list_user.getCpf());
			
			if(list_user.getTransacoes_enviadas()!=null){
				for (Transacao list_result : list_user.getTransacoes_enviadas()) {
					
					if(list_result.getStatus_transacao().equals(String.valueOf(Transacao.CONFIRMADO_RECEBIMENTO)) ||
							list_result.getStatus_transacao().equals(String.valueOf(Transacao.RESP_QUITACAO_SOLICITADA_RECUSADA))
							){
					
						//list_trans.add(list_result);
						
						try {
							
							
							Instant instant = Instant.now();
							ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Sao_Paulo");
							ZonedDateTime zdt = ZonedDateTime.ofInstant(instant,fusoHorarioDeSaoPaulo);
							
							//acrescentamos o "Z" para ficar no formato padrao UTC do android e nao dar problema na conversao
							String data_hoje_string = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"Z";
							
							
							String str_vencimento = list_result.getVencimento();
							String str_data_pedido = list_result.getData_pedido();
							
							Date date_vencimento = format_vencimento.parse(str_vencimento);
							//adicionamos 3 horas na data para ficar igual ao banco de dados
							//pois por algum motivo a hora esta retornando 21:00, ao invé de 00:00
							//alterando o dia do vencimento para 1 dia a menos
							Calendar gc = new GregorianCalendar();
							gc.setTime(date_vencimento);
							gc.add(Calendar.HOUR, 3);
							date_vencimento = gc.getTime();
							
							Date date_pedido = format_data_pedido.parse(str_data_pedido);
							
							int dias = daysBetweenJoda(date_pedido,date_vencimento);
							
				            System.out.println("date_vencimento" +date_vencimento);
				            System.out.println("date_pedido" +date_pedido);
				            System.out.println("dias" +dias);
							
				            String mensagem = "";
				            String titulo = "";
				            titulo = "Atenção";
			            	//verifica quantos dias faltam para vencimento
			            	int dias_para_vencimento = daysBetweenJoda(hoje,date_vencimento);
			            	System.out.println("dias vencimento = " +dias_para_vencimento);
			            	if(dias_para_vencimento == 9){
			            		
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo está em andamento. Faltam 9 dias para o vencimento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            		
			            	}else if(dias_para_vencimento == 5){
			            		//mensagem de que falta 5 dias para vencimento
			            		
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo está em andamento. Faltam 5 dias para o vencimento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            		
			            		
			            	}else if(dias_para_vencimento == 3){
			            		//mensagem de que falta 9 dias para vencimento
			            		
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo está em andamento. Faltam 3 dias para o vencimento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            		
			            	}else if(dias_para_vencimento == 0){
			            		//mensagem de que falta 9 dias para vencimento
			            		
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo vencerá hoje, faça o pagamento e evite multa e juros moratórios.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            		
			            		
			            	}else if(dias_para_vencimento == -3){
			            		
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 3 dias, seu amigo "+list_result.getNome_usu2()+" está à espera do pagamento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            		
			            	}else if(dias_para_vencimento == -5){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 5 dias, seu amigo "+list_result.getNome_usu2()+" está à espera do pagamento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -7){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 7 dias, seu amigo "+list_result.getNome_usu2()+" está à espera do pagamento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -10){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 10 dias, seu amigo "+list_result.getNome_usu2()+" está à espera do pagamento.";
			            		
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -15){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 15 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -20){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 20 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -21){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 21 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -25){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 25 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -29){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 29 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -30){
			            		mensagem = "Olá "+list_user.getNome()+", seu empréstimo venceu à 30 dias, não perca o controle financeiro, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -35){
			            		mensagem = "Olá "+list_user.getNome()+", já passaram 35 dias do vencimento, evite a negativação do seu nome, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -40){
			            		mensagem = "Olá "+list_user.getNome()+", já passaram 40 dias do vencimento, evite a negativação do seu nome, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}else if(dias_para_vencimento == -45){
			            		mensagem = "Olá "+list_user.getNome()+", já passaram 45 dias do vencimento, evite a negativação do seu nome, quite seu contrato.";
			            		//mensagem de que falta 9 dias para vencimento
			            		mensagemGcm(list_result.getNome_usu2(),list_user.getToken_gcm(),list_result);
			            		
			            		//add no banco a mensagem de notificacao
			            		NotificacaoContrato nc = new NotificacaoContrato();
			            		nc.setCpf_user(list_user.getCpf());
			            		nc.setDate(data_hoje_string);
			            		nc.setMensagem(mensagem);
			            		nc.setDias(String.valueOf(dias_para_vencimento));
			            		nc.setTitulo(titulo);
			            		nc.setId_trans(list_result.getId_trans());
			            		UsuarioDAO usu_dao = new UsuarioDAO();
			            		usu_dao.addNotificacaoContrato(nc);
			            	}
							
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
			}
			
		}
		
	}
	
	public void mensagemGcm(String nome_user2,String token_user1,Transacao trans){
		System.out.println("envio mensagem");
		String message = "Voce esta devendo o(a) "+nome_user2;
		String title = "Voce tem uma conta em atraso";
		
		try{
			String resposta = gcm.sendAndroidNotification("divida",trans,token_user1, message, title);
			System.out.println("resposta = "+resposta);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<Usuario> listUser() {
        Datastore datastore = Conexao.abrirConexao();
  
        Query<Usuario> query = datastore.find(Usuario.class);
        List<Usuario> listProjeto = query.asList();
  
        Conexao.fecharConexao(datastore);
        return listProjeto;
      }
	
	public static int daysBetweenJoda(Date date1, Date date2){
		
		return Days.daysBetween(
				new LocalDate(date1.getTime()),
						new LocalDate(date2.getTime())).getDays();
		
	}
}

