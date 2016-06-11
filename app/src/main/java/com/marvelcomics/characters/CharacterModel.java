package com.marvelcomics.characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHUCHI on 6/4/2016.
 */
public class CharacterModel {


    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("attributionText")
    @Expose
    private String attributionText;
    @SerializedName("attributionHTML")
    @Expose
    private String attributionHTML;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     *
     * @param copyright
     * The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     *
     * @return
     * The attributionText
     */
    public String getAttributionText() {
        return attributionText;
    }

    /**
     *
     * @param attributionText
     * The attributionText
     */
    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    /**
     *
     * @return
     * The attributionHTML
     */
    public String getAttributionHTML() {
        return attributionHTML;
    }

    /**
     *
     * @param attributionHTML
     * The attributionHTML
     */
    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    /**
     *
     * @return
     * The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     * The etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("limit")
        @Expose
        private Integer limit;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("results")
        @Expose
        private List<Result> results = new ArrayList<Result>();

        /**
         * @return The offset
         */
        public Integer getOffset() {
            return offset;
        }

        /**
         * @param offset The offset
         */
        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        /**
         * @return The limit
         */
        public Integer getLimit() {
            return limit;
        }

        /**
         * @param limit The limit
         */
        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        /**
         * @return The total
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * @param total The total
         */
        public void setTotal(Integer total) {
            this.total = total;
        }

        /**
         * @return The count
         */
        public Integer getCount() {
            return count;
        }

        /**
         * @param count The count
         */
        public void setCount(Integer count) {
            this.count = count;
        }

        /**
         * @return The results
         */
        public List<Result> getResults() {
            return results;
        }

        /**
         * @param results The results
         */
        public void setResults(List<Result> results) {
            this.results = results;
        }

        public class Result {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("modified")
            @Expose
            private String modified;
            @SerializedName("thumbnail")
            @Expose
            private Thumbnail thumbnail;
            @SerializedName("resourceURI")
            @Expose
            private String resourceURI;
            @SerializedName("comics")
            @Expose
            private Comics comics;
            @SerializedName("series")
            @Expose
            private Series series;
            @SerializedName("stories")
            @Expose
            private Stories stories;
            @SerializedName("events")
            @Expose
            private Events events;
            @SerializedName("urls")
            @Expose
            private List<Url> urls = new ArrayList<Url>();

            /**
             * @return The id
             */
            public Integer getId() {
                return id;
            }

            /**
             * @param id The id
             */
            public void setId(Integer id) {
                this.id = id;
            }

            /**
             * @return The name
             */
            public String getName() {
                return name;
            }

            /**
             * @param name The name
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * @return The description
             */
            public String getDescription() {
                return description;
            }

            /**
             * @param description The description
             */
            public void setDescription(String description) {
                this.description = description;
            }

            /**
             * @return The modified
             */
            public String getModified() {
                return modified;
            }

            /**
             * @param modified The modified
             */
            public void setModified(String modified) {
                this.modified = modified;
            }

            /**
             * @return The thumbnail
             */
            public Thumbnail getThumbnail() {
                return thumbnail;
            }

            /**
             * @param thumbnail The thumbnail
             */
            public void setThumbnail(Thumbnail thumbnail) {
                this.thumbnail = thumbnail;
            }

            /**
             * @return The resourceURI
             */
            public String getResourceURI() {
                return resourceURI;
            }

            /**
             * @param resourceURI The resourceURI
             */
            public void setResourceURI(String resourceURI) {
                this.resourceURI = resourceURI;
            }

            /**
             * @return The comics
             */
            public Comics getComics() {
                return comics;
            }

            /**
             * @param comics The comics
             */
            public void setComics(Comics comics) {
                this.comics = comics;
            }

            /**
             * @return The series
             */
            public Series getSeries() {
                return series;
            }

            /**
             * @param series The series
             */
            public void setSeries(Series series) {
                this.series = series;
            }

            /**
             * @return The stories
             */
            public Stories getStories() {
                return stories;
            }

            /**
             * @param stories The stories
             */
            public void setStories(Stories stories) {
                this.stories = stories;
            }

            /**
             * @return The events
             */
            public Events getEvents() {
                return events;
            }

            /**
             * @param events The events
             */
            public void setEvents(Events events) {
                this.events = events;
            }

            /**
             * @return The urls
             */
            public List<Url> getUrls() {
                return urls;
            }

            /**
             * @param urls The urls
             */
            public void setUrls(List<Url> urls) {
                this.urls = urls;
            }


            public class Events {

                @SerializedName("available")
                @Expose
                private Integer available;
                @SerializedName("collectionURI")
                @Expose
                private String collectionURI;
                @SerializedName("items")
                @Expose
                private List<Object> items = new ArrayList<Object>();
                @SerializedName("returned")
                @Expose
                private Integer returned;

                /**
                 * @return The available
                 */
                public Integer getAvailable() {
                    return available;
                }

                /**
                 * @param available The available
                 */
                public void setAvailable(Integer available) {
                    this.available = available;
                }

                /**
                 * @return The collectionURI
                 */
                public String getCollectionURI() {
                    return collectionURI;
                }

                /**
                 * @param collectionURI The collectionURI
                 */
                public void setCollectionURI(String collectionURI) {
                    this.collectionURI = collectionURI;
                }

