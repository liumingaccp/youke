package youke.service.fans.vo;

import youke.common.utils.excel.ExcelCellType;
import youke.common.utils.excel.ExcelField;
import java.io.Serializable;
import java.util.Date;

public class ShopFansExcelVo implements Serializable {
    @ExcelField("平台买家昵称")
    private String nickName;
    @ExcelField("买家手机号")
    private String phone;
    @ExcelField("省份名称")
    private String province;
    @ExcelField("城市名称")
    private String city;
    @ExcelField("区/县")
    private String street;
    @ExcelField("详细地址")
    private String address;
    @ExcelField("买家姓名")
    private String name;
    @ExcelField("买家Email（选填）")
    private String email;
    @ExcelField(value = "爱好（选填）")
    private String hobby;
    @ExcelField(value = "性别（1男，2女，选填）")
    private String sex;
    @ExcelField(value = "年龄（选填）")
    private String age;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
