package edu.sjsu.cmpe.library.domain;


public class Book {
    private long isbn;
    private String title;
    private String language;
    private String status;
    private String publication_date;
    private String authors[];
    // add more fields here

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    public String getLanguage() {
    	return language;
        }
    public void setLanguage(String language) {
    	this.language = language;
        }
    public String getStatus() {
    	return status;
        }
    public void setStatus(String status) {
    	this.status= status;
        }
    
    public String getPublication_Date(){
    	return publication_date;
    }
    public void setPublication_Date(String publication_date){
    	this.publication_date = publication_date;
    	
    }
    
   /*
    public String[] getAuthors() {
    	return authors ;
    }
    
    public void setAuthors(String authors[]) {
    	this. authors = authors;
    }
     */ 
    
    
}
