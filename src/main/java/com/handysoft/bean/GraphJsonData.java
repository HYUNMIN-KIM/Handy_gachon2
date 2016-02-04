package com.handysoft.bean;




public class GraphJsonData {
	private String date;
	private int conditionPoint;
	private float caloriePoint;
	private GraphJsonConditionData conditionData;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getConditionPoint() {
		return conditionPoint;
	}
	public void setConditionPoint(int conditionPoint) {
		this.conditionPoint = conditionPoint;
	}
	public float getCaloriePoint() {
		return caloriePoint;
	}
	public void setCaloriePoint(float caloriePoint) {
		this.caloriePoint = caloriePoint;
	}
	public GraphJsonConditionData getConditionData() {
		return conditionData;
	}
	public void setConditionData(GraphJsonConditionData conditionData) {
		this.conditionData = conditionData;
	}
	
	
}
