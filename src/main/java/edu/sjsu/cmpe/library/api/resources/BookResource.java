package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;


//import org.eclipse.jetty.http.HttpStatus;



import java.util.*;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
		"GET"));
	bookResponse.addLink(new LinkDto("update-book",
		"/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", "/books/"+ book.getIsbn(),"DELETE" ));
	bookResponse.addLink(new LinkDto("create-review", "/books/"+ book.getIsbn() + "/reviews", "POST") );
	
	return bookResponse;
    }

    @POST
    @Timed(name = "create-book")
    public Response createBook(Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	String location = "/books/" + savedBook.getIsbn();
	
	/*
	BookDto bookResponse = new BookDto(savedBook);
	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	bookResponse.addLink(new LinkDto("create-book", location, "POST"));
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	// Add other links if needed
*/
	//return Response.status(201).entity(bookResponse).build();
	
	
	
    Map<String, Object> responseMap = new HashMap<String, Object>();
    List<LinkDto> links = new ArrayList<LinkDto>();
    links.add(new LinkDto("view-book", location, "GET"));
    links.add(new LinkDto("update-book",location,"PUT"));
    links.add(new LinkDto("delete-book",location,"DELETE"));
    links.add(new LinkDto("create-review",location+ "/reviews","POST"));

    responseMap.put("links", links);

      return Response.status(201).entity(responseMap).build();


    }
    
    
   
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
   public Response deleteBook(@PathParam("isbn") LongParam isbn) {
    // public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn1) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	bookRepository.deleteBook(book);
       // System.out.println("Book has been deleted");
    	
  /*
        BookDto bookResponse = new BookDto(book);
    	bookResponse.addLink(new LinkDto("create-book", "isbn", "POST"));
    	//return Response.status(204).entity(bookResponse).build();   
    */	
    
    	 Map<String, Object> responseMap = new HashMap<String, Object>();
    	 List<LinkDto> links = new ArrayList<LinkDto>();
    	 links.add(new LinkDto("create-book", "/books", "POST"));;
    	 responseMap.put("links", links);
       	 return Response.status(200).entity(responseMap).build();
    }
    		
   @PUT
   @Path("/{isbn}")
   @Timed(name= "update-book")
   public Response updateBook(@PathParam("isbn") LongParam isbn,@QueryParam("status") String status){
	  // System.out.println("inside put API"); 
	   Book book = bookRepository.getBookByISBN(isbn.get());
	   book.setStatus(status);
	   //System.out.println(" The current status is : "+ book.getStatus());
	   Map<String, Object> responseMap = new HashMap<String, Object>();
	    List<LinkDto> links = new ArrayList<LinkDto>();
	    links.add(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
	    links.add(new LinkDto("update-book","/books/" + book.getIsbn(),"PUT"));
	    links.add(new LinkDto("delete-book","/books/" + book.getIsbn(),"DELETE"));
	    links.add(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews","POST"));

	    responseMap.put("links", links);

	      return Response.status(200).entity(responseMap).build();
	 }
   
   @POST
   @Path("/{isbn}/reviews")
   @Timed(name= "create-reviews")
   public Response createReviews(Review reviewRequest,@PathParam("isbn") LongParam isbn){
	  
	 Book book = bookRepository.getBookByISBN(isbn.get());

	   Review savedReview = bookRepository.saveReview(reviewRequest);
	   
	   String location = "/books/" + book.getIsbn() +"/reviews/" + savedReview.getId() ;
	   
	   Map<String, Object> responseMap = new HashMap<String, Object>();
	   List<LinkDto> links = new ArrayList<LinkDto>();
	   links.add(new LinkDto("view-review", location, "GET"));
	   responseMap.put("links", links);
		
	   
	 //  Book retrieveBook = bookRepository.getBookByISBN(isbn.get());
/*
		Review.setId(id);
		retrieveBook.getReview().add(reviews);
		review_id++;

	  // public Response createBook(Book request) {
	*/   
	   return Response.status(200).entity(responseMap).build();
	   
	   
   }
    
    
}

