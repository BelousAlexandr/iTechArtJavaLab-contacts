package by.belous.contacts.entity;

public class Paging {

    private Integer page;

    private Integer pageSize;

    private Integer recordsSize;

    private Integer pagesCount;

    public Paging() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecordsSize() {
        return recordsSize;
    }

    public void setRecordsSize(Integer recordsSize) {
        this.recordsSize = recordsSize;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }
}
