package Repository;

public class Author {
    public String firstName;
    public String lastName;
    public String midName;
    public String title;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", midName='" + midName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
