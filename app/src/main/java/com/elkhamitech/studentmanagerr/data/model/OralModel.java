package com.elkhamitech.studentmanagerr.data.model;

/**
 * Created by ElkhamiTech on 2/12/2018.
 */

public class OralModel extends ExamsModel{

    private long row_id;
    private String value;
    private String valueFrom;
    private long subjectOral;

    public OralModel() {
    }

    public long getRow_id() {
        return row_id;
    }

    public void setRow_id(long row_id) {
        this.row_id = row_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public long getSubjectOral() {
        return subjectOral;
    }

    public void setSubjectOral(long subjectOral) {
        this.subjectOral = subjectOral;
    }
}
