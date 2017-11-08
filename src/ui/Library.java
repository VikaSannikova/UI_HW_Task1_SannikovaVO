package ui;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Boris on 05.11.2017.
 */
public class Library extends AbstractTableModel {

    private List<Book> books = new ArrayList<>();
    private String fileName;

    public void setFile(String fileName){
        this.fileName = fileName;
        try {
            Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
            String data;
            while(scanner.hasNext()) {
                data = scanner.useDelimiter("\n").next();

                StringTokenizer stok = new StringTokenizer(data, " ");

                String bookName = stok.nextToken();
                String edition = stok.nextToken();

                Genre genre = Genre.Classic;
                String tmp = stok.nextToken();
                if(tmp.equals("Comics"))
                    genre = Genre.Comics;
                if(tmp.equals("Detective"))
                    genre = Genre.Detective;
                if(tmp.equals("Fantasy"))
                    genre = Genre.Fantasy;
                if(tmp.equals("Scientific"))
                    genre = Genre.Scientific;

                int qty = Integer.parseInt(stok.nextToken());
                double price = Double.parseDouble(stok.nextToken());
                String authorName = stok.nextToken();
                String email = stok.nextToken();

                Gender gender = Gender.Man;
                if(stok.nextToken().equals("Woman")) gender = Gender.Woman;

                books.add(new Book(bookName,edition, genre, new Author(authorName,email,gender),price,qty));
            }
            scanner.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        fireTableDataChanged();
    }

    public void newFile(String fileName) throws IOException{
        File file = new File(fileName+".txt");
        file.createNewFile();
    }

    public void deleteBook(int row){
        books.remove(row);
        fireTableDataChanged();
    }

    public Book getBook(int row){
        return books.get(row);
    }

    public void setBook(int row, Book book){
        books.set(row, book);
        fireTableDataChanged();
    }

    public void saveFile(){
        String data = "";

        for (Book book:books) {
            data+=book.getName()+" "+book.getEdition()+" "+book.getGenre()+" "+book.getQty()+" "+book.getPrice()+ " " + book.getAuthor().getName()+ " " + book.getAuthor().getEmail()+ " " + book.getAuthor().getGender() + "\n";
        }

        File file = new File(fileName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear(){
        books.clear();
        fireTableDataChanged();
    }

    public void addBook(Book b){
        books.add(b);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() { return 6; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book cur=books.get(rowIndex);
        switch (columnIndex){
            case 0:
                return cur.getName();
            case 1:
                return cur.getEdition();
            case 2:
                return cur.getGenre();
            case 3:
                return cur.getAuthor().toString();
            case 4:
                return cur.getPrice();
            case 5:
                return cur.getQty();
        }
        return null;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Book name";
            case 1:
                return "Edition";
            case 2:
                return "Genre";
            case 3:
                return "Author";
            case 4:
                return "Price";
            case 5:
                return "Quality";
        }
        return "";
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;
            case 5:
                return Integer.class;
        }
        return Object.class;
    }
}