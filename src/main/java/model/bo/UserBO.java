package model.bo;
import model.bean.User;
import model.dao.UserDAO;

public class UserBO {
    UserDAO userDAO = new UserDAO();
    
    public User checkLogin(String username, String password) {
        return userDAO.checkLogin(username, password);
    }
    
    // Thêm 2 hàm mới
    public boolean checkUserExist(String username) {
        return userDAO.checkUserExist(username);
    }
    
    public boolean registerUser(String username, String password, String fullname) {
        return userDAO.insertUser(username, password, fullname);
    }
}