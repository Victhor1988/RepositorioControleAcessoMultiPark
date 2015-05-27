package com.converter;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.FilialFacade;
import com.model.Filial;

@FacesConverter(value="filialConverter")
public class FilialConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2 == "") {
			return "";
		}
		
		FilialFacade filialFacade = new FilialFacade();
		int filialId = 0;

		try {
			filialId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao criar usuario.", 
					"Erro ao criar usuario."));
		}

		return filialFacade.obterFilial(filialId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null || arg2 == "") {
			return "";
		}
		Filial filial = (Filial) arg2;
		return String.valueOf(filial.getId());
	}
	
	 protected void addAttribute(UIComponent component, Filial filial) {  
	        Integer key = filial.getId(); // codigo da empresa como chave neste caso  
	        this.getAttributesFrom(component).put(key.toString(), filial);  
    }  
  
    protected Map<String, Object> getAttributesFrom(UIComponent component) {  
        return component.getAttributes();  
    } 
}
