package com.travelsky.im.client.internal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 聊天变更通知类型
 */
@AllArgsConstructor
public enum ChatChangeTypeEnum {
    CREATE("create", "新增"),
    UPDATE("update", "修改"),
    DELETE("delete", "删除");

    /**
     * 通知类型编码
     */
    @Getter
    @Setter
    private String typeCode;

    /**
     * 通知类型描述
     */
    @Getter
    @Setter
    private String typeDescription;

    public static ChatChangeTypeEnum getByTypeCode(String typeCode) {
        if(StringUtils.isEmpty(typeCode)){
            return null;
        }
        for (ChatChangeTypeEnum chatChangeTypeEnum : values()) {
            if (chatChangeTypeEnum.getTypeCode().equals(typeCode) ) {
                return chatChangeTypeEnum;
            }
        }
        return null;
    }
}
