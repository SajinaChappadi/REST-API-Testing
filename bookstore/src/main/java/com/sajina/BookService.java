package com.sajina;

import java.util.*;

public class BookService {
    private Map<String, Book> books = new HashMap<>();

  
	public List<Book> getBooks() {
		return new ArrayList<>(books.values());
	}

	public Book getBook(String id) {
		return books.get(id);
	}

	public Book createBook(String id,String name, String author, String price) {
		failIfInvalid(id,name, author,price);
		Book book = new Book(id, name, author,price);
		books.put(book.getId(), book);
		return book;
  }

  public Book updateBook( String id, String name, String author,String price) {
		Book book = books.get(id);
		if (book == null) {
			throw new IllegalArgumentException("No book with id '" + id + "' found");
		}
		failIfInvalid(id,name, author,price);
		book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
		return book;
    }
    
    public String deleteBook(String id){
        Book book = books.remove(id);
		
        if (book == null) {
			throw new IllegalArgumentException("No book with id '" + id + "' found");
		}
		String message = "Book deleted";
		return message;
    }
  
  private void failIfInvalid(String id, String name, String author, String price) {
	if (id == null || id.isEmpty()) {
		throw new IllegalArgumentException("Parameter 'id' cannot be empty");
	}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'name' cannot be empty");
		}
		if (author == null || author.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'author' cannot be empty");
        }
        if (price == null || price.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'price' cannot be empty");
		}
}
}


