package com.example.demo.user;

import com.example.demo.contratmariage.ContratMariage;
import com.example.demo.declarationnaissance.DeclarationNaissance;
import com.example.demo.diplome.Diplome;
import com.example.demo.permis.Permis;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode
@Entity
@Table(name="userCit")
public class Userego implements UserDetails {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName="user_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="user_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate dateNaissance;
    private String cin;

    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole userRole;
    private Boolean locked=false;
    private Boolean enabled=false;

    @OneToOne
    private Permis permis;

    @OneToOne
    private Diplome diplome;

    @ManyToOne
    private ContratMariage contratMariage;

    @OneToOne
    private DeclarationNaissance declarationNaissance;

    public Userego() {
    }
    public Userego(Long id, String firstName, String lastName, String email, LocalDate dateNaissance, String cin, String phone, String password, AppUserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.cin = cin;
        this.phone=phone;
        this.password=password;
        this.userRole=role;
    }
    public Userego(String firstName, String lastName, String email, LocalDate dateNaissance, String cin, String phone, String password, AppUserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.cin = cin;
        this.phone=phone;
        this.password=password;
        this.userRole=role;
    }
    @Override
    public String toString() {
        return "Userego{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", cin='" + cin + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role='" + userRole + '\'' +
                ", locked='" + locked + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
