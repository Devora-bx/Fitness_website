package com.movies.beans;

import java.io.Serializable;
import java.sql.*;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("loginBean")
@SessionScoped
public class loginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String role;

    // Empty constructor - required by JSF/CDI
    public loginBean() {
    }

    // --- Getters and Setters ---

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- Login logic ---

    public String login() {
        // H2 database path - using embedded mode with AUTO_SERVER for external access
        String url = "jdbc:h2:C:/Users/USER/test;AUTO_SERVER=TRUE";
        String user = "sa";
        String pass = "";

        try {
            Class.forName("org.h2.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "SELECT role FROM Users WHERE username = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    this.role = rs.getString("role");

                    if ("admin".equals(this.role)) {
                        return "admin_dashboard?faces-redirect=true";
                    } else {
                        return "catalog?faces-redirect=true";
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "שגיאה:", "שם משתמש או סיסמה לא נכונים."));
                    return null;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: H2 driver not found in lib!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }
        return null;
    }

    // Logout
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}