package dao;

import jersey.repackaged.com.google.common.base.Joiner;
import info.User;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class UserDao implements Dao<User> {
    @Override
    public List<User> getAll(){
        return Database.selectTransactional(session -> session.createQuery("from User").list());
    }

    @Override
    public List<User> getAllWhere(String... hqlConditions){
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from User where "
                + totalCondition).list());

    }

    @Override
    public void insert(User user){
        Database.doTransactional(session -> session.save(user));
    }

    public void delete(@NotNull String userName){
        Database.doTransactional(session -> session.createQuery("DELETE User WHERE name = :name")
                .setParameter("name", userName)
                .executeUpdate());
    }

    public void updateName(@NotNull String userName, String newName) throws IllegalArgumentException{
        List<User> checkNewName = getAllWhere("name = '" + newName + "'");
        if(checkNewName.isEmpty()) {
            Database.doTransactional(session ->
                    session.createQuery("UPDATE User SET name = :newName WHERE name = :oldname")
                            .setParameter("oldname", userName)
                            .setParameter("newName", newName)
                            .executeUpdate());
        } else {
            throw (new IllegalArgumentException());
        }
    }

    public void updateEmail(@NotNull String userName, @NotNull String new_email) {
        Database.doTransactional(session ->
                session.createQuery("UPDATE User SET email = :new_email WHERE name = :name")
                        .setParameter("name", userName)
                        .setParameter("new_email", new_email)
                        .executeUpdate());
    }

    public void updatePassword(@NotNull String userName, @NotNull String new_password) {
        Database.doTransactional(session ->
                session.createQuery("UPDATE User SET password = :new_password WHERE name = :name")
                        .setParameter("name", userName)
                        .setParameter("new_password", new_password)
                        .executeUpdate());
    }

    public boolean passwordIsTrue(@NotNull String userName, @NotNull String password){
        try {
            List<User> user = getAllWhere("name = '" + userName + "'");
            return password.equals(user.get(0).getPassword());
        } catch (Exception e) {
            return false;
        }
    }
}
