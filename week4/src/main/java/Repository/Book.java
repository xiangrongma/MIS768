package Repository;

public class Book {
    public String title;
    public String author;
    public Integer Id;

    @Override
    public String toString() {
        return "{ Id: "+ Id + ", title: "+ title + ", author: "+ author+ "}";
    }
}
