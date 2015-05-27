package com.converter;

import javax.faces.convert.FacesConverter;

@FacesConverter("genericConverter")
public class GenericConverter //implements Converter, Serializable 
{

	private static final long serialVersionUID = 1L;

//	@Override
//    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
//        if (value != null && !value.isEmpty()) {
//            return (Filial) uiComponent.getAttributes().get(value);
//        }
//        return null;
//    }
//
//    @Override
//    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
//        if (value instanceof Filial) {
//        	Filial filial = (Filial) value;
//            if (filial != null && filial instanceof Filial && filial.getId() != "") {
//                uiComponent.getAttributes().put( filial.getId().toString(), filial);
//                return filial.getId();
//            }
//        }
//        return "";
//    }

}
