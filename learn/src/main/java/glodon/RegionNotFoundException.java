package glodon;

public class RegionNotFoundException extends Exception {

	private String region;
	public RegionNotFoundException(String region){
		super("cannot find region " + region);
		this.region = region;
	}
}
