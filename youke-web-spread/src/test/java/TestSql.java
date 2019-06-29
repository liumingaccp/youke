import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import youke.web.spread.Application;
import youke.web.spread.bean.PageBean;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.utils.DateUtil;
import youke.web.spread.common.utils.IDUtil;
import youke.web.spread.entity.*;
import youke.web.spread.service.biz.UserSpreadBiz;
import youke.web.spread.service.jms.producer.MQProducer;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)// 指定spring-boot的启动类
public class TestSql {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserSpreadBiz userSpreadBiz;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入一条记录
     */
    @Test
    public void TestSave(){
        System.out.println("保存成功");
    }

    @Test
    public void SaveOrder(){
        userSpreadBiz.doTakeMoney("EAUHv7r",1000);
    }

    @Test
    public void SaveCommis(){
        TUserCommission commission = new TUserCommission();
        commission.setCommission(100000);
        commission.setCommissionFlag("Bb");
        commission.setCommissionId("WUFdnER");
        commission.setCommissionMobile("18688491561");
        commission.setCommissionRegisterTime(new Date());
        commission.setCommissionScale("10%");
        commission.setCreateTime(new Date());
        commission.setId(IDUtil.getUUID());
        commission.setMarketMoney(1500000);
        commission.setMoney(1000000);
        commission.setVip(2);
        commission.setUserId(22);
        mongoTemplate.save(commission);
        System.out.println("保存成功");
    }

    @Test
    public void TestInfo(){
//        List<TUserCommission> s = mongoTemplate.find(new Query(Criteria.where("userId").is(395).and("commissionId").is("RjaV6YQ")),TUserCommission.class);
//        System.out.println(JSONArray.fromObject(s).toString());
        THelper helper = mongoTemplate.findById("Y1nnMgn",THelper.class);
        System.out.println(JSONObject.fromObject(helper).toString());
    }

    @Test
    public void sendQueue(){
//        LinkVo linkVo = new LinkVo();
//        linkVo.setName("店有客");
//        linkVo.setUrl("www.dianyk.com");
//        mqProducer.send(MQConstant.default_queue,linkVo);
    }

    @Test
    public void sendTopic(){
//        LinkVo linkVo = new LinkVo();
//        linkVo.setName("店有客");
//        linkVo.setUrl("www.dianyk.com");
//        mqProducer.send(MQConstant.default_topic,linkVo);
    }



}
