package User_And_Member;

public class User {
    String email;
    String password;
    LockedPassword passwordStatus;

    public User(String email, String password, LockedPassword passwordStatus){
        this.email = email;
        this.password = password;
        this.passwordStatus = passwordStatus;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public void setPasswordStatus(LockedPassword newpasswordStatus){
        passwordStatus = newpasswordStatus;
    }

    public LockedPassword getPasswordStatus() {
        return passwordStatus;
    }

    public String toString(){
        return String.format("""
                User:
                Email: %s
                Password: %s
                """, email, password);
    }
}
