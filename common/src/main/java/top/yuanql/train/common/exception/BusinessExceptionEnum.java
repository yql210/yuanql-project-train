package top.yuanql.train.common.exception;

public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机已注册");


    private String desc;

    BusinessExceptionEnum() {
    }

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
