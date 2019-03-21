package com.moerlong.youfen.constant;

public enum ValidationEnum {

    NAME_IDCARD("姓名身份证二要素验证","1"),
    NAME_CELLPHONE("姓名手机号二要素验证","2"),
    NAME_BANKCARD("姓名银行卡二要素验证","3"),
    CELLPHONE_BANKCARD("手机号银行卡二要素验证","4"),
    NAME_IDCARD_BANKCARD("姓名身份证银行卡三要素验证","5"),
    NAME_IDCARD_BANKCARD_CELLPHONE("姓名身份证银行卡手机号四要素验证","6"),
    IDCARD_BANKCARD("身份证银行卡二要素验证","7"),
    NAME_IDCARD_CELLPHONE("姓名身份证手机号三要素验证","8"),
    BANKCARD("银行卡有效性验证","9");

    private String name;
    private String num;

    private ValidationEnum(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    // 普通方法
    public static final String getTypeNum(String name) {
        return fromValue(name).num;
    }

    public static final String getTypeName(String num) {
        return fromValue(num).name;
    }

    public static final ValidationEnum fromValue(String inputStr) {
        for (ValidationEnum c : ValidationEnum.values()) {
            if (c.name.equals(inputStr) || c.num.equals(inputStr)) {
                return c;
            }
        }
        throw new RuntimeException("ValidationEnum，输入参数：" + inputStr);
    }

}
