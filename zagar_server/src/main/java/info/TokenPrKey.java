package info;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


public class TokenPrKey implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Date date;

    @Override
    public boolean equals(Object obj){
        if (obj instanceof TokenPrKey) {
            TokenPrKey _key = (TokenPrKey)obj;
            return id == _key.id;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (int)(id * 31 + date.hashCode() * 17);
    }
}
