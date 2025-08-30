package org.dat250.poll.domains;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(String username, String password, String email, String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
