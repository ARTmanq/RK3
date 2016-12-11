package info;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "tokens")
@IdClass(TokenPrKey.class)
public class Token {
    @Id
    @NotNull
    private Long id;
    @Id
    @NotNull
    private Date date;
    @NotNull
    String userName;

    public Token() {}

    public Token(@NotNull Long _id){
        this.id = _id;
        date = new Date(System.currentTimeMillis());
        userName = "Unknown";
    }

    public Token(@NotNull String _userName){

        do {
            this.id = ThreadLocalRandom.current().nextLong();
        } while (AuthDataStorage.tokenExists(id));
        date = new Date(System.currentTimeMillis());
        userName = _userName;
    }

    public Long getId(){ return id; }
    public Date getDate(){return new Date(date.getTime());}
    public String getUserName(){ return userName; }
    public void setUserName(@NotNull String _name){
        userName = _name;
    }
}
