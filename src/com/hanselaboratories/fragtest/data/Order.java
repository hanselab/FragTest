package com.hanselaboratories.fragtest.data;

public class Order {
	int _id;
	String _description;
	int _handlingUnit;
	
	// Empty constructor 
	public Order() {
	}
	
	public Order(int id, String description, int handlingUnit) {
		this._id = id;
		this._description = description;
		this._handlingUnit = handlingUnit;
	}
	
	public int getID() {
		return this._id;
	}
	
	public String getDescription() {
		return this._description;
	}
	
	public int getHandlingUnit() {
		return this._handlingUnit;
	}
	
	public boolean setDescription(String newDescription) {
		this._description = newDescription;
		
		return true;
	}
}
