package youke.service.cloudcode.provider;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.ICloudCodeDao;
import youke.common.model.TChatMessage;
import youke.common.utils.DateUtil;
import youke.facade.cloudcode.provider.IChatService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatService extends Base implements IChatService {

    @Resource
    private ICloudCodeDao cloudCodeDao;


    @Override
    public void saveChatMessage(String type, String body, String weixinId, String friendId) {
         cloudCodeDao.insertChatMessage(type,body,weixinId,friendId,DateUtil.getDateTime());
    }

    @Override
    public List<TChatMessage> getChatMessage(String type, int sort, int page, int limit, String weixinId, String friendId) {
         PageHelper.startPage(page,limit,sort==0?"id desc":"id");
         if("".equals(type))
             type=null;
         List<TChatMessage> messages = cloudCodeDao.selectChatMessage(type,sort,weixinId,friendId);
         return messages;
    }

}
