package web;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import dao.CarroDAO;
import entity.Carro;


@ManagedBean (name="carroMBean")
@SessionScoped
public class CarroMBean {
	/** Variáveis acessadas nas páginas. */
	private Carro novo = new Carro();

	/** Variáveis acessadas nas páginas. */
	private Carro selecionado;
	
	private List<Carro> lista;
	
	private CarroDAO dao = new CarroDAO();

	public CarroMBean() {
		
	}
	
	/**
	 * Processamento do Login.
	 * @return retorna 
	 */
	public String incluirAction() {
		try {
			dao.inserir(novo);
		} catch (Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        ex.getMessage(), "Erro ao Incluir um Carro"));
	        return "";
		}
		
		// Limpar a pessoa selecionada
		novo = new Carro();
		return "consultar.xhtml";
	}


	/**
	 * Processamento do Login.
	 * @return retorna 
	 */
	public String alterarPage(int id) {
		try {
			selecionado = dao.getCarro(id);
		} catch (Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        ex.getMessage(), "Erro ao Alterar um Carro"));
	        return "";
		}
		
		return "atualizar.xhtml";
	}
	
	public String alterarAction() {
		try {
			dao.atualizar(selecionado);
		} catch (Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        ex.getMessage(), "Erro ao Alterar um Carro"));
	        return "";
		}
		
		return "consultar.xhtml";
	}

	/**
	 * Retirar do banco a pessoa selecionada
	 * @return PessoaListar se ok.
	 */
	public String excluirAction(int id) {
		try {
			selecionado = dao.getCarro(id);
			dao.excluir(selecionado);
		} catch (Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        ex.getMessage(), "Erro ao excluir um Carro"));
	        return "";
		}
		return "consultar.xhtml";
	}

	

	public List<Carro> getLista() {
		try {
			lista = dao.listAll();
		} catch (Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        ex.getMessage(), "Erro ao pegar a Lista de Carros"));
		}
	
		return lista;
	}

	public Carro getNovo() {
		return novo;
	}

	public void setNovo(Carro novo) {
		this.novo = novo;
	}

	public Carro getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Carro selecionado) {
		this.selecionado = selecionado;
	}

	public CarroDAO getDao() {
		return dao;
	}

	public void setDao(CarroDAO dao) {
		this.dao = dao;
	}

	public void setLista(List<Carro> lista) {
		this.lista = lista;
	}
}
