package br.com.inbanker.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import br.com.inbanker.dao.UsuarioDAO;
import br.com.inbanker.email.SendEmail;
import br.com.inbanker.entidades.Contrato;
import br.com.inbanker.entidades.Recibo;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;

@Path("/usuario")
public class UsuarioService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private UsuarioDAO daousuario = new UsuarioDAO();
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Usuario> listarUsuarios() {
		List<Usuario> lista = null;
		try {
			lista = daousuario.listUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/findEmail/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorEmail(@PathParam("email") String email) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUserEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@GET
	@Path("/findUsuarioCpf/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario findUsuarioCpf(@PathParam("cpf") String cpf) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUsuarioCpf(cpf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@GET
	@Path("/verificaUsuarioCadastro/{cpf}/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String verificaUsuarioCadastro(@PathParam("cpf") String cpf,@PathParam("email") String email) {
		String result = null;
		try {
			result = daousuario.verificaUsuarioCadastro(cpf,email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@GET
	@Path("/verificaEmailCadastro/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String verificaEmailCadastro(@PathParam("email") String email) {
		String result = null;
		try {
			result = daousuario.verificaEmailCadastro(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@GET
	@Path("/verificaCPFCadastro/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String verificaCPFCadastro(@PathParam("cpf") String cpf) {
		String result = null;
		try {
			result = daousuario.verificaCPFCadastro(cpf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@GET
	@Path("/verificaIdFace/{id_face}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String verificaIdFace(@PathParam("id_face") String id_face) {
		String result = null;
		try {
			result = daousuario.verificaIdFace(id_face);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	@GET
	@Path("/findCpfTransEnv/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorCpfEnv(@PathParam("cpf") String cpf) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUserCpfTransEnv(cpf);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@GET
	@Path("/findCpfTransRec/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorCpfRec(@PathParam("cpf") String cpf) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUserCpfTransRec(cpf);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@GET
	@Path("/findCpfTransHistorico/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorCpfHistorico(@PathParam("cpf") String cpf) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUserCpfTransHistorico(cpf);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@GET
	@Path("/findFace/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorIdFace(@PathParam("id") String id_face) {
		Usuario usuario = null;
		
		try {
			usuario = daousuario.findUserFace(id_face);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}
	
	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addUsu(Usuario usu) { 
		String msg= "";
		//System.out.println("teste id = "+usu.getId_face());
		
		try {
			
			usu.setAdicionado_em(obterHora());
			
			daousuario.incluirUsuario(usu);
			
			msg = "sucesso";
			
		} catch (Exception e) {
			msg = "Erro ao add a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@POST
	@Path("/editSenhaByCPF/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarSenhabyCPF(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println(usu.getNome() +" - "+usu.getId_face() +" -  "+cpf);
		
		try {
			daousuario.editarSenhaByCPF(usu, cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
	@POST
	@Path("/editEnderecoByCPF/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarEnderecobyCPF(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println(usu.getNome() +" - "+usu.getId_face() +" -  "+cpf);
		
		try {
			daousuario.editarEnderecoByCPF(usu, cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
	@POST
	@Path("/editUserByCPF/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarUsuariobyCPF(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println(usu.getNome() +" - "+usu.getId_face() +" -  "+cpf);
		
		try {
			daousuario.editarUsuarioByCPF(usu, cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@POST
	@Path("/editUserByCPFFace/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarUsuariobyCPFFace(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println(usu.getNome() +" - "+usu.getId_face() +" -  "+cpf);
		
		try {
			daousuario.editarUsuarioByCPFFace(usu, cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@POST
	@Path("/editUserByFace/{id_face}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarUsuariobyFace(Usuario usu, @PathParam("id_face") String id_face) {
		String msg = "";
		
		//System.out.println("id_face ="+id_face);
		
		try {
			daousuario.editarUsuarioByFace(usu, id_face);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
	/*@POST
	@Path("/deletaUsuFace/{id_face}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletaUsuFace(Usuario usu, @PathParam("id_face") String id_face) {
		String msg = "";
		
		//System.out.println("id_face ="+id_face);
		
		try {
			daousuario.deletaUsuFace(usu, id_face);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	*/
	
	@POST
	@Path("/addTransacao/{user1}/{user2}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addTransacao(Transacao usu, @PathParam("user1") String cpf_user1,@PathParam("user2") String cpf_user2) {
		String msg = "";
		
		//System.out.println("mensagem para nos = "+usu.getUsu1());

		try {
			daousuario.addTransacao(usu, cpf_user1,cpf_user2);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
	@POST
	@Path("/editTransacao/{user1}/{user2}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarTransacao(Transacao trans, @PathParam("user1") String cpf_user1,@PathParam("user2") String cpf_user2) {
		String msg = "";
		
		System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			
			if(trans.getStatus_transacao().equals("3")){

				//recibo de confirmacao vai ser o mesmo contrato
				
				//add contrato
				Contrato contrato = new Contrato();
				contrato.setId_trans(trans.getId_trans());
				contrato.setCpf_usu1(trans.getCpf_usu1());
				contrato.setCpf_usu2(trans.getCpf_usu2());
				contrato.setValor(trans.getValor());
				contrato.setVencimento(trans.getVencimento());
				contrato.setData_pedido(trans.getData_pedido());
				contrato.setData_confirma_recebimento(trans.getHistorico().get(trans.getHistorico().size()-1).getData());
				contrato.setNome_usu2(trans.getNome_usu2());
				contrato.setNome_usu1(trans.getNome_usu1());
				String id_contrato = daousuario.addContrato(contrato);
				
				trans.setId_contrato(id_contrato);
				
				daousuario.editarTransacao(trans, cpf_user1,cpf_user2);
				
				msg = id_contrato;
				
			}else{
				daousuario.editarTransacao(trans, cpf_user1,cpf_user2);
				
				msg = "sucesso_edit";
			}
		} catch (Exception e) {
			msg = "error_edit_trans";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	//para editar a data em que a transacao foi cancelada ou finalizada
	@POST
	@Path("/editTransacaoResposta/{user1}/{user2}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarTransacaoResposta(Transacao trans, @PathParam("user1") String cpf_user1,@PathParam("user2") String cpf_user2) {
		String msg = "";
		
		System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			
			if(trans.getStatus_transacao().equals("6")){
				
				//add recibo confirmacao
				Recibo recibo = new Recibo();
				recibo.setId_trans(trans.getId_trans());
				recibo.setCpf_usu1(trans.getCpf_usu1());
				recibo.setCpf_usu2(trans.getCpf_usu2());
				recibo.setValor(trans.getValor());
				recibo.setValor_servico(trans.getValor_servico());
				recibo.setValor_juros_mensal(trans.getValor_juros_mensal());
				recibo.setValor_juros_mora(trans.getValor_juros_mora());
				recibo.setValor_multa(trans.getValor_multa());
				recibo.setVencimento(trans.getVencimento());
				recibo.setData_pedido(trans.getData_pedido());
				recibo.setData_confirma_quitacao((trans.getHistorico().get(trans.getHistorico().size()-1).getData()));
				recibo.setNome_usu2(trans.getNome_usu2());
				recibo.setNome_usu1(trans.getNome_usu1());
				String id_recibo = daousuario.addRecibo(recibo);
				
				trans.setId_recibo(id_recibo);
				
				daousuario.editarTransacaoResposta(trans, cpf_user1,cpf_user2);
				
				msg = id_recibo;
				
			}else{
				daousuario.editarTransacaoRespostaCancela(trans, cpf_user1,cpf_user2);
				
				msg = "sucesso_edit";
			}
		} catch (Exception e) {
			msg = "error_edit_trans";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@POST
	@Path("/addCartaoUsuario/{user1}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addCartaoUsuario(Usuario usuario, @PathParam("user1") String cpf_user1) {
		String msg = "";
		
		//System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			daousuario.addCartaoUsuario(usuario, cpf_user1);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@POST
	@Path("/updateTokenGcm/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateTokenGcm(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			daousuario.updateTokenGcm(usu,cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@GET
	@Path("/obterHora")
	@Produces(MediaType.TEXT_PLAIN)
	public String obterHora() {
		String msg = "";
		
		//System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			
			Instant instant = Instant.now();
			ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Sao_Paulo");
			ZonedDateTime zdt = ZonedDateTime.ofInstant(instant,fusoHorarioDeSaoPaulo);
			
			/*System.out.println("BASIC_ISO_DATE: \t" + zdt.format(DateTimeFormatter.BASIC_ISO_DATE));
	        System.out.println("ISO_LOCAL_DATE: \t" + zdt.format(DateTimeFormatter.ISO_LOCAL_DATE));
	        System.out.println("ISO_OFFSET_DATE: \t" + zdt.format(DateTimeFormatter.ISO_OFFSET_DATE));
	        System.out.println("ISO_DATE: \t\t" + zdt.format(DateTimeFormatter.ISO_DATE));
	        System.out.println("ISO_LOCAL_TIME: \t" + zdt.format(DateTimeFormatter.ISO_LOCAL_TIME));
	        System.out.println("ISO_OFFSET_TIME: \t" + zdt.format(DateTimeFormatter.ISO_OFFSET_TIME));
	        System.out.println("ISO_TIME: \t\t" + zdt.format(DateTimeFormatter.ISO_TIME));
	        System.out.println("ISO_LOCAL_DATE_TIME: \t" + zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));*/
			
			//acrescentamos o "Z" para ficar no formato padrao UTC do android e nao dar problema na conversao
			msg = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"Z";
			
		} catch (Exception e) {
			msg = "error";
			e.printStackTrace();
		}
		
		System.out.println("DATETIME = " + msg);
		
		return msg;
	}
	
	@PUT
	@Path("/addRecibo")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addRecibo(Recibo recibo) {
		String msg = "";
		
		try {
			msg = daousuario.addRecibo(recibo);
			
			//msg = "sucesso_edit_recibo";
		} catch (Exception e) {
			msg = "Erro ao adicionar recibo!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@PUT
	@Path("/addContrato")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addContrato(Contrato contrato) {
		String msg = "";
		
		try {
			daousuario.addContrato(contrato);
			
			msg = "sucesso_edit_contrato";
		} catch (Exception e) {
			msg = "Erro ao adicionar contrato!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
}
