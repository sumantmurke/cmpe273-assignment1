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

import edu.sjsu.cmpe.library.domain.Authors;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private int reviewId =1; 
    private static int author_id = 1;
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
	    if (book.getReview().size() > 0) {
            links.add(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() +"/reviews", "GET"));
	    }
	    responseMap.put("links", links);

	      return Response.status(200).entity(responseMap).build();
	 }
   
  
  
   @POST
   @Path("/{isbn}/reviews")
   @Timed(name= "create-reviews")
   public Response createReviews(Review reviewRequest,@PathParam("isbn") LongParam isbn){
	  
	 Book book = bookRepository.getBookByISBN(isbn.get());
	
	reviewRequest.setId(reviewId);
	reviewId++;
     book.getReview().add(reviewRequest);
	  // Review savedReview = bookRepository.saveReview(reviewRequest);
	   
	   //String location = "/books/" + book.getIsbn() +"/reviews/" + reviewRequest.getId() ;
	  
	   
	   ReviewDto reviewResponse = new ReviewDto();
		reviewResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + reviewRequest.getId(), "GET"));

	return Response.status(201).entity(reviewResponse.getLinks()).build();
	   
	   /*
	   System.out.println("my comment"+ reviewRequest.getComment());
	   Map<String, Object> responseMap = new HashMap<String, Object>();
	   List<LinkDto> links = new ArrayList<LinkDto>();
	   links.add(new LinkDto("view-review", location, "GET"));
	   responseMap.put("links", links);
		
	   
		   return Response.status(200).entity(responseMap).build();
	   
	   */
 }
     
   /*
   @POST
   @Path("/{isbn}/reviews")
   //@Produces({MediaType.APPLICATION_JSON})
   //@Consumes({MediaType.APPLICATION_JSON})
   @Timed(name = "create-review")
   public Response createReview(@PathParam("isbn") LongParam isbn, Review reviewToAdd) {
       

           Book book = bookRepository.getBookByISBN(isbn.get());
           List<Review> reviews = book.getReview();
           reviewToAdd.setId(reviews.size() + 1);
           reviews.add(reviewToAdd);
           book.setReview(reviews);
           bookRepository.updateBook(book);

           LinksDto bookResponse = new LinksDto();
           bookResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn()+ "/reviews/" + reviewToAdd.getId(), "GET"));

           return Response.status(201).entity(bookResponse).build();

       
   } */
   
   @GET
   @Path("/{isbn}/reviews/{id}")
   @Produces({MediaType.APPLICATION_JSON})
   @Timed(name = "view-review")
   public Response viewReview(@PathParam("isbn") LongParam isbn, @PathParam("id") int id) {
      
           Book book = bookRepository.getBookByISBN(isbn.get());
           List<Review> reviews = book.getReview();
           Review review = reviews.get(id);

           ReviewDto reviewResponse = new ReviewDto(review);
           reviewResponse.addLink(new LinkDto("view-review", "/books/" + isbn + "/reviews/" + id, "GET"));

           return Response.status(200).entity(reviewResponse).build();

          }

   
   @GET
   @Path("/{isbn}/reviews")
 //  @Produces({MediaType.APPLICATION_JSON})
   @Timed(name = "view-all-reviews")
   public Response viewAllReviews(@PathParam("isbn") LongParam isbn) {
       
           Book book = bookRepository.getBookByISBN(isbn.get());
           List<Review> reviews = book.getReview();

           ReviewsDto reviewsResponse = new ReviewsDto(reviews);

           return Response.status(200).entity(reviewsResponse).build();

       
   }
   
   
   @GET
   @Path("/{isbn}/authors/{id}")
   @Produces({MediaType.APPLICATION_JSON})
   @Timed(name = "view-author")
   public Response viewAuthor(@PathParam("isbn") LongParam isbn, @PathParam("id") int id) {
       
           Book book = bookRepository.getBookByISBN(isbn.get());
           List<Authors> authors = book.getAuthors();
           
           Authors author = authors.get(id);

           AuthorDto authorResponse = new AuthorDto(author);
           authorResponse.addLink(new LinkDto("view-author", "/books/" + isbn + "/authors/" + id, "GET"));

           return Response.status(200).entity(authorResponse).build();

       
           
       }
   
   @GET
   @Path("/{isbn}/authors")
   @Produces({MediaType.APPLICATION_JSON})
   @Timed(name = "view-all-authors")
   public Response viewAllAuthors(@PathParam("isbn") LongParam isbn) {
      
           Book book = bookRepository.getBookByISBN(isbn.get());
           List<Authors> authors = book.getAuthors();

           AuthorsDto authorsResponse = new AuthorsDto(authors);

           return Response.status(200).entity(authorsResponse).build();

       
   }
  
   
}

