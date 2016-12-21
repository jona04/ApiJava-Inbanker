package br.com.inbanker.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AloMundoServLet extends HttpServlet {

	private static final long serialVersionUID = 6477475784792402995L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		writer.print(" Ola mundo pessoal. ");
		writer.flush();
		
	}
	

	
}
