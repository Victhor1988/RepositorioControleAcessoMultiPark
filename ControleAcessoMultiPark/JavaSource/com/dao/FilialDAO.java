package com.dao;
 
import com.model.Filial;
 
public class FilialDAO extends GenericDAO<Filial> {
 
	private static final long serialVersionUID = 1L;

	public FilialDAO() {
        super(Filial.class);
    }
	
	 public void deletarFilial(Filial filial) {
		super.deletar(filial.getId(), Filial.class);
	}
 
}