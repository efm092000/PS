public class User {
    private String name="";
    private String mail="";
    private String password="";
    enum entorno{HOME,GYM,OUTSIDE};
    private int id;
    private Routine[] myRoutines;


    public User(String name, String mail, String password){
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
