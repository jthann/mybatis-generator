package com.jd.panda.code.tools;

public class FieldVO {
	private String name;
	private String fieldName;
	private String type;
	private Integer fieldSize;
	private String comment;
	private boolean notNull;

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public boolean isNotNull() {
		return this.notNull;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public Integer getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(Integer fieldSize) {
		this.fieldSize = fieldSize;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", fieldName=" + fieldName + ", type="
				+ type + ", fieldSize=" + fieldSize + ", comment=" + comment
				+ ", notNull=" + notNull + "]";
	}
}
