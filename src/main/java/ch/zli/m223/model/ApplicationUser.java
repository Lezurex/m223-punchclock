package ch.zli.m223.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class ApplicationUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private long id;

  @Column(nullable = false)
  @NotBlank(message = "Title may not be blank.")
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "category")
  @JsonIgnoreProperties("category")
  @Fetch(FetchMode.JOIN)
  private Set<Entry> entries;

  public ApplicationUser(String title, String password) {
    this.username = title;
    this.password = password;
  }

  public ApplicationUser() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

}

