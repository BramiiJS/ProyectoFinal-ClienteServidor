package casoestudio2.modelo;

public class Administrador extends Usuario {

    public Administrador(String username, String password) {
        super(username, password, "Administrador");
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

}