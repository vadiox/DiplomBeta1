package ua.study.english.other;

public class WordTranslEnt {
	
	String word;
	String translate;
	int id;
	
	//�������� ��� ������ ��� � �� ����������� �� ������ ������ � ����
	//word				����� �� �������� ���
	//translate			���� ��������
	//id				����� ����� � ���������� ����� ���
	
	//�������
	//word				absolutely
	//translate			����������, ����������
	//id				13
	
	public WordTranslEnt(){}
	
	public WordTranslEnt(String mWord, String mTranslate,int mId){
		this.word=mWord;
		this.translate=mTranslate;
		this.id=mId;

	}


	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
