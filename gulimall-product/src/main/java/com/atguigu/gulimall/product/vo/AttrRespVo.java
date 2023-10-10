package com.atguigu.gulimall.product.vo;

public class AttrRespVo extends AttrVo {

    /**
     * 所属分类
     */
    private String catelogName;

    /**
     * 所属分组
     */
    private String groupName;

    private Long[] catelogPath;

    public String getCatelogName() {
        return catelogName;
    }

    public void setCatelogName(String catelogName) {
        this.catelogName = catelogName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long[] getCatelogPath() {
        return catelogPath;
    }

    public void setCatelogPath(Long[] catelogPath) {
        this.catelogPath = catelogPath;
    }
}
