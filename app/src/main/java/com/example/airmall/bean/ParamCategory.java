package com.example.airmall.bean;

import java.util.List;

public class ParamCategory {
    private Long id;

    private String name;

    private List<ParamDesc> paramDescList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParamDesc> getParamDescList() {
        return paramDescList;
    }

    public void setParamDescList(List<ParamDesc> paramDescList) {
        this.paramDescList = paramDescList;
    }
}
