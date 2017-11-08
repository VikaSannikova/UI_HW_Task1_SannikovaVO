package ui;

enum Genre{
    Fantasy,
    Classic,
    Detective,
    Comics,
    Scientific
}
public class Book {
    private  String name;
    private String edition;
    private Genre genre;
    private Author author;
    private double price;
    private int qty;


    public Book(String name,String edition, Genre genre, Author author, double price) {
        this.name = name;
        this.edition = edition;
        this.genre = genre;
        this.author = author;
        this.price = price;
    }

    public Book(String name, String edition, Genre genre, Author author, double price, int qty) {
        this.name = name;
        this.edition = edition;
        this.genre = genre;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    public Author getAuthor() { return author; }

    public String getName() { return name; }

    public String getEdition() { return edition; }

    public Genre getGenre() { return genre; }

    public void setName(String name) { this.name = name; }

    public void setEdition(String edition) { this.edition = edition; }

    public void setGenre(Genre genre) { this.genre = genre; }

    public Author getAuthors() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Book[" +
                "name='" + name + '\'' +
                ", edition=" +edition +
                ",genre=" +genre +
                ", authors=" +author +
                ", price=" + price +
                ", qty=" + qty +
                ']';
    }

}
