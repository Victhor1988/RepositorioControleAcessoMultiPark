package com.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.model.User;
 
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
	
	 public User findUserByEmailJdbc(String email){
		 Connection conexao = null;
		 PreparedStatement ps = null;
		 ResultSet rs = null;

		 try {
			 	String sql = "select * from users u where u.email = ?";
			 	conexao = createConnection();
			 	ps = conexao.prepareStatement(sql);
				ps.setString(1, email);
				
				rs = ps.executeQuery();
				
			 	while (rs.next()) {
			 		 System.out.println(rs.getString("email"));
		 		}

		} catch (SQLException e ) {
			System.err.print("Rollback efetuado na transação");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
		return null;
	 }
 
    public User findUserByEmail(String email){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", email);     
 
        return super.listar(User.FIND_BY_EMAIL, parameters);
    }
    
    public void deletarUsuario(User user) {
		super.deletar(user.getId(), User.class);
	}
}