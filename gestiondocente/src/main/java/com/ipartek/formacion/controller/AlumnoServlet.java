package com.ipartek.formacion.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.dbms.pojo.Alumno;
import com.ipartek.formacion.service.AlumnoService;
import com.ipartek.formacion.service.AlumnoServiceImp;

/**
 * Servlet implementation class AlumnoServlet
 */
public class AlumnoServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(AlumnoServlet.class);
	private static final long serialVersionUID = 1L;
    private AlumnoService aS;  
    private RequestDispatcher rd;
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		aS = new AlumnoServiceImp();
		super.init();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String operacion = req.getParameter(Constantes.PAR_OPERACION);
		int op = -1;
		try{
			op = Integer.parseInt(operacion);
			switch (op){
				case Constantes.OP_CREATE:
					rd = req.getRequestDispatcher(Constantes.JSP_FORMULARIO_ALUMNOS);
					break;
				case Constantes.OP_READ:
					cargarListaAlumnos(req);
					break;
				case Constantes.OP_UPDATE:{
					int codigo = -1;
					codigo = Integer.parseInt(req.getParameter(Constantes.PAR_CODIGO));
					Alumno alumno = aS.getById(codigo);
					rd = req.getRequestDispatcher(Constantes.JSP_FORMULARIO_ALUMNOS);
					req.setAttribute(Constantes.ATT_ALUMNO, alumno);
				}
					break;
				case Constantes.OP_DELETE:{
					int codigo = -1;
					codigo = Integer.parseInt(req.getParameter(Constantes.PAR_CODIGO));
					aS.delete(codigo);
					req.setAttribute(Constantes.ATT_MENSAJE, "el alumno a sido borrado Correctamente");
					cargarListaAlumnos(req);
				}
					
					break;
				default:
					cargarListaAlumnos(req);
					break;
			}
		} catch(Exception e){
			LOG.error(e.getMessage()+"valor del codigo del Alumno" + req.getParameter(Constantes.PAR_CODIGO));
			//cargarListaAlumnos(req);
			resp.sendRedirect(Constantes.JSP_HOME);
			return;
		}
		rd.forward(req, resp);
	}
	
	private void cargarListaAlumnos(HttpServletRequest req) {
		List<Alumno> alumnos = aS.getAll();
		rd = req.getRequestDispatcher(Constantes.JSP_LISTADO_ALUMNOS);
		req.setAttribute(Constantes.ATT_LISTADO_ALUMNOS, alumnos);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Alumno alumno = null; 
		String mensaje = "";
		int codigo =-1;
		try{
			codigo= Integer.parseInt(req.getParameter(Constantes.PAR_CODIGO));
			
			alumno = recogerParametros(req);
			alumno.setCodigo(codigo);
			if(alumno.getCodigo() > Alumno.CODIGO_NULO){
				aS.update(alumno);
				mensaje = "El alumno a sido actualizado correctamente";
			}else{
				aS.create(alumno);
				mensaje = "El alumno a sido creado correctamente";
			}
			cargarListaAlumnos(req);
		} catch (Exception e){
			if (codigo == -1){
				cargarListaAlumnos(req);
				LOG.error(e.getMessage()+"valor del codigo del Alumno" + req.getParameter(Constantes.PAR_CODIGO));
				mensaje="Se ha producido un error inesperado";
			}else{
				alumno = aS.getById(codigo);
				req.setAttribute(Constantes.ATT_ALUMNO, alumno);
				rd = req.getRequestDispatcher(Constantes.JSP_FORMULARIO_ALUMNOS);
				LOG.error(e.getMessage()+"valor del codigo del Alumno" + req.getParameter(Constantes.PAR_CODIGO));
				mensaje= e.getMessage();
			}
			//System.out.println(e.getMessage());
			
		}
		req.setAttribute(Constantes.ATT_MENSAJE, mensaje);
		rd.forward(req, resp);
	}
	private Alumno recogerParametros(HttpServletRequest req) throws Exception {
		Alumno alumno = new Alumno();
		try{
			alumno.setCodigo(Integer.parseInt(req.getParameter(Constantes.PAR_CODIGO)));
			alumno.setNombre(req.getParameter(Constantes.PAR_NOMBRE));
			alumno.setApellidos(req.getParameter(Constantes.PAR_APELLIDOS));
			alumno.setDni(req.getParameter(Constantes.PAR_DNI));
			alumno.setEmail(req.getParameter(Constantes.PAR_EMAIL));
			alumno.setDireccion(req.getParameter(Constantes.PAR_DIRECCION));
			
			
			if ("1".equals(req.getParameter(Constantes.PAR_ACTIVO))){
				alumno.setActivo(true);
			} else {
				alumno.setActivo(false);
			}
			
			String nHermanos = req.getParameter(Constantes.PAR_NHERMANOS);
			 			if (!"".equalsIgnoreCase(nHermanos)) {
			 				alumno.setnHermanos(Integer.parseInt(nHermanos));
			 			}
			
			String date = req.getParameter(Constantes.PAR_FNACIMIENTO);
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			alumno.setfNacimiento(dateFormat.parse(date));
			
		} catch(Exception e){
			LOG.error(e.getMessage()+"valor del codigo del Alumno" + req.getParameter(Constantes.PAR_CODIGO));
			throw new Exception("Los datos son incorrectos: " + e.getMessage());
			
		}
		
		return alumno;
	}
	@Override
	public void destroy() {
		aS = null;
		super.destroy();
	}
}