package es.home.example.pojo;

public class Book {
	private Integer id;
	private String author;
	private String name;

	public Book() {
		super();
	}

	public Book(final Integer id, final String author, final String name) {
		super();
		this.id = id;
		this.author = author;
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
