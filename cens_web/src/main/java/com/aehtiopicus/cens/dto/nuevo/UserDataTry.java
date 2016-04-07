package com.aehtiopicus.cens.dto.nuevo;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

public class UserDataTry {

	public static void main(String args[]){
		String testData[] = {				
				"a-b.c-dd.x-f-g-h",
				"a.b-c.c",
				"a.b-c.c1",
				"a.b-c.c2",
				"a.b-c.c3",
				"a.b-c.c4",
				"a.b-c.c5",
				"a.b-c.c.d",
				"a.b-c.c.x",
				"a.b-f.c.1",
				"a-b",
				"a",
				"b",
				"a.x-f",
				"a.x-f.1",
				"m"};
		
		new UserDataTry(testData);
	}
	public UserDataTry(String[] data){
		
		UserDataDb usdb = new UserDataDb();

		
		for(String datas : data){
			usdb.putValues(datas);			
		}
		
		//get normalito
		test("a.b-c.c.c1.c2.c3.c4.c5",usdb,0);
		test("a.b-c.c1.c2.c3.c4.c6",usdb,1);
		test("a.b-c.c.d",usdb,2);
		test("a.b-c.c",usdb,3);
		test("a-b.c-dd.x-f-g-h",usdb,4);
		test("a.x",usdb,5);
		test("a.b-c",usdb,6);
		test("a.b-f",usdb,7);
		test("a",usdb,8);
		
		//return  
		
		testReturn (test("a.b-c.c.c1.c2.c3.c4.c5",usdb,0));
		testReturn (test("a.b-c.c1.c2.c3.c4.c6",usdb,1));
		testReturn (test("a.b-c.c.d",usdb,2));
		testReturn (test("a.b-c.c",usdb,3));
		testReturn (test("a-b.c-dd.x-f-g-h",usdb,4));
		testReturn (test("a.x",usdb,5));
		testReturn (test("a.b-c",usdb,6));
		testReturn (test("a.b-f",usdb,7));
		testReturn (test("a",usdb,8));
		testReturn (test("m",usdb,9));
		
		//put
		try {
			put(usdb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testReturn(Map<String,Object> data){
		Map<String,Object> result = UserDataUtils.assembleResultData(data);
		if(result != null){
			System.out.println(result);
		}else{
			System.out.println("no data");	
		}
	}
	
	private Map<String,Object> test(String testString,UserDataDb usdb,int nro){
		Map<String,Object> resultd = new HashMap<>();
		try{
			for(String result : UserDataUtils.assembleRequest(testString,new ArrayList<String>(usdb.getCallData().keySet()),false)){
				resultd.put(result, UUID.randomUUID().toString());
				System.out.println(result);
			}
			System.out.println(nro +" success ");
		}catch(Exception e){
			System.out.println(nro +" fail");
			System.out.println(e.getMessage());
			return null;
			
		}
		return resultd;
	}
	
	private void put(UserDataDb usdb) throws Exception{
		String data = "{" +
			"\"b\": 0,"+
		    "\"a\": {"+ 
		    "\"a\": 1,"+
		    "\"bC\": {"+
		    		"\"c\": {"+
		    		"\"c\": 2,"+
		    		"\"d\": 3,"+
		    		"\"x\": 4"+
		            "},"+
		            "\"c1\": 5,"+
		            "\"c2\": 6,"+
		            "\"c3\": 7,"+
		            "\"c4\": 8,"+
		            "\"c5\": 9"+
		        "}"+
		  "}"+
		"}";
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> dataMap = mapper.readValue(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), Map.class);
		
		UserDataUtils.assemblePutData(dataMap, usdb);
	}
}
	
class UserDataUtils{
	/**Put*/
	public static Map<String,Object> assemblePutData(Map<String,Object> putData,UserDataDb usdb) throws Exception{
		Map<String,Object> result = null;
		if(putData != null && !putData.isEmpty()){
			result = new HashMap<String, Object>();
			for(Entry<String,Object> putDataEntry : putData.entrySet()){
				handleMapAdding(result,generateCategoryData(putDataEntry.getKey(),putDataEntry.getValue(),null));
			}
		}
		
		return replaceKey(result,usdb);
	}
	
	private static Map<String,Object> replaceKey(Map<String,Object> dataMap, UserDataDb usdb) throws Exception{
		Map<String,Object> result = null;
		if(dataMap != null && !dataMap.isEmpty()){
			result = new HashMap<String, Object>();
			for(Entry<String,Object> dataMapEntry : dataMap.entrySet()){
				if(usdb.getData().containsKey(dataMapEntry.getKey())){
					result.put(usdb.getData().get(dataMapEntry.getKey()), dataMapEntry.getValue());
				}else{
					assembleRequest(dataMapEntry.getKey(),new ArrayList<String>(usdb.getCallData().keySet()),false);
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> generateCategoryData(String key, Object value,String categoryKey){
		
		if(categoryKey == null){
			categoryKey = new String(key);
		}
		if(value instanceof Map){
			Map<String,Object> resulFinalMap = new HashMap<String, Object>();
			for (Entry<String,Object> entryData : ((Map<String,Object>)value).entrySet()){
				if(categoryKey.equals(key)){
					handleMapAdding(resulFinalMap,generateCategoryData(entryData.getKey(),entryData.getValue(),categoryKey));
				}else{
					handleMapAdding(resulFinalMap,generateCategoryData(entryData.getKey(),entryData.getValue(),categoryKey+"."+key));
				}
				
			}
			return resulFinalMap;
			
		}else{
			Map<String,Object> resultMap = new HashMap<String, Object>();
			if(categoryKey.equals(key)){
				resultMap.put(categoryKey, value.toString());	
			}else if(categoryKey.substring(categoryKey.lastIndexOf(".")+1).equals(key)){
				resultMap.put(categoryKey, value.toString());
			}else{
				resultMap.put(categoryKey+"."+key, value.toString());
			}
			
			return resultMap;
		}
	}
	
	/**
	 * This method handles the addAll issue
	 * @param result
	 * @param target
	 */
	private static void handleMapAdding(Map<String,Object>result, Map<String,Object> target){
		for(Entry<String,Object> targetEntry : target.entrySet()){
			result.put(targetEntry.getKey(), targetEntry.getValue());
		}
	}
	
	/**Return*/
	public static Map<String,Object> assembleResultData(Map<String,Object> dbData){
		if(dbData!=null && !dbData.isEmpty()){
			Map<String,Object> result = new HashMap<String, Object>();		
			for(Entry<String,Object> dbDataEntry : dbData.entrySet()){
				generateResultEntry(dbDataEntry.getKey(),dbDataEntry.getValue(),result);				
			}
			return result;
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private static void generateResultEntry(String resultPath, Object resultPathData, Map<String,Object> result){
		StringTokenizer st  = new StringTokenizer(resultPath,".");			
		String cat = capitalize(st.nextToken());
		if(result.containsKey(cat)){
			Object attr = result.get(cat);			
			if(attr instanceof Map){
				if(st.hasMoreElements()){
					generateResultEntry(resultPath.substring(cat.length()+1),resultPathData,(convertInstanceOfObject(attr,Map.class)));
				}else{
					generateResultEntry(resultPath,resultPathData,(convertInstanceOfObject(attr,Map.class)));
				}
				
			}else{				
				if(st.hasMoreTokens()){
					HashMap<String,Object> newCategory = new HashMap<String, Object>();
					newCategory.put(cat, attr);
					result.put(cat, newCategory );
					generateResultEntry(resultPath.substring(cat.length()+1),resultPathData,newCategory);
				}else{					
					//this case should never be triggered this means two same records have been retrieved from db.
					result.put(cat,  resultPathData);
				}				
			}
		}else{
			if(st.hasMoreTokens()){
				Map<String,Object> newCategory = new HashMap<String, Object>();
				result.put(cat, newCategory );
				generateResultEntry(resultPath.substring(cat.length()+1),resultPathData,newCategory);
			}else{
				result.put(cat,  resultPathData);
			}
			
		}
		
	}
	
	private static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
	    try {
	        return clazz.cast(o);
	    } catch(ClassCastException e) {
	        return null;
	    }
	}
	
	
	
	/**GET*/
		public static String capitalize(String data){
			while(data.contains("-")){
				int index = data.indexOf("-");
				String replaceMe = data.substring(index+1,index+2).toUpperCase();
				data = data.replace(data.substring(index,index+2), replaceMe);			
			}
			return data;
		}
		
		/**
		 * 
		 * @param request
		 * @param callData
		 * @return
		 * @throws Exception
		 */
		public static List<String> assembleRequest(String request, List<String> callDataRequest,boolean inner) throws Exception{
			List<String> callData = new ArrayList<String>(callDataRequest);
			List<String> resultData = new ArrayList<String>();
			//same cat same attr
			addMatchingData(callData,request,resultData);

		    if(resultData.isEmpty() && !inner){
				//check cats and split attributes no sub empty cats allowed
				if(request.contains(".")){
					
					assembleAttributeData(callData,resultData,request);				
						
					if(resultData.isEmpty()){
						throw new Exception("Invalid category '"+request+"'");
					}
				}else if(resultData.isEmpty()){
					throw new Exception("Invalid category '"+request+"'");
				}
		    }

		return resultData;	
	}
		
	private static void assembleAttributeData(List<String> callData,List<String> resultData,String request)throws Exception{
		List<String> subAttributes = new ArrayList<String>();
		String requestExpression = request;
		do{
			String subString = requestExpression.substring(0,requestExpression.lastIndexOf("."));
			subAttributes.add(requestExpression.replace(subString+".", ""));
			requestExpression = subString;
			//cat found and attr found
			if(callData.contains(requestExpression)){
				List<String> resultDataMin = new ArrayList<String>();
				resultDataMin.add(requestExpression);
				callData.remove(requestExpression);
				for(String sa : subAttributes){
					String catAndAttr = requestExpression.substring(0,requestExpression.lastIndexOf(".")+1)+sa;
					if(callData.contains(catAndAttr)){
						resultDataMin.add(catAndAttr);	
						callData.remove(catAndAttr);
					}else{
						if(requestExpression.indexOf(".")==-1){
							throw new Exception("Attribute '"+sa+"' not present under '"+requestExpression+"' category");
						}else{
							throw new Exception("Attribute '"+sa+"' not present under '"+requestExpression.substring(0,requestExpression.lastIndexOf("."))+"' category");
						}
					}
					
				}	
				resultData.addAll(appendRecursionResult(resultDataMin, callData));
				break;
			}
		}while((requestExpression.length() - requestExpression.replace(".", "").length())>1);
	}
		
	private static void addMatchingData(List<String> callData, String request, List<String>resultData){
		if(callData.contains(request)){				
			resultData.add(request);
			callData.remove(request);
		}

		Iterator<String> it = callData.iterator();
	    while (it.hasNext()) {
	    	String nextIt = it.next();		    	
	    	if(nextIt.startsWith(request) &&  nextIt.replace(request, "").startsWith(".")){
				resultData.add(nextIt);
				it.remove();
			}
	    }
	}
	private static List<String> appendRecursionResult(List<String> resultDataMin,List<String> callData) throws Exception{
		List<String> resultDataMin2 = new ArrayList<String>();
		for(String rdm : resultDataMin){
			for(String result:  assembleRequest(rdm,callData,true)){
				resultDataMin2.add(result);
			}
		}
		resultDataMin.addAll(resultDataMin2);
		return resultDataMin;
	}
}
	
	
class UserDataDb {
		
		
		
		private Map<String,String> callData = new HashMap<String, String>();
		private Map<String,String> data = new HashMap<String, String>();

		public Map<String, String> getCallData() {
			return callData;
		}
		
	
		public Map<String, String> getData() {
			return data;
		}


		public void putValues(String callData){
			String capData = UserDataUtils.capitalize(callData);
			this.callData.put(callData, capData);
			this.data.put(capData, callData);
		}
		
		
		
	}

