package de.jugmz.sponsors;

public class SponsorDto {

    final String id;
    final String name;
    final String url;
    final String imgName;

    public SponsorDto(String id, String name, String url, String imgName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imgName = imgName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImgName() {
        return imgName;
    }
}
