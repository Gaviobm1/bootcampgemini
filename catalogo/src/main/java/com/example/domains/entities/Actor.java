package com.example.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name = "actor")
@NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a")
public class Actor extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Partial {
	}

	public static class Complete extends Partial {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "actor_id", unique = true, nullable = false)
	@JsonView(Actor.Partial.class)
	private int actorId;

	@Column(name = "first_name", nullable = false, length = 45)
	@Size(max = 45, min = 2, message = "First name must be between 2 and 45 characters")
	@Pattern(regexp = "^[A-Z]*$", message = "First name must be capitalized")
	@JsonView(Actor.Partial.class)
	private String firstName;

	@Size(max = 45, min = 2, message = "Last name must be between 2 and 45 characters")
	@Pattern(regexp = "^[A-Z]*$", message = "Last name must be capitalized")
	@Column(name = "last_name", nullable = false, length = 45)
	@JsonView(Actor.Partial.class)
	private String lastName;

	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
	@PastOrPresent
	@JsonView(Actor.Complete.class)
	private Timestamp lastUpdate;

	// bi-directional many-to-one association to FilmActor
	@JsonIgnore
	@OneToMany(mappedBy = "actor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonView(Actor.Complete.class)
	private List<FilmActor> filmActors;

	public Actor() {
	}

	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

	public void jubilate() {
		// pon active a false
		// pon fecha de baja a la fecha actual
	}

	public void premioRecibido(String premio) {
		// ...
	}
}