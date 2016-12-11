package dao;

import jersey.repackaged.com.google.common.base.Joiner;
import info.Token;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class TokenDao implements Dao<Token>{
    @Override
    public List<Token> getAll(){
        return Database.selectTransactional(session -> session.createQuery("from Token").list());
    }

    @Override
    public List<Token> getAllWhere(String... hqlConditions){
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Token where "
                + totalCondition).list());

    }

    @Override
    public void insert(Token token){
        Database.doTransactional(session -> session.save(token));
    }

    public void delete(@NotNull Long id){
        Database.doTransactional(session -> session.createQuery("DELETE Token WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    public void updateName(@NotNull Long id, String newName) throws IllegalArgumentException {
        List<Token> checkNewName = getAllWhere("username = '" + newName + "'");
        if(checkNewName.isEmpty()) {
            Database.doTransactional(session ->
                    session.createQuery("UPDATE Token SET username = :newName WHERE id = :id")
                            .setParameter("id", id)
                            .setParameter("newName", newName)
                            .executeUpdate());
        } else {
            throw (new IllegalArgumentException());
        }
    }

    public boolean tokenExists(@NotNull Long id){
        try {
            List<Token> token = getAllWhere("id = '" + id + "'");
            return !token.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
