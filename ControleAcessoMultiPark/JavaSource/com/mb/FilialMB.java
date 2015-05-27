package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.facade.FilialFacade;
import com.model.Filial;

@SessionScoped
@ManagedBean(name="filialMB")
public class FilialMB extends AbstractMB implements Serializable {

	public static final String INJECTION_NAME = "#{filialMB}";
	private static final long serialVersionUID = 1L;

	private Filial filial;
	private List<Filial> listaFilial;
	private FilialFacade filialFacade;

	public FilialFacade getFilialFacade() {
		if (filialFacade == null) {
			filialFacade = new FilialFacade();
		}

		return filialFacade;
	}
	
	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getAllFilial() {
		if (listaFilial == null) {
			carregarFilial();
		}

		return listaFilial;
	}

	private void carregarFilial() {
		listaFilial = getFilialFacade().listarTodasFiliais();
	}
	
	public void criarFilial() {
		try {
			getFilialFacade().criarFilial(filial);
			closeDialog();
			displayInfoMessageToUser("Filial cadastrado com Sucesso!");
			carregarFilial();
			resetarFilial();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao criar uma filial. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void alterarFilial() {
		try {
			getFilialFacade().alterarFilial(filial);
			closeDialog();
			displayInfoMessageToUser("Filial alterado com sucesso!");
			carregarFilial();
			resetarFilial();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao alterar uma filial. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void deletarFilial() {
		try {
			getFilialFacade().deletarFilial(filial);
			closeDialog();
			displayInfoMessageToUser("Filial apagado com sucesso!");
			carregarFilial();
			resetarFilial();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao deletar uma filial. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void resetarFilial() {
		filial = new Filial();
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result
				+ ((filialFacade == null) ? 0 : filialFacade.hashCode());
		result = prime * result
				+ ((listaFilial == null) ? 0 : listaFilial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilialMB other = (FilialMB) obj;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (filialFacade == null) {
			if (other.filialFacade != null)
				return false;
		} else if (!filialFacade.equals(other.filialFacade))
			return false;
		if (listaFilial == null) {
			if (other.listaFilial != null)
				return false;
		} else if (!listaFilial.equals(other.listaFilial))
			return false;
		return true;
	}
	

}