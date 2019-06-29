package youke.facade.wx.vo.menu;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuVo implements Serializable {

    private ButtonVo button;

    private List<ButtonVo> subButtons = new ArrayList<ButtonVo>();

    public ButtonVo getButton() {
        return button;
    }

    public void setButton(ButtonVo button) {
        this.button = button;
    }

    public List<ButtonVo> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<ButtonVo> subButtons) {
        this.subButtons = subButtons;
    }
}
