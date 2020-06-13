package com.kgc.hyb.walletprovider.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import wallet.bean.FinanceHistory;
import wallet.bean.Temfinance;

import javax.annotation.Resource;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    public RedisUtils(RedisTemplate<Object,Object> redisTemplate){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate=redisTemplate;
    }
    private RedisTemplate redisTemplate;
//    @Autowired//按照类型去注入值
//    private RedisTemplate redisTemplate;
//设置
    public void set(String key,Object value){

        ValueOperations<String,Object> ops=redisTemplate.opsForValue();
        ops.set(key,value);
        //ops.set(key,value ,300, TimeUnit.SECONDS);
    }
    public Object get(String key){
        ValueOperations<String,Object> ops=redisTemplate.opsForValue();
        return  ops.get(key);
    }
    public void delete(String key){
        redisTemplate.delete(key);
    }

    public void setSms(String key,Object value){
        ValueOperations<String,Object> ops=redisTemplate.opsForValue();
        ops.set(key,value);
    }
    public  Object getSms(String key){
        ValueOperations<String,Object> ops=redisTemplate.opsForValue();
        return ops.get(key);
    }
    public void deleteSms(String key){
        redisTemplate.delete(key);
    }



    /**
     * 给redis设置过期时间
     * @param key
     * @param value
     * @param time
     */
    public void setTime(String key,Object value,int time){
        ValueOperations<String,Object> ops=redisTemplate.opsForValue();
//        set(K key, V value, long timeout, TimeUnit unit)
//        设置变量值的过期时间。
        ops.set(key,value ,time, TimeUnit.HOURS);
    }


    public void setTemfinance(Temfinance value){
        ValueOperations<String,Object> vo=redisTemplate.opsForValue();
        String key=value.getUid()+""+value.getFinid()+ UUID.randomUUID().toString();
        System.out.println("key:"+key);
        vo.set(key, value,15, TimeUnit.MINUTES);
    }

    public Set<Object> getTemfinance(Long uid){
        return redisTemplate.keys(uid+"*");
    }





    /***
     * 加锁的方法
     * @return
     */
    public boolean lock(String key){
        key="lock:"+key;
        RedisConnection redisConnection=redisTemplate.getConnectionFactory().getConnection();
        //设置序列化方法
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        if(redisConnection.setNX(key.getBytes(),new byte[]{1})){
            redisTemplate.expire(key,10,TimeUnit.SECONDS);
            redisConnection.close();
            return true;
        }else{
            redisConnection.close();
            return false;
        }
    }
    /***
     * 解锁的方法
     * @param key
     */
    public void unLock(String key){
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.delete(key);
    }

    public Object getSession(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public void deleteSession(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 给redis设置过期时间
     *
     * @param key
     * @param value
     */
    public void setSession(String key, Object value) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//        set(K key, V value, long timeout, TimeUnit unit)
//        设置变量值的过期时间。
        ops.set(key, value);
    }


//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    RedisTemplate<Object, Object> redisTemplate;
//
//    @Resource(name = "stringRedisTemplate")
//    ValueOperations<String, String> valOpsStr;
//
//    @Resource(name = "redisTemplate")
//    ValueOperations<Object, Object> valOpsObj;
//
//    /**
//     * 根据指定key获取String
//     *
//     * @param key
//     * @return
//     */
//    public String getStr(String key) {
//        return valOpsStr.get(key);
//    }
//
//    /**
//     * 设置Str缓存
//     *
//     * @param key
//     * @param val
//     */
//    public void setStr(String key, String val) {
//        valOpsStr.set(key, val);
//    }
//
//    /***
//     * 设置Str缓存
//     * @param key
//     * @param val
//     * @param expire 超时时间
//     */
//    public void setStr(String key, String val, Long expire) {
//        valOpsStr.set(key, val, expire, TimeUnit.MINUTES);
//    }
//
//    /**
//     * 删除指定key
//     *
//     * @param key
//     */
//    public void del(String key) {
//        stringRedisTemplate.delete(key);
//    }
//
//    /**
//     * 根据指定o获取Object
//     *
//     * @param o
//     * @return
//     */
//    public Object getObj(Object o) {
//        return valOpsObj.get(o);
//    }
//
//    /**
//     * 设置obj缓存
//     *
//     * @param o1
//     * @param o2
//     */
//    public void setObj(Object o1, Object o2) {
//        valOpsObj.set(o1, o2);
//    }
//
//    /**
//     * 删除Obj缓存
//     *
//     * @param o
//     */
//    public void delObj(Object o) {
//        redisTemplate.delete(o);
//    }
}