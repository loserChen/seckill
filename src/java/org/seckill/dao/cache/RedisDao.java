package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public RedisDao(String ip,int port) {
        jedisPool=new JedisPool(ip,port);
    }
    private RuntimeSchema<Seckill> runtimeSchema=RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckillId;
                //并没有实现内部序列化操作
                //get-> byte[] -> 反序列化 ->Object(Seckill)
                //采用自定义序列化
                //protostuff ：pojo
                byte[] bytes=jedis.get(key.getBytes());
                //缓存重获取
                if(bytes!=null){
                    Seckill seckill=runtimeSchema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,runtimeSchema);
                    //seckill被反序列化
                    return seckill;
                }
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        //set Object(seckill)-> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, runtimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
