package com.fenxiangz.java.jmh;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**  
 *   
 * @Description: 	TODO   
 * @author:  		my    
 * @date:   		2018-08-22   
 * @version V1.0
 *
 */
public class MapUtil {
	
	/**
	 * 
	 * @Title: sortByValueJava7   
	 * @Description: 按照value排序 
	 * @param: @param map
	 * @param: @return      
	 * @return: Map<K,V>      
	 * @throws
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueJava7(Map<K, V> map) {
		List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 
	 * @Title: sortByValueJava8   
	 * @Description: TODO  
	 * @param: @param map
	 * @param: @param reversed
	 * @param: @return      
	 * @return: Map<K,V>      
	 * @throws
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueJava8(Map<K, V> map, boolean reversed) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Entry<K, V>> st = map.entrySet().stream();
		if(reversed) {
			// 倒序
			st.sorted(Comparator.comparing(e -> ((Entry<K, V>) e).getValue()).reversed()).forEach(e -> result.put(e.getKey(), e.getValue()));
		} else {
			st.sorted(Comparator.comparing(e -> ((Entry<K, V>) e).getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
		}

		return result;
	}
	
	
}
