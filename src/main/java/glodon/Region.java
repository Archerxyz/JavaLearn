package glodon;

public class Region {
	private String chineseName;
	private String englishName;
	
	public Region(){
		
	}
	public Region(String chineseName, String englishName){
		this.chineseName = chineseName;
		this.englishName = englishName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	@Override
	public String toString() {
		return "glodon.Region [chineseName=" + chineseName + ", englishName="
				+ englishName + "]";
	}
	
	
}
