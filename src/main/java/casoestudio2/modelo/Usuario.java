package casoestudio2.modelo;

public abstract class Usuario {

    protected String username;
    protected String password;
    protected String rol;

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public abstract boolean login(String username, String password);

    public void logout() {
        System.out.println("Sesión finalizada.");
    }
}