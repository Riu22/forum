package com.example.forum.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class user implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generación automática de UUID
    @Column(name = "_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "moderate_category")
    private String moderateCategory;

    @Version
    @Column(name = "__v")
    private Integer version;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission")
    private List<String> permissions;

    @OneToMany(mappedBy = "moderator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<categori> categories;

    public List<categori> getCategories() {
        return categories;
    }

    public void setCategories(List<categori> categories) {
        this.categories = categories;
    }
// --- CONSTRUCTORES ---

    /**
     * Constructor vacío requerido por JPA/Hibernate
     */
    public user() {}

    /**
     * Constructor recomendado para el proceso de Registro.
     * Observa que NO incluimos 'id' ni 'version', permitiendo que JPA
     * los asigne correctamente al persistir.
     */
    public user(String email, String name, String password, String role,
                String avatarUrl, String moderateCategory, List<String> permissions) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.moderateCategory = moderateCategory;
        this.permissions = permissions;
    }

    // --- MÉTODOS DE SPRING SECURITY (UserDetails) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte el rol simple a un formato que Spring Security entienda
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // --- GETTERS Y SETTERS ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getModerateCategory() { return moderateCategory; }
    public void setModerateCategory(String moderateCategory) { this.moderateCategory = moderateCategory; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}