                /**
                 * @return The items
                 */
                public List<Object> getItems() {
                    return items;
                }

                /**
                 * @param items The items
                 */
                public void setItems(List<Object> items) {
                    this.items = items;
                }

                /**
                 * @return The returned
                 */
                public Integer getReturned() {
                    return returned;
                }

                /**
                 * @param returned The returned
                 */
                public void setReturned(Integer returned) {
                    this.returned = returned;
                }
            }


            public class Comics {

                @SerializedName("available")
                @Expose
                private Integer available;
                @SerializedName("collectionURI")
                @Expose
                private String collectionURI;
                @SerializedName("items")
                @Expose
                private List<Object> items = new ArrayList<Object>();
                @SerializedName("returned")
                @Expose
                private Integer returned;

                /**
                 * @return The available
                 */
                public Integer getAvailable() {
                    return available;
                }

                /**
                 * @param available The available
                 */
                public void setAvailable(Integer available) {
                    this.available = available;
                }

                /**
                 * @return The collectionURI
                 */
                public String getCollectionURI() {
                    return collectionURI;
                }

                /**
                 * @param collectionURI The collectionURI
                 */
                public void setCollectionURI(String collectionURI) {
                    this.collectionURI = collectionURI;
                }

                /**
                 * @return The items
                 */
                public List<Object> getItems() {
                    return items;
                }

                /**
                 * @param items The items
                 */
                public void setItems(List<Object> items) {
                    this.items = items;
                }

                /**
                 * @return The returned
                 */
                public Integer getReturned() {
                    return returned;
                }

                /**
                 * @param returned The returned
                 */
                public void setReturned(Integer returned) {
                    this.returned = returned;
                }
            }


            public class Series {

                @SerializedName("available")
                @Expose
                private Integer available;
                @SerializedName("collectionURI")
                @Expose
                private String collectionURI;
                @SerializedName("items")
                @Expose
                private List<Object> items = new ArrayList<Object>();
                @SerializedName("returned")
                @Expose
                private Integer returned;

                /**
                 * @return The available
                 */
                public Integer getAvailable() {
                    return available;
                }

                /**
                 * @param available The available
                 */
                public void setAvailable(Integer available) {
                    this.available = available;
                }

                /**
                 * @return The collectionURI
                 */
                public String getCollectionURI() {
                    return collectionURI;
                }

                /**
                 * @param collectionURI The collectionURI
                 */
                public void setCollectionURI(String collectionURI) {
                    this.collectionURI = collectionURI;
                }

                /**
                 * @return The items
                 */
                public List<Object> getItems() {
                    return items;
                }

                /**
                 * @param items The items
                 */
                public void setItems(List<Object> items) {
                    this.items = items;
                }

                /**
                 * @return The returned
                 */
                public Integer getReturned() {
                    return returned;
                }

                /**
                 * @param returned The returned
                 */
                public void setReturned(Integer returned) {
                    this.returned = returned;
                }
            }


            public class Stories {

                @SerializedName("available")
                @Expose
                private Integer available;
                @SerializedName("collectionURI")
                @Expose
                private String collectionURI;
                @SerializedName("items")
                @Expose
                private List<Object> items = new ArrayList<Object>();
                @SerializedName("returned")
                @Expose
                private Integer returned;

                /**
                 * @return The available
                 */
                public Integer getAvailable() {
                    return available;
                }

                /**
                 * @param available The available
                 */
                public void setAvailable(Integer available) {
                    this.available = available;
                }

                /**
                 * @return The collectionURI
                 */
                public String getCollectionURI() {
                    return collectionURI;
                }

                /**
                 * @param collectionURI The collectionURI
                 */
                public void setCollectionURI(String collectionURI) {
                    this.collectionURI = collectionURI;
                }

                /**
                 * @return The items
                 */
                public List<Object> getItems() {
                    return items;
                }

                /**
                 * @param items The items
                 */
                public void setItems(List<Object> items) {
                    this.items = items;
                }

                /**
                 * @return The returned
                 */
                public Integer getReturned() {
                    return returned;
                }

                /**
                 * @param returned The returned
                 */
                public void setReturned(Integer returned) {
                    this.returned = returned;
                }
            }


            public class Thumbnail {

                @SerializedName("path")
                @Expose
                private String path;
                @SerializedName("extension")
                @Expose
                private String extension;

                /**
                 * @return The path
                 */
                public String getPath() {
                    return path;
                }

                /**
                 * @param path The path
                 */
                public void setPath(String path) {
                    this.path = path;
                }

                /**
                 * @return The extension
                 */
                public String getExtension() {
                    return extension;
                }

                /**
                 * @param extension The extension
                 */
                public void setExtension(String extension) {
                    this.extension = extension;
                }
            }


            public class Url {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("url")
                @Expose
                private String url;

                /**
                 * @return The type
                 */
                public String getType() {
                    return type;
                }

                /**
                 * @param type The type
                 */
                public void setType(String type) {
                    this.type = type;
                }

                /**
                 * @return The url
                 */
                public String getUrl() {
                    return url;
                }

                /**
                 * @param url The url
                 */
                public void setUrl(String url) {
                    this.url = url;
                }

            }
        }
    }
}
