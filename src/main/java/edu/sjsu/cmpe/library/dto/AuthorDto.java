package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cmpe.library.domain.Authors;

public class AuthorDto extends LinksDto {

    @JsonProperty("author")
    private Authors author;

    public AuthorDto(Authors author) {
        this.setAuthor(author);
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }
}
