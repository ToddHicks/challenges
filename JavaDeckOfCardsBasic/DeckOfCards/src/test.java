String[] solution(String[][] queries) {
    String[] array = new String[queries.length];
    for(int i = 0; i < queries.length; ++i){
        if(queries[i][0].equals("SET_OR_INC")){
            array[i]=String.valueOf(setOrInc(queries[i][1],queries[i][2],Integer.valueOf(queries[i][3])));
        }
        else if(queries[i][0].equals("GET")){
            array[i]=String.valueOf(get(queries[i][1],queries[i][2]));
        }else if(queries[i][0].equals("DELETE")){
            array[i]=String.valueOf(delete(queries[i][1],queries[i][2]));
        }
        else if(queries[i][0].equals("TOP_N_KEYS")){
            array[i]=String.valueOf(top_n_keys(Integer.valueOf(queries[i][1])));
        }
        else if(queries[i][0].equals("LOCK")){
            array[i]=String.valueOf(lock(queries[i][1],queries[i][1]));
        }
    }
    return array;
}
HashMap<String,Integer> counter = new HashMap<String,Integer>();
HashMap<String,HashMap<String,Integer>> maps = new HashMap<String, HashMap<String,Integer>>();
HashMap<String,String> locks = new HashMap<String,String>();
private int setOrInc(String key, String field, int value){
    if(maps.get(key) != null){
        if(maps.get(key).get(field) != null){
            maps.get(key).put(field,  maps.get(key).get(field) + value);
            counter.put(key, counter.get(key)+1);
            return maps.get(key).get(field);
        }
        else{
            maps.get(key).put(field, value);
            counter.put(key, counter.get(key)+1);
            return value;
        }
    }
    else {
        HashMap<String,Integer> temp = new HashMap<String,Integer>();
        temp.put(field,value);
        maps.put(key, temp);
        counter.put(key, 1);
        return value;
    }
}
private String get(String key, String field){
    if( maps.get(key) == null){
        return "";
    }
    else if( maps.get(key).get(field) == null){
        return "";
    }
    else {
        return String.valueOf(maps.get(key).get(field));
    }
}
private boolean delete(String key, String field){
    if(maps.get(key) == null){
        return false;
    }
    else{
        if(maps.get(key).get(field) == null){
            return false;
        }
        else {
            maps.get(key).remove(field);
            if (maps.get(key).size() == 0){
                maps.remove(key);
                counter.remove(key);
            }
            else{
                counter.put(key, counter.get(key)+1);
            }
            return true;
        }
    }
}
private String lock(String person, String key){
    if(locks.get(key)==null){
        locks.put(key, person);
        return "acquired";
    }
}
private String top_n_keys(int n){
    List<Integer> values = new LinkedList<Integer>();
    counter.forEach((key, val)->{
        values.add(val);
    });
    Collections.sort(values, Collections.reverseOrder());
    System.out.println(values);
    String returnValue = "";
    int count = 0;
    for(int index = 0; index < counter.size(); index=count){
        System.out.println("HERE!");
        List<String> nextValue = new LinkedList<String>();
        for(Map.Entry<String, Integer> entry : counter.entrySet()){
            String key = entry.getKey();
            Integer val = entry.getValue();
            System.out.println(entry);
            System.out.println(val + " : " + values.get(index));
            if(val == values.get(index)){
                nextValue.add(key + "("+ val +")");
            }
        }
        Collections.sort(nextValue);
        if(nextValue.size() + count < n){
            for(String val : nextValue){
                count = count+1;
                if(returnValue.equals("")){
                    returnValue = val;
                }
                else{
                    returnValue = returnValue + ", " + val;
                }
            }
        }
        else if(nextValue.size() + count == n){
            for(String val : nextValue){
                count = count+1;
                if(returnValue.equals("")){
                    returnValue = val;
                }
                else{
                    returnValue = returnValue + ", " + val;
                }
            }
            return returnValue;
        }
        else{
            for(String v : nextValue){
                if(count < n){
                    if(returnValue.equals("")){
                        count = count+1;
                        returnValue = v;
                    }
                    else{
                        count = count+1;
                        returnValue = returnValue + ", " + v;
                    }
                }
                else{
                    return returnValue;
                }
            }
        }
    }
    return returnValue;
}