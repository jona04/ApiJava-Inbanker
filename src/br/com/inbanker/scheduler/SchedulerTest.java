package br.com.inbanker.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.inbanker.config.Conexao;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;
import br.com.inbanker.gcm.GcmDao;

public class SchedulerTest implements Job {
	
	private GcmDao gcm = new GcmDao();
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//System.out.println("JSF 2 + Quartz 2 example");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date hoje = Calendar.getInstance().getTime();
		
		List<Usuario> list = listUser();
		
		for (Usuario list_user : list) {
			
			List<Transacao> list_trans = list_user.getTransacoes_enviadas();
			
			for (Transacao list_result : list_trans) {
				if(list_result.getStatus_transacao().equals(String.valueOf(Transacao.CONFIRMADO_RECEBIMENTO)) ||
					list_result.getStatus_transacao().equals(String.valueOf(Transacao.RESP_QUITACAO_SOLICITADA_RECUSADA))
					){
					//System.out.println("Depois = "+list_result.getId_trans());
					try {
						
						Date date = formatter.parse(list_result.getVencimento());
			            //System.out.println(hoje +"--"+date);
						
						if(hoje.after(date)){
							System.out.println("Depois = "+list_result.getNome_usu1()+"-"+list_result.getVencimento());
							
							String message = "Voce esta devendo o(a) "+list_result.getNome_usu2();
							String title = "Voce tem uma conta em atraso";
							
							gcm.sendAndroidNotification("divida",list_result,list_user.getToken_gcm(), message, title);
							
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
				}
				
			}
		}
		
		
	}
	
	public List<Usuario> listUser() {
        Datastore datastore = Conexao.abrirConexao();
  
        Query<Usuario> query = datastore.find(Usuario.class);
        List<Usuario> listProjeto = query.asList();
  
        //Conexao.fecharConexao(datastore);
        return listProjeto;
      }
}
