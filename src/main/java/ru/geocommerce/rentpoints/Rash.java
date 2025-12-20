package ru.geocommerce.rentpoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rash {
    private String type;
    private String id;
    private String form;
    private String highlight;
    private String favoritesIds;
    private Coordinates coords;
    private int radius;
    private int itemsCount;
    private boolean isFavorite;
    private boolean isVas;
    private boolean isOutGeo;

    // Геттеры и сеттеры

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getForm() { return form; }
    public void setForm(String form) { this.form = form; }

    public String getHighlight() { return highlight; }
    public void setHighlight(String highlight) { this.highlight = highlight; }

    public String getFavoritesIds() { return favoritesIds; }
    public void setFavoritesIds(String favoritesIds) { this.favoritesIds = favoritesIds; }

    public Coordinates getCoords() { return coords; }
    public void setCoords(Coordinates coords) { this.coords = coords; }

    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }

    public int getItemsCount() { return itemsCount; }
    public void setItemsCount(int itemsCount) { this.itemsCount = itemsCount; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public boolean isVas() { return isVas; }
    public void setVas(boolean vas) { isVas = vas; }

    public boolean isOutGeo() { return isOutGeo; }
    public void setOutGeo(boolean outGeo) { isOutGeo = outGeo; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coordinates {
        private double lng;
        private double lat;

        public double getLng() { return lng; }
        public void setLng(double lng) { this.lng = lng; }

        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
    }

    @Override
    public String toString() {
        return "Rash{" +
                "id='" + id + '\'' +
                ", coords=" + coords.lat + "," + coords.lng +
                ", radius=" + radius +
                '}';
    }
}
