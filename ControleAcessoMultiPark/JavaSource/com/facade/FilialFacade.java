package com.facade;

import java.io.Serializable;
import java.util.List;

import com.dao.FilialDAO;
import com.model.Filial;

public class FilialFacade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private FilialDAO filialDAO = new FilialDAO();

	public List<Filial> listarTodasFiliais() {
		filialDAO.beginTransaction();
		List<Filial> list = filialDAO.listarTodos();
		filialDAO.closeTransaction();
		return list;
	}
	
	public void criarFilial(Filial filial) {
		filialDAO.beginTransaction();
		filialDAO.salvar(filial);
		filialDAO.commitAndCloseTransaction();
	}

	public void alterarFilial(Filial filial) {
		filialDAO.beginTransaction();
		Filial filialBanco = filialDAO.obter(filial.getId());
		filialBanco.setId(filial.getId());
		filialBanco.setNome(filial.getNome());
		filialDAO.alterar(filialBanco);
		filialDAO.commitAndCloseTransaction();
	}

	public Filial obterFilial(int id) {
		filialDAO.beginTransaction();
		Filial filial = filialDAO.obter(id);
		filialDAO.closeTransaction();
		return filial;
	}

	public void deletarFilial(Filial filial) {
		filialDAO.beginTransaction();
		Filial filialBanco = filialDAO.findReferenceOnly(filial.getId());
		filialDAO.deletarFilial(filialBanco);
		filialDAO.commitAndCloseTransaction();
	}

}