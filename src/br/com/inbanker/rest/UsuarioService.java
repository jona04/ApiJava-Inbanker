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
	@Path("/findCpf/{cpf}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorCpf(@PathParam("cpf") String cpf) {
		Usuario usuario = null;
		try {
			usuario = daousuario.findUserCpf(cpf);
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
	@Path("/editTransacao/{user1}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarTransacao(Transacao usu, @PathParam("user1") String cpf_user1) {
		String msg = "";
		
		//System.out.println("mensagem para nos = "+usu.getUsu1());
		
		try {
			daousuario.editarTransacao(usu, cpf_user1);
			
			msg = "sucesso_edit";
		} catch (Exception e) {
			msg = "Erro ao editar a usuario!";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
	
	
}
