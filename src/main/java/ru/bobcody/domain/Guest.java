package ru.bobcody.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table
public class Guest implements Serializable {
    private static final long serialVersionUID = 284892174904515L;
    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String firstName;

    @Column(columnDefinition = "varchar(255)")
    private String lastName;

    @Column
    private String userName;

    @Column
    private String languageCode;

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    private Set<TextMessage> textMessages = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City city;

    public Guest(org.telegram.telegrambots.meta.api.objects.User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.languageCode = user.getLanguageCode();
    }

    public Guest(Long id, String firstName, String lastName, String userName, String languageCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return userName + " "
                + lastName + " "
                + firstName + " "
                + id + " "
                + languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id.equals(guest.id) && firstName.equals(guest.firstName) && Objects.equals(lastName, guest.lastName) && Objects.equals(userName, guest.userName) && Objects.equals(languageCode, guest.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
