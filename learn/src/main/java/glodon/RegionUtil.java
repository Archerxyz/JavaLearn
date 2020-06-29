package glodon;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 地区中英文转化
 * @author hanll-a
 *
 */
public class RegionUtil {
	public static Map<String, String> regionChineseToEnglishMap = new HashMap<String, String>();
	public static Map<String, String> regionEnglishToChineseMap = new HashMap<String, String>();

	private static final int MARKUP = 1;
	private static final int READONLY = 1;
	private static final String REGION_CN_EN= "region_cn_en.properties";
	
	private static void init(){
		if (regionChineseToEnglishMap.isEmpty()){
			Properties props = new Properties();
			InputStream input = RegionUtil.class.getResourceAsStream("/"+REGION_CN_EN);
			try {
				props.load(new InputStreamReader(input, "UTF-8"));
				Enumeration en = props.propertyNames();
				while (en.hasMoreElements()) {
		              String key = (String) en.nextElement();
		              String Property = props.getProperty(key).trim();
		              regionChineseToEnglishMap.put(key, Property);
		              regionEnglishToChineseMap.put(Property, key);
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Region region(String regionStr) throws RegionNotFoundException {
		init();
		
		if (StringUtils.isEmpty(regionStr)){
			throw new RegionNotFoundException("region is null");
		}
		// 大写的
		String regionUpper = regionStr.toUpperCase();
		if (regionChineseToEnglishMap.containsKey(regionUpper)){
			return new Region(regionUpper, regionChineseToEnglishMap.get(regionUpper));
		}
		else if (regionEnglishToChineseMap.containsKey(regionUpper)){
			return new Region(regionEnglishToChineseMap.get(regionUpper), regionUpper);
		}
		throw new RegionNotFoundException(regionStr);
		//return new glodon.Region(regionStr, regionStr);
	}
	
	/**
	 * 获取所有地区
	 * @return
	 */
	public static List allRegions(){
		init();
		List regions = new ArrayList<Region>();
		for (String regionStr : regionChineseToEnglishMap.keySet()){
			regions.add(new Region(regionStr, regionChineseToEnglishMap.get(regionStr)));
		}
		return regions;
	}
	
	
	public static Map<String, String> getCNENRegions(String enRegions){
		init();
		Map<String, String> cnenRegions = new HashMap<String, String>();
		if ( enRegions!=null && !enRegions.isEmpty()) {
			String enRegionsUpper = enRegions.toUpperCase();
			String[] enRegionArr = enRegionsUpper.split(",");
			for (String regionStr : enRegionArr) {
				if (regionEnglishToChineseMap.containsKey(regionStr)){
					cnenRegions.put(regionStr, regionEnglishToChineseMap.get(regionStr));
				}
			} 
		}
		return cnenRegions;
	}
	
	/**
	 * 中文
	 * @param region
	 * @return
	 */
	public static String chinese(String region){
		try {
			Region regionDsc = region(region);
			return regionDsc.getChineseName();
		} catch (RegionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return region;
	}
	
	/**
	 * 英文
	 * @param region
	 * @return
	 */
	public static String english(String region){
		try {
			Region regionDsc = region(region);
			return regionDsc.getEnglishName();
		} catch (RegionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return region;
	}

	public static void main(String []arg){
		try {
			System.out.println(region("hebei"));
			
			System.out.println(region("河北"));
		} catch (RegionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
}
