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


import br.com.inbanker.dao.UsuarioDAO;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;
import br.com.inbanker.gcm.testGcm;

@Path("/usuario")
public class UsuarioService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private UsuarioDAO daousuario = new UsuarioDAO();
	private testGcm test = new testGcm();

	
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
	@Path("/gcmtest")
	@Produces(MediaType.TEXT_PLAIN)
	public String gcmTest() {
		String result = null;
		
		String deviceToken = "e7PjRhhbkW0:APA91bHxH29dmC77nmhh-oVWfo2w4vl62phOAmudQC_sMxED-XwNtYTAo_LC4rjZ97iSVPck2kXRRK_qTm8VK2rdP7awEj9hgaegdbTsAvyveiRy6KEcxXO8WG4WZDZfteCOiz5cLnCU";
		String message = "teste api android oioio";
		String title = "titulo notification";
		
		try {
			result = test.sendAndroidNotification(deviceToken, message, title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		System.out.println("teste id = "+usu.getId_face());
		
		try {
			daousuario.incluirUsuario(usu);

			msg = "sucesso";
		} catch (Exception e) {
			msg = "Erro ao add a usuario!";
			e.printStackTrace();
		}

		return msg;
	}
	
	@POST
	@Path("/edit/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarUsuario(Usuario usu, @PathParam("cpf") String cpf) {
		String msg = "";
		
		//System.out.println(usu.getNome() +" - "+usu.getId_face() +" -  "+cpf);
		
		try {
			daousuario.editarUsuario(usu, cpf);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
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
		
		//System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			daousuario.editarTransacao(trans, cpf_user1,cpf_user2);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
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
		
		//System.out.println("mensagem para nos = "+trans.getStatus_transacao());
		
		try {
			daousuario.editarTransacaoResposta(trans, cpf_user1,cpf_user2);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
}
