package casoestudio2.modelo;

public class Cajero extends Usuario {

    public Cajero(String username, String password) {
        super(username, password, "Cajero");
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

}