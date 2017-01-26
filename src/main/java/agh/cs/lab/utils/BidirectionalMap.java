package agh.cs.lab.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//from http://stackoverflow.com/a/7834138

public class BidirectionalMap<KeyType, ValueType>{
    private final Map<KeyType, ValueType> keyToValueMap = new ConcurrentHashMap<>();
    private final Map<ValueType, KeyType> valueToKeyMap = new ConcurrentHashMap<>();

    synchronized public void put(final KeyType key, final ValueType value){
        keyToValueMap.put(key, value);
        valueToKeyMap.put(value, key);
    }

    synchronized public ValueType removeByKey(final KeyType key){
        final ValueType removedValue = keyToValueMap.remove(key);
        valueToKeyMap.remove(removedValue);
        return removedValue;
    }

    synchronized public KeyType removeByValue(final ValueType value){
        final KeyType removedKey = valueToKeyMap.remove(value);
        keyToValueMap.remove(removedKey);
        return removedKey;
    }

    public boolean containsKey(final KeyType key){
        return keyToValueMap.containsKey(key);
    }

    public boolean containsValue(final ValueType value){
        return keyToValueMap.containsValue(value);
    }

    public KeyType getKey(final ValueType value){
        return valueToKeyMap.get(value);
    }

    public ValueType get(final KeyType key){
        return keyToValueMap.get(key);
    }

    public Set<KeyType> keySet() {
        return keyToValueMap.keySet();
    }

    public Set<ValueType> valueSet() {
        return valueToKeyMap.keySet();
    }
}