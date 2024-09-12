package com.tripmaven.guideranking;

public class GuideRankingDTO {
    private Long id;
    private String name;
    private Long postCount;

    // Constructor
    public GuideRankingDTO(Long id, String name, Long postCount) {
        this.id = id;
        this.name = name;
        this.postCount = postCount;
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPostCount() {
        return postCount;
    }

    // Optionally, you can add setter methods if needed
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }
}
