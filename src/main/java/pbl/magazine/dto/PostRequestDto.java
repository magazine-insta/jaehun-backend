package pbl.magazine.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String contents;
    private String imageUrl;
    private String layoutType;
}
