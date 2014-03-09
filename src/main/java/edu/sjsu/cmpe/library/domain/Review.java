package edu.sjsu.cmpe.library.domain;

public class Review {
	private Long id;
	private int rating;
	private String comment;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id= id;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


}
