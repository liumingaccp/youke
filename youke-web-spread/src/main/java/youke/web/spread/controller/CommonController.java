package youke.web.spread.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mysql.jdbc.SocketMetadata;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.web.spread.bean.JsonResult;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.utils.BeanUtil;
import youke.web.spread.common.utils.DateUtil;
import youke.web.spread.entity.THelper;
import youke.web.spread.entity.THelperType;
import youke.web.spread.entity.TUserSpread;
import youke.web.spread.service.biz.UserSpreadBiz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("spread/common")
public class CommonController extends BaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserSpreadBiz userSpreadBiz;

    @RequestMapping("")
    public JsonResult index(){
        return new JsonResult();
    }

    @RequestMapping("404")
    public JsonResult error404(){
        return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"会话无效");
    }

    @RequestMapping("405")
    public JsonResult error405(){
        return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"请求无效");
    }

    @RequestMapping("checkspread")
    public String checkspread(HttpServletRequest request){
        String id = request.getParameter("id");
        return mongoTemplate.count(new Query(Criteria.where("id").is(id)), TUserSpread.class)+"";
    }

    @RequestMapping("registerspread")
    public JsonResult registerspread(HttpServletRequest request){
        String id = request.getParameter("id");
        int userid = Integer.parseInt(request.getParameter("userid"));
        String spreadId = userSpreadBiz.doInit(userid);
        TUserSpread userSpread = mongoTemplate.findById(spreadId,TUserSpread.class);
        userSpread.setParentId(id);
        userSpread.setParentFlag(mongoTemplate.findById(id,TUserSpread.class).getFlag());
        mongoTemplate.save(userSpread);
        return new JsonResult();
    }

    @RequestMapping("helperlist")
    public JsonResult helperlist(){
        List<Map> maps = new ArrayList<>();
        if(mongoTemplate.count(new Query(), THelperType.class)>0){
            Query query = new Query();
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"sort")));
            List<THelperType> types = mongoTemplate.find(query,THelperType.class);
            for (THelperType type:types){
                Map map = new HashMap();
                map.put("id",type.getId());
                map.put("title",type.getTitle());
                BasicDBObject fObject = new BasicDBObject();
                fObject.put("id",1);
                fObject.put("title",1);
                List<Map> amaps = new ArrayList<>();
                Query aQuery = new BasicQuery(new BasicDBObject("type",type.getId()),fObject);
                List<THelper> helpers = mongoTemplate.find(aQuery, THelper.class);
                if(helpers.size()>0){
                   for (THelper helper:helpers){
                       Map amap = new HashMap();
                       amap.put("id",helper.getId());
                       amap.put("title",helper.getTitle());
                       amaps.add(amap);
                   }
                }
                map.put("articles",amaps);
                maps.add(map);
            }
        }
        return new JsonResult(maps);
    }

    @RequestMapping("helperarticle")
    public JsonResult helperarticle(HttpServletRequest request){
        String id = request.getParameter("id");
        if(empty(id)){
            BasicDBObject fObject = new BasicDBObject();
            fObject.put("id",1);
            Query aQuery = new BasicQuery(new BasicDBObject(),fObject);
            aQuery.with(new Sort(Sort.Direction.ASC,"createTime"));
            List<THelper> helpers = mongoTemplate.find(aQuery, THelper.class);
            if(helpers.size()>0)
                id = helpers.get(0).getId();
            else
                id = "0";
        }
        THelper helper = mongoTemplate.findById(id,THelper.class);
        Map map = new HashMap();
        try {
            BeanUtil.bean2Map(helper,map);
            map.put("createTime",DateUtil.formatDate(helper.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(map);
    }

}
