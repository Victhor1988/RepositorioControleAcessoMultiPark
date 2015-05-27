package com.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.UserFacade;
import com.model.Filial;
import com.model.Role;
import com.model.User;

@ViewScoped
@ManagedBean
public class LoginMB extends AbstractMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String email;
	private String password;
	private boolean habilitaNovaSenha;
	private String newpassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		UserFacade userFacade = new UserFacade();

		User user = userFacade.isValidLogin(email, password);
		
		//User user = userMock();
		
		if(user != null){
			if(newpassword == null && user.getPassword().equals("000000")){
				this.habilitaNovaSenha = true;
				displayErrorMessageToUser("Favor redefinir sua senha.");
				//return "/pages/public/login.xhtml?faces-redirect=true";
			}else{
				if(newpassword != null){
					user = userFacade.resetarSenha(email, password, newpassword);
				}
				userMB.setUser(user);
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				request.getSession().setAttribute("user", user);
				this.habilitaNovaSenha = false;
				return "/pages/protected/index.xhtml?faces-redirect=true";
			}
		}else{
			displayErrorMessageToUser("Email/senha inválido.");
		}
		
		return null;
	}

	private User userMock() {
		User user = new User();
		user.setEmail("luizh.ucb@gmail.com");
		user.setId(1);
		user.setName("luiz");
		user.setPassword("123456");
		user.setRole(Role.USER);
		Filial filial = new Filial();
		filial.setCidade("Brasilia");
		filial.setNomeArquivo("RELATORIO_OPERACIONAL_ALAMEDA.xlsx");
		filial.setUf("DF");
		user.setFilial_id(filial);
		return user;
	}
	
	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public boolean isHabilitaNovaSenha() {
		return habilitaNovaSenha;
	}

	public void setHabilitaNovaSenha(boolean habilitaNovaSenha) {
		this.habilitaNovaSenha = habilitaNovaSenha;
	}
	
}