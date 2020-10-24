package com.sajina;

import static spark.Spark.*;
import static com.sajina.JsonUtil.*;

public class BookController {
    
    public BookController(final BookService bookService) {

		get("/books", (req, res) -> bookService.getBooks(), json());

		get("/books/:id", (req, res) -> {
			String id = req.params(":id");
			Book book = bookService.getBook(id);
			if (book != null) {
				return book;
			}
			res.status(400);
			return new ResponseError("No book with id '%s' found", id);
		}, json());

		post("/books", (req, res) -> bookService.createBook(
                req.queryParams("id"),
				req.queryParams("name"),
                req.queryParams("author"),
                req.queryParams("price")
        ), json());
        put("/books/:id", (req, res) -> bookService.updateBook(
            req.params(":id"),
            req.queryParams("name"),
            req.queryParams("author"),
            req.queryParams("price")
    ), json());

    delete("/books/:id", (req, res) -> bookService.deleteBook(
        req.params(":id")
    ),json());

    after((req, res) -> {
        res.type("application/json");
    });

    exception(IllegalArgumentException.class, (e, req, res) -> {
        res.status(400);
        res.body(toJson(new ResponseError(e)));
    });

}
}

