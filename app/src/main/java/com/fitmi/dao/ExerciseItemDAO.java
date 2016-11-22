package com.fitmi.dao;

public class ExerciseItemDAO {
	
	private int exerciseId = 0;
	private String exerciseSum = "";
	private String exerciseName = "";
	
	public String getExerciseName() {
		return exerciseName;
	}
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	public String getExerciseSum() {
		return exerciseSum;
	}
	public void setExerciseSum(String exerciseSum) {
		this.exerciseSum = exerciseSum;
	}

}
