package entity;

public class Seat {
	
	private int seat_id;
	private boolean status;
	private String information;

	
	public int getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(int seat_id) {
		this.seat_id = seat_id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Seat(int seat_id, boolean status, String information) {
		super();
		this.seat_id = seat_id;
		this.status = status;
		this.information = information;
	}
	
	@Override
	public String toString() {
		return information;
	}
			

}
