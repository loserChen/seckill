package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class SeckillServiceTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() {
        long seckillId=1000;
        Seckill seckill=seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
    }
    //完整逻辑代码测试，注意可重复执行
//    @Test
//    public void testSeckillLogic() {
//        long seckillId=1000;
//        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
//        if(exposer.isExposed()){
//            logger.info("exposer={}"+exposer);
//            long userPhone=13476191896L;
//            String md5=exposer.getMd5();
//            try{
//                SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId,userPhone,md5);
//                logger.info("result={}",seckillExecution);
//            }catch (RepeatKillException e){
//                logger.error(e.getMessage());
//            }catch (SeckillCloseException e1){
//                logger.error(e1.getMessage());
//            }
//        }else{
//            //秒杀未开启
//            logger.warn("exposer={}",exposer);
//        }
//    }
    @Test
    public void executeSeckillProcedure(){
        long seckillId=1003;
        long phone=1368011101;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution seckillExecution=seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }
}