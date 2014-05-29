package ua.study.english.other;

public class AsocCardEnt {
	
	String img;
	String var1;
	String var2;
	String var3;
	String var4;
	String right_ans;
	
	public AsocCardEnt(){}
	
	public AsocCardEnt(String mImg, String mVar1, String mVar2, String mVar3, String mVar4, String mRight_ans){
		this.img = mImg;
		this.var1 = mVar1;
		this.var2 = mVar2;
		this.var3 = mVar3;
		this.var4 = mVar4;
		this.right_ans = mRight_ans;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public String getVar3() {
		return var3;
	}

	public void setVar3(String var3) {
		this.var3 = var3;
	}

	public String getVar4() {
		return var4;
	}

	public void setVar4(String var4) {
		this.var4 = var4;
	}

	public String getRight_ans() {
		return right_ans;
	}

	public void setRight_ans(String right_ans) {
		this.right_ans = right_ans;
	}

}
