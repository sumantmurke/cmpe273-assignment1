package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yammer.dropwizard.jersey.params.LongParam;

@JsonPropertyOrder({"isbn","title","publication-date","language","num-pages","status","reviews","authors"})
public class Book {
	@JsonProperty
	private long isbn;

	@JsonProperty
	@NotEmpty
	private String title;

	@JsonProperty("publication-date")
	@NotEmpty
	private String publication_date;

	@JsonProperty
	private String language;

	@JsonProperty("num-pages")
	private int num_pages;

	@JsonProperty
	private String status;

	@JsonProperty
	private ArrayList<Review> reviews = new ArrayList<Review>();

	// @JsonProperty
	//private Authors[] authors;


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

	public String getPublication_date(){
		return publication_date;
	}

	public void setPublication_date(String publication_date){
		this.publication_date = publication_date;

	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getNum_pages() {
		return num_pages;
	}
	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status= status;
	}

	public ArrayList<Review> getReview(){
		return reviews;

	}
	public void setReview(ArrayList<Review> reviews){
		this.reviews= reviews;
	}
	
	public Review getbookReview(int id){
		return reviews.get(id);
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
