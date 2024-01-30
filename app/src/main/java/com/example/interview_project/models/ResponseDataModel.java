package com.example.interview_project.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseDataModel {
    @SerializedName("page")
    private int page;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("data")
    private List<UserDataModel> users;

    public ResponseDataModel() {
    }

    public ResponseDataModel(int page, int perPage, int total, int totalPages, List<UserDataModel> users) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.users = users;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserDataModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserDataModel> users) {
        this.users = users;
    }
}
