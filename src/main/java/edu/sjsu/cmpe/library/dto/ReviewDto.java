package edu.sjsu.cmpe.library.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;


@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {
	public Review review;

	public ReviewDto(Review review) {
		super();
		this.review = review;
		// TODO Auto-generated constructor stub
	}
   

	public Review getReview() {
	return review;
    }

	
    
    public void setReview(Review review) {
	this.review = review;
    }

   
}