package com.epam.training.task7.data;

import com.epam.training.task7.Configuration;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Author implements Serializable {

    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Gender gender;

    public Author(String name, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return Period.between(this.getDateOfBirth(), this.getDateOfDeath() != null ? dateOfDeath : LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(name);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(dateOfBirth);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(dateOfDeath);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(gender);
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!Objects.equals(name, author.name)) {
            return false;
        }
        if (!Objects.equals(dateOfBirth, author.dateOfBirth)) {
            return false;
        }
        if (!Objects.equals(gender, author.gender)) {
            return false;
        }
        if (!Objects.equals(dateOfDeath, author.dateOfDeath)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (dateOfDeath != null ? dateOfDeath.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}
