package Api;

import Collections.TaggedArray;
import Repository.Author;
import Repository.Book;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookApiImp implements BookApi {
  public static final String BASE_URL = "https://www.gutenberg.org/";
  public static final String INDEX_ROOT = BASE_URL + "dirs/GUTINDEX.2020";
  private static final Logger logger = LogManager.getLogger(BookApi.class);
    private static  List<Book> books = new LinkedList<>();

  HttpClient client = new DefaultHttpClient();
  HttpGet request;
  HttpResponse response;

  private static Book parseBook(String s) {
    String[] parts = s.split(", by ");
    String title = parts[0];
    String[] nameparts = parts[1].split("[ \\t\\s]+");

    StringBuilder authorBuilder = new StringBuilder();
    int bookid = 9999;
    for (int i = 0; i < nameparts.length - 1; i++) {
      authorBuilder.append(" ").append(nameparts[i].trim());
    }
    try {
      if (nameparts.length > 1) {
        bookid = Integer.parseInt(nameparts[nameparts.length - 1]);
      }
    } catch (NumberFormatException ignored) {
    }

    Book b = new Book();
    b.author = authorBuilder.toString().trim();
    b.title = title;
    b.Id = bookid;
    return b;
  }

  static String genPathFromId(Integer Id) {
    char[] digits = Id.toString().toCharArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < digits.length - 1; i++) {
      sb.append("/").append(digits[i]);
    }
    sb.append("/").append(Id);
    return sb.toString();
  }

  public static void main(String[] args) {
    BookApi api = new BookApiImp();
    api.downloadBooks();
  }

  List<String> genDownloadList() {
    List<Book> books = getBooks();
    final String mirror_url =
        "https://www.mirrorservice.org/sites/ftp.ibiblio.org/pub/docs/books/gutenberg";
    final String format = ".zip";

    return books.stream()
        .map(book -> mirror_url + genPathFromId(book.Id) + "/" + book.Id + format)
        .collect(Collectors.toList());
  }

  @Override
  public void downloadBooks() {
    List<String> urls = genDownloadList();
    String path = "/Users/mark/Projects/MIS768/week4";
    List<String> fileNames =
        urls.stream()
            .map(
                urlString -> {
                  String[] parts = urlString.split("/");
                  return path + parts[parts.length - 1];
                })
            .collect(Collectors.toList());

    final TaggedArray<String> jobArray = new TaggedArray(urls.toArray(), fileNames.toArray());
    int total = urls.size();

    AtomicInteger remaining = new AtomicInteger(total);
    AtomicInteger success1 = new AtomicInteger(0);
    AtomicInteger success2 = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);

    TaggedArray.parEach(
        jobArray,
        urlString -> {
          String[] parts = urlString.split("/");
          String filename = path + parts[parts.length - 1];
          ReadableByteChannel rbc;
          try {
            URL remoteurl = new URL(urlString);
            rbc = Channels.newChannel(remoteurl.openStream());

            FileOutputStream fos = new FileOutputStream(filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            logger.info("saved at " + filename);
            success1.getAndIncrement();
            remaining.getAndDecrement();
          } catch (FileNotFoundException e) {
            // retry "-0.zip"
            try {
              logger.error(e);
              String newUrl = urlString.replace(".zip", "-0.zip");
              logger.error("retying on " + newUrl);
              URL remoteurl = new URL(newUrl);
              rbc = Channels.newChannel(remoteurl.openStream());
              FileOutputStream fos = new FileOutputStream(filename);
              fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
              logger.info("saved at " + filename);
            } catch (IOException e1) {
              logger.error(e1);
              success2.getAndIncrement();
              remaining.getAndDecrement();
            }
          } catch (IOException e) {
            logger.error(e);
            failed.getAndIncrement();
            remaining.getAndDecrement();
          }
            logger.error("total : " + total +
                    "\tRemaining" + remaining +
                    "\tSuccess (.zip): " + success1 +
                    "\tSuccess(Retried with 0.zip) : " + success2
                    +"\tFailed : " + failed);
        });
  }

  @Override
  public List<Book> getBooks() {

    //        String response =  getFileFromUrl(INDEX_ROOT);
    String content = getFileFromFS("/Users/mark/Project/JavaClass/week4/GUTINDEX.ALL");
    String header =
        "TITLE and AUTHOR                                                     EBOOK NO.";
    int posStart = content.indexOf(header);
    content = content.substring(posStart + header.length());
    String[] lines = content.split("\\n");

    books =
        Stream.of(lines)
            .filter(s -> s.contains(", by "))
            .map(BookApiImp::parseBook)
            .collect(Collectors.toList());
    books.sort(Comparator.comparingInt(a -> a.Id));
    return books;
  }

    @Override
    public Book getBook(Integer bookid) {
      if(books.size() > 0) {

          final List<Book> collect = books.stream()
                  .filter(s -> s.Id.equals(bookid))
                  .collect(Collectors.toList());
          System.out.println(collect);
          return collect.get(0);
      } else {
          return null;
      }
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
      BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
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
      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      String line = "";
      while ((line = rd.readLine()) != null) {

        sb.append(line).append("\n");
      }
      logger.error(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
