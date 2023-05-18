package com.sunmote.common.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class QueryPageBean implements Serializable {
    private Integer page;//页码
    private Integer limit;//页大小
    private String sortOrder;
    private String sort;
    private Integer offset;
    private String start;
    private String end;

    public QueryPageBean(Integer page, Integer limit, String sort, Integer offset) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.offset = offset;
    }
}