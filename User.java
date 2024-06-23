import java.io.Serializable;

class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String username;
    private String password;
    private int age;

    public User(String name, String username, String password, int age) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //getters
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
