package com.belkov.mobileappservice.dao;


import com.belkov.mobileappservice.model.User;
import com.belkov.mobileappservice.repository.DataSource;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.UUID;

@Repository("userDao")
public class UserDataAccessService implements UserDao {


    @Override
    public User loginUser(User user) {

        if (!loginAvailabilityCheck(user)) {
            user.setError("Invalid login");
            return user;
        }

        if (!passwordCheck(user)) {
            user.setError("Invalid password");
            return user;
        }

        return getToken(user);
    }

    @Override
    public User registerUser(User user) {
        UUID uuid = UUID.randomUUID();

        if (loginAvailabilityCheck(user)) {
            user.setError("Such user exist");
            return user;
        } else user.setUuid(uuid);


        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "Insert into USERS (uuid, login, password) " +
                             "values ('" + user.getUuid() + "', '" + user.getLogin() + "', '" + user.getPassword() + "');"
             )) {
            pst.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return user;
    }

    private boolean loginAvailabilityCheck(User user) {
        boolean userExist;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select login " +
                             "from users " +
                             "where login = '" + user.getLogin() + "';");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            userExist = resultSet.next();
            return userExist;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return true;
        }
    }

    private boolean passwordCheck(User user) {
        boolean correctPassword;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select login " +
                             "from users " +
                             "where login = '" + user.getLogin() + "' AND password = '" + user.getPassword() + "';");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            correctPassword = resultSet.next();
            return correctPassword;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return true;
        }
    }

    private User getToken(User user) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select uuid " +
                             "from users " +
                             "where login = '" + user.getLogin() + "';");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                user.setUuid(UUID.fromString(resultSet.getString(1)));
            }
            return user;
        } catch (SQLException exc) {
            exc.printStackTrace();
            user.setError("DataBase exception");
            return user;
        }
    }
}
