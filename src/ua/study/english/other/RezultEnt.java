package ua.study.english.other;

public class RezultEnt {
	String date;
	long rezult;
	
	public RezultEnt(){}
	
	//�������� ��� ������ ��������� �� ������ ������ � ����
	// date				���� ����������� ����� �� ����� ����������� ������
	// rezult			������� ����� �������� �����������
	
	//�������--------
	// date					28/05/14 		dd/MM/yy
	// rezult				5  (min 0, max 100)
	
	public RezultEnt(String mDate, long mRezult){
		this.date=mDate;
		this.rezult=mRezult;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getRezult() {
		return rezult;
	}

	public void setRezult(long l) {
		this.rezult = l;
	}

}
