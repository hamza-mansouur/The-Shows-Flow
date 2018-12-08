package com.example.macbook.theshowsflow.Models;

import com.example.macbook.theshowsflow.Models.Show;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowResponse {

        @SerializedName("page")
        @Expose
        private int page;

        @SerializedName("total_results")
        @Expose
        private int totalResults;

        @SerializedName("results")
        @Expose
        private List<Show> shows;

        @SerializedName("total_pages")
        @Expose
        private int totalPages;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public List<Show> getShows() {
            return shows;
        }

        public void setShows(List<Show> shows) {
            this.shows = shows;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

}
