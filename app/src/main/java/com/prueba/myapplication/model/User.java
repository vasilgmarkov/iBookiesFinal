package com.prueba.myapplication.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserToken userToken;
    private Double saldo;
    private String login;

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    private String langKey;


    public User() {
    }

    public User(Long id, String username, String password, UserToken userToken, Double saldo, String login, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userToken = userToken;
        this.saldo = saldo;
        this.login = login;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (userToken != null ? !userToken.equals(user.userToken) : user.userToken != null)
            return false;
        if (saldo != null ? !saldo.equals(user.saldo) : user.saldo != null) return false;
        return !(login != null ? !login.equals(user.login) : user.login != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userToken != null ? userToken.hashCode() : 0);
        result = 31 * result + (saldo != null ? saldo.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userToken=" + userToken +
                ", saldo=" + saldo +
                ", login='" + login + '\'' +
                '}';
    }
}
