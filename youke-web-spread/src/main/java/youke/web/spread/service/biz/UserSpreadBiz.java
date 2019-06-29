package youke.web.spread.service.biz;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import youke.web.spread.bean.PageBean;
import youke.web.spread.common.base.Base;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.utils.BeanUtil;
import youke.web.spread.common.utils.DateUtil;
import youke.web.spread.common.utils.IDUtil;
import youke.web.spread.common.utils.StringUtil;
import youke.web.spread.entity.*;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserSpreadBiz extends Base{

     @Autowired
     private JdbcTemplate jdbcTemplate;
     @Autowired
     private MongoTemplate mongoTemplate;

     public TUserSpread getUserSpread(String id){
         return mongoTemplate.findById(id,TUserSpread.class);
     }

    /**
     * 初始化推广奖励信息
     * @param userId
     */
     public String doInit(int userId){
          String id = "";
          List<TUserSpread> spreads = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)),TUserSpread.class);
          if(spreads==null||spreads.size()==0){
              TUserSpread spread = new TUserSpread();
              spread.setId(IDUtil.getRandomId());
              spread.setAlipayAccount("");
              spread.setAlipayName("");
              spread.setBalance(0);
              spread.setFlag(ApiCodeConstant.USER_FLAG_C);
              Map map = jdbcTemplate.queryForMap("select mobile,createTime,role from t_user where id="+userId);
              spread.setRole((Integer) map.get("role"));
              spread.setMobile((String) map.get("mobile"));
              spread.setRegisterTime((Date)map.get("createTime"));
              spread.setUserId(userId);
              mongoTemplate.save(spread);
              id = spread.getId();
          }else{
              id = spreads.get(0).getId();
          }
          return id;
     }

    public Map getInfo(String id) {
         TUserSpread userSpread = mongoTemplate.findById(id,TUserSpread.class);
         Map map = new HashMap();
         try {
            BeanUtil.bean2Map(userSpread,map);
            map.put("registerTime", DateUtil.formatDate(userSpread.getRegisterTime(),"yyyy-MM-dd HH:mm:dd"));
            String[] uids = {id+"_YH",id+"_Br_C",id+"_Br_Bb_C",id+"_Br_Bb_T_C",id+"_Br_T_C",id+"_Bb_C",id+"_Bb_T_C",id+"_T_C",id+"_C_C"};
            List<TConfig> configs = mongoTemplate.find(new Query(Criteria.where("id").in(uids)),TConfig.class);
            if(configs.size()==0)
               configs = mongoTemplate.find(new Query(Criteria.where("type").is("user_flag")),TConfig.class);
            else{
                for (TConfig config:configs) {
                    config.setId(config.getId().replace(id+"_",""));
                }
            }
            map.put("flagItems",configs);
         } catch (Exception e) {
            e.printStackTrace();
         }finally {
             return map;
         }
    }

    public PageBean<Map> getSubData(int page, int limit, String id, String flag, String timeBeg, String timeEnd) {
        Query query = new Query(Criteria.where("parentId").is(id).and("flag").is(flag));
        if(notEmpty(timeBeg))
           query.addCriteria(Criteria.where("registerTime").gte(DateUtil.parseDate(timeBeg)).lte(DateUtil.parseDate(timeEnd)));
        int totalCount = (int)mongoTemplate.count(query,TUserSpread.class);
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"userId")));
        query.skip((page-1)*limit);
        query.limit(limit);
        List<TUserSpread> userSpreads = mongoTemplate.find(query,TUserSpread.class);
        PageBean<Map> pageBean = new PageBean<>();
        if(userSpreads!=null&&userSpreads.size()>0) {
            List<Map> maps = new ArrayList<>();
            for (TUserSpread uSpread:userSpreads) {
                int totalC = (int)mongoTemplate.count(new Query(Criteria.where("parentId").is(uSpread.getId())), TUserSpread.class);
                int totalNum = (int)mongoTemplate.count(new Query(Criteria.where("parentId").is(uSpread.getId())), TUserOrder.class);
                int totalMoney = 0;
                List<TUserOrder> moneylist = mongoTemplate.find(new BasicQuery(new BasicDBObject("parentId",uSpread.getId()),new BasicDBObject("money",1)),TUserOrder.class);
                if(moneylist!=null&&moneylist.size()>0){
                    totalMoney = moneylist.stream().mapToInt(TUserOrder::getMoney).sum();
                }
                Map map = new HashMap();
                map.put("id",uSpread.getId());
                map.put("flag",uSpread.getFlag());
                map.put("doTime",DateUtil.formatDate(uSpread.getRegisterTime(),"yyyy-MM-dd HH:mm:ss"));
                map.put("mobile",uSpread.getFlag().equals("C")?StringUtil.hideMobile(uSpread.getMobile()):uSpread.getMobile());
                map.put("totalC",totalC);
                map.put("totalNum",totalNum);
                map.put("totalMoney",totalMoney);
                maps.add(map);
            }
            pageBean.setList(maps);
            pageBean.setPageNum(page);
            pageBean.setPageSize(limit);
            pageBean.setTotal(totalCount);
        }
        return pageBean;
    }

    public PageBean<Map> getSubList(int page, int limit, String id,String timeBeg, String timeEnd) {
        PageBean<Map> pageBean = new PageBean<>();
        TUserSpread userSpread = mongoTemplate.findById(id,TUserSpread.class);
        Query query = new Query();
        if(userSpread.getFlag().equals("Br")||userSpread.getFlag().equals("Bb")){
            List<TUserSpread> subUserSpreads = mongoTemplate.find(new Query(Criteria.where("parentId").is(id).and("flag").in("Br","Bb","T")),TUserSpread.class);
            List<TUserSpread> subCUserSpreads = mongoTemplate.find(new Query(Criteria.where("parentId").is(id).and("flag").in("C")),TUserSpread.class);
            List<String> subUserIds = subUserSpreads.stream().map(TUserSpread::getId).collect(Collectors.toList());
            List<Integer> subCUserIds = subCUserSpreads.stream().map(TUserSpread::getUserId).collect(Collectors.toList());
            Criteria criteria1 = Criteria.where("commissionId").in(subUserIds);
            Criteria criteria2 = Criteria.where("userId").in(subCUserIds).and("commissionId").is(id);
            Criteria cr = new Criteria();
            query.addCriteria(cr.orOperator(criteria1,criteria2));
        }else{
            query.addCriteria(Criteria.where("commissionId").is(id));
        }
        if(notEmpty(timeBeg))
            query.addCriteria(Criteria.where("createTime").gte(DateUtil.parseDate(timeBeg)).lte(DateUtil.parseDate(timeEnd)));
        int totalCount = (int) mongoTemplate.count(query, TUserCommission.class);
        if(totalCount>0){
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
            query.skip((page - 1) * limit).limit(limit);
            List<TUserCommission> userComms = mongoTemplate.find(query, TUserCommission.class);
            if (userComms != null && userComms.size() > 0) {
                List<Map> maps = new ArrayList<>();
                for (TUserCommission userComm : userComms) {
                    Map map = new HashMap();
                    map.put("id",userComm.getId());
                    map.put("doTime",DateUtil.formatDate(userComm.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                    if(userSpread.getFlag().equals("Br")||userSpread.getFlag().equals("Bb")) {
                        if(userComm.getCommissionId().equals(id)) {
                            map.put("flag", "C");
                            map.put("mobile", StringUtil.hideMobile(userComm.getMobile()));
                            map.put("scale", userComm.getCommissionScale());
                            map.put("commission", userComm.getCommission());
                        }else{
                            map.put("flag", userComm.getCommissionFlag());
                            map.put("mobile", userComm.getCommissionMobile());
                            List<TUserCommission> pComms = mongoTemplate.find(new Query(Criteria.where("userId").is(userComm.getUserId()).and("commissionId").is(id)), TUserCommission.class);
                            if(pComms.size()>0) {
                                map.put("scale", pComms.get(0).getCommissionScale());
                                map.put("commission", pComms.get(0).getCommission());
                            }else{
                                map.put("scale", "");
                                map.put("commission", 0);
                            }
                        }
                    }else{
                        map.put("flag","C");
                        map.put("mobile", StringUtil.hideMobile(userComm.getMobile()));
                        map.put("scale", userComm.getCommissionScale());
                        map.put("commission", userComm.getCommission());
                    }
                    map.put("vip",userComm.getVip());
                    map.put("marketMoney",userComm.getMarketMoney());
                    map.put("money",userComm.getMoney());
                    map.put("cmobile", StringUtil.hideMobile(userComm.getMobile()));
                    map.put("cflag","C");
                    maps.add(map);
                }
                pageBean.setList(maps);
                pageBean.setPageNum(page);
                pageBean.setPageSize(limit);
                pageBean.setTotal(totalCount);
            }
        }
        return pageBean;
    }

    public void doBindAlipay(String id, String alipayAccount, String alipayName) {
        TUserSpread userSpread = mongoTemplate.findById(id,TUserSpread.class);
        userSpread.setAlipayAccount(alipayAccount);
        userSpread.setAlipayName(alipayName);
        mongoTemplate.save(userSpread);
    }

    public void doTakeMoney(String id, int money) {
        TUserSpread userSpread = mongoTemplate.findById(id,TUserSpread.class);
        userSpread.setBalance(userSpread.getBalance()-money);
        mongoTemplate.save(userSpread);
        TTakeMoney takeMoney = new TTakeMoney();
        takeMoney.setBalance(userSpread.getBalance());
        takeMoney.setCreateTime(new Date());
        takeMoney.setId(IDUtil.getUUID());
        takeMoney.setRegisterTime(userSpread.getRegisterTime());
        takeMoney.setState(0);
        takeMoney.setTakeMoney(money);
        takeMoney.setType(0);
        takeMoney.setUserId(userSpread.getUserId());
        takeMoney.setAlipayAccount(userSpread.getAlipayAccount());
        takeMoney.setAlipayName(userSpread.getAlipayName());
        takeMoney.setFlag(userSpread.getFlag());
        takeMoney.setMobile(userSpread.getMobile());
        takeMoney.setSpreadId(userSpread.getId());
        mongoTemplate.save(takeMoney);
    }

    public PageBean<Map> getTakeMoneyRecord(int page, int limit, int userId, int state, String timeBeg, String timeEnd) {
        Query query = new Query(Criteria.where("userId").is(userId));
        if(state>-1)
            query.addCriteria(Criteria.where("state").is(state));
        if(notEmpty(timeBeg))
            query.addCriteria(Criteria.where("createTime").gte(DateUtil.parseDate(timeBeg)).lte(DateUtil.parseDate(timeEnd)));
        int totalCount = (int)mongoTemplate.count(query,TTakeMoney.class);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        query.skip((page - 1) * limit).limit(limit);
        List<TTakeMoney> takeMonies = mongoTemplate.find(query,TTakeMoney.class);
        PageBean<Map> pageBean = new PageBean<>();
        try {
            List<Map> maps = new ArrayList<>();
            for (TTakeMoney takeMoney:takeMonies)
            {
                Map map = new HashMap();
                BeanUtil.bean2Map(takeMoney,map);
                map.put("createTime",DateUtil.formatDate(takeMoney.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                map.remove("doTime");
                maps.add(map);
            }
            pageBean.setTotal(totalCount);
            pageBean.setPageNum(page);
            pageBean.setPageSize(limit);
            pageBean.setList(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return pageBean;
        }
    }

    private String getScaleStr(String flagS,String id,boolean yh) {
        double scale = getComminssionScale(flagS, id);
        if (scale > 0) {
            if (yh) {
                scale = scale * getComminssionScale("YH", id) / 100;
            }
        }
        DecimalFormat dFormat=new DecimalFormat("#.00");
        String scaleStr=dFormat.format(scale);
        scaleStr = scaleStr.replace(".00","");
        return scaleStr+"%";
    }

    private double getComminssionScale(String flagS,String id){
        double scale = 0;
        try {
            TConfig config;
            if(mongoTemplate.count(new Query(Criteria.where("id").is(id+"_"+flagS)),TConfig.class)>0)
                config = mongoTemplate.findById(id+"_"+flagS, TConfig.class);
            else
                config = mongoTemplate.findById(flagS, TConfig.class);
            scale = Double.parseDouble(config.getVal().replace("%", ""));
        }catch (Exception e){

        }finally {
            return scale;
        }
    }

}
