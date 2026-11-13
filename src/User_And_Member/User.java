package User_And_Member;

public class User {
    String email;
    String password;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public String toString(){
        return String.format("""
                User:
                Email: %s
                Password: %s
                """, email, password);
    }
}
