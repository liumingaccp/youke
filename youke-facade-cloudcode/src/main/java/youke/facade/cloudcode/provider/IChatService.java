package youke.facade.cloudcode.provider;

import youke.common.model.TChatMessage;

import java.util.List;

public interface IChatService {

    void saveChatMessage(String type,String body,String weixinId,String friendId);

    List<TChatMessage> getChatMessage(String type,int sort,int page,int limit,String weixinId,String friendId);

}
