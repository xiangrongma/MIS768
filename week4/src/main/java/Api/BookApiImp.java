package Api;

import Repository.Author;
import Repository.Book;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookApiImp implements BookApi {
    public static final String BASE_URL = "https://www.gutenberg.org/";
    public static final String INDEX_ROOT = BASE_URL + "dirs/GUTINDEX.2020";
    private static final Logger logger = LogManager.getLogger(BookApi.class);

    HttpClient client = new DefaultHttpClient();
    HttpGet request;
    HttpResponse response;

    private static Book parseBook(String s) {
        String[] parts = s.split(", by ");
        String title = parts[0];
        String[] nameparts = parts[1].split("[ \\t\\s]+");

        StringBuilder authorBuilder = new StringBuilder();
        Integer bookid = 9999;
        for (int i = 0; i < nameparts.length - 1; i++) {
            authorBuilder.append(" ").append(nameparts[i].trim());

        }
        try {
            if (nameparts.length > 1) {
                bookid = Integer.parseInt(nameparts[nameparts.length - 1]);

            }
        } catch (NumberFormatException e) {
        }

        Book b = new Book();
        b.author = authorBuilder.toString().trim();
        b.title = title;
        b.Id = bookid;
        return b;
    }

    @Override
    public List<Book> getBooks() {

//        String response =  getFileFromUrl(INDEX_ROOT);
        String content = getFileFromFS("/Users/mark/Projects/MIS768/week4/GUTINDEX.ALL");
        List<Book> books = new ArrayList<>();
        String header = "TITLE and AUTHOR                                                     EBOOK NO.";
        int posStart = content.indexOf(header);
        content = content.substring(posStart + header.length());
        String[] lines = content.split("\\n");

        books = Stream.of(lines)
                .filter(s -> s.contains(", by "))
                .map(
                        BookApiImp::parseBook)
                .collect(Collectors.toList());
        books.sort(Comparator.comparingInt(a -> a.Id));
        return books;
    }

    @Override
    public List<Author> getAuthors() {
        String response = getFileFromUrl(INDEX_ROOT);
        List<Author> authors = new ArrayList<>();
        return authors;

    }

    private String getFileFromFS(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return sb.toString();
    }

    private String getFileFromUrl(String url) {
        StringBuilder sb = new StringBuilder();
        this.request = new HttpGet(url);
        try {
            response = client.execute(request);
            // Get the response
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {

                sb.append(line + "\n");
            }
            logger.error(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BookApi api = new BookApiImp();
        List<Book> books = api.getBooks();
        logger.error(books);

    }
}
