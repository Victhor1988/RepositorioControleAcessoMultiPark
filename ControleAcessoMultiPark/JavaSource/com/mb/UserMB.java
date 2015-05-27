package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.FilialFacade;
import com.facade.UserFacade;
import com.model.Filial;
import com.model.Role;
import com.model.User;

@SessionScoped
@ManagedBean(name="userMB")
public class UserMB extends AbstractMB implements Serializable {

	public static final String INJECTION_NAME = "#{userMB}";
	private static final long serialVersionUID = 1L;

	private User user;
	private User usuario;
	private Filial filial;
	private List<Filial> listaFilial;
	private List<User> listaUsuarios;
	private Integer perfil;
	
	private UserFacade usuarioFacade;
	private FilialFacade filialFacade;

	public UserFacade getUsuarioFacade() {
		if (usuarioFacade == null) {
			usuarioFacade = new UserFacade();
		}

		return usuarioFacade;
	}
	
	public FilialFacade getFilialFacade() {
		if (filialFacade == null) {
			filialFacade = new FilialFacade();
		}

		return filialFacade;
	}
	
	
	public boolean isAdmin() {
		return user.isAdmin();
	}

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public User getUsuario() {
		if (usuario == null) {
			usuario = new User();
		}

		return usuario;
	}
	
 
    public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void criarUsuario() {
		try {
			if(filial != null && usuario != null){
				usuario.setFilial_id(filial);
				usuario.setPassword("000000");
				
				if(perfil != null){
					if(perfil.equals(1)){
					usuario.setRole(Role.ADMIN);
					}else{
						usuario.setRole(Role.USER);
					}
				}
			
				getUsuarioFacade().criarUsuario(usuario);
				closeDialog();
				displayInfoMessageToUser("Usuario cadastrado com Sucesso!");
				carregarUsuario();
				resetarUsuario();
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao criar um usuário. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void alterarUsuario() {
		try {
			getUsuarioFacade().alterarUsuario(usuario);
			closeDialog();
			displayInfoMessageToUser("Usuario alterado com sucesso!");
			carregarUsuario();
			resetarUsuario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao alterar um usuário. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void deletarUsuario() {
		try {
			getUsuarioFacade().deletarUsuario(usuario);
			closeDialog();
			displayInfoMessageToUser("Usuario apagado com sucesso!");
			carregarUsuario();
			resetarUsuario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema ao deletar um usuário. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}

	public List<User> getAllUsuarios() {
		if (listaUsuarios == null) {
			carregarUsuario();
		}

		return listaUsuarios;
	}

	private void carregarUsuario() {
		listaUsuarios = getUsuarioFacade().listarTodosUsuarios();
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

	public void resetarUsuario() {
		usuario = new User();
		filial = new Filial();
	}
	
	public void resetarUser() {
		user = new User();
	}
	
	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((listaUsuarios == null) ? 0 : listaUsuarios.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result
				+ ((usuarioFacade == null) ? 0 : usuarioFacade.hashCode());
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
		UserMB other = (UserMB) obj;
		if (listaUsuarios == null) {
			if (other.listaUsuarios != null)
				return false;
		} else if (!listaUsuarios.equals(other.listaUsuarios))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (usuarioFacade == null) {
			if (other.usuarioFacade != null)
				return false;
		} else if (!usuarioFacade.equals(other.usuarioFacade))
			return false;
		return true;
	}
	
	
	
}