package ru.bobcody.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(schema = "public", name = "guests")
public class Guest {
    @Id
    private Long id;
    @Column(name = "first_name")
    @NotNull
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "language_code")
    private String languageCode;
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TextMessage> textMessages;
    @Column(name = "city_name", columnDefinition = "varchar(40) default 'Izhevsk'")
    private String cityName;

    public Guest(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.languageCode = user.getLanguageCode();
        this.cityName = "Izhevsk";
    }

    public Guest(Long id, String firstName, String lastName, String userName, String languageCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
        this.cityName = "Izhevsk";
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
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(getId(), guest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
