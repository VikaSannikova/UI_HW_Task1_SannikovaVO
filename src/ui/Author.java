package ui;

import java.util.Objects;

enum Gender{
    Man,
    Woman,
}

public class Author {
    private String name;
    private String email;
    private Gender gender;

    public Author(String name, String email, Gender gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "Author[" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ']';
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}