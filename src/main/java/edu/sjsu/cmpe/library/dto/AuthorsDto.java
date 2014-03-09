package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cmpe.library.domain.Authors;

import java.util.List;

public class AuthorsDto extends LinksDto {

    @JsonProperty("authors")
    private List<Authors> authors;

    public AuthorsDto(List<Authors> authors) {
        this.setAuthors(authors);
    }

    public List<Authors> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Authors> authors) {
        this.authors = authors;
    }
}
