package com.facade;

import java.util.List;

import com.dao.UserDAO;
import com.model.User;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String email, String password) {
		userDAO.beginTransaction();
		User user = userDAO.findUserByEmail(email);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}
	
	public User resetarSenha(String email, String password, String newpassword) {
		userDAO.beginTransaction();
		User user = userDAO.findUserByEmail(email);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}else{
			user.setPassword(newpassword);
			user = alterarUsuario(user);
		}

		return user;
	}
	
	public void criarUsuario(User usuario) {
		userDAO.beginTransaction();
		userDAO.salvar(usuario);
		userDAO.commitAndCloseTransaction();
	}

	public User alterarUsuario(User usuario) {
		userDAO.beginTransaction();
		User usuarioBanco = userDAO.obter(usuario.getId());
		usuarioBanco.setName(usuario.getName());
		usuarioBanco.setEmail(usuario.getEmail());
		usuarioBanco.setFilial_id(usuario.getFilial_id());
		usuarioBanco.setPassword(usuario.getPassword());
		userDAO.alterar(usuarioBanco);
		userDAO.commitAndCloseTransaction();
		return usuario;
	}

	public User obterUsuario(int id) {
		userDAO.beginTransaction();
		User usuario = userDAO.obter(id);
		userDAO.closeTransaction();
		return usuario;
	}

	public List<User> listarTodosUsuarios() {
		userDAO.beginTransaction();
		List<User> list = userDAO.listarTodos();
		userDAO.closeTransaction();
		return list;
	}

	public void deletarUsuario(User usuario) {
		userDAO.beginTransaction();
		User usuarioBanco = userDAO.findReferenceOnly(usuario.getId());
		userDAO.deletarUsuario(usuarioBanco);
		userDAO.commitAndCloseTransaction();
	}

}