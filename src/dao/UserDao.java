package dao;


import entity.User;

public interface UserDao {
    void addUser(User user);
    void deleteUser(int id);
    void checkUser(User user);
}
