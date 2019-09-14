package Data;

import java.util.Random;

public class UserData { //Класс данных о пользователе
    private final Long telephone;
    private final String login;

    public UserData(Long telephone, String login){
        this.telephone = telephone;
        this.login = login;
    }

    public int sentPassword(){ //высылка пароля на телефон
        //здесь происходит высылка пароля на телефон
        return new Random().nextInt();
    }

}
