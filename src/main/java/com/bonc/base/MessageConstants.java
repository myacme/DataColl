package com.bonc.base;

/**
 * @Author : booo
 * @Date: 2020-06-28
 */
public interface MessageConstants {
    /**
     * 1. 系统初始化相关
     * 0 - 199
     */
    String MSG_0000 = "服务 {} 启动, ip: {}, port: []";
    String MSG_0001 = "读取到公私钥自维护服务启动配置，初始化应用公私钥对, 更新周期: {} ms";
    String MSG_0002 = "公私钥自维护服务：  对应用公私钥进行更新";

    String MSG_0200 = "操作成功";

    /**
     * 2. 文件操作相关
     * 201 - 399
     */
    String MSG_0201 = "读取文件： {}";

    /**
     * 3. 数据库操作相关
     * 400 - 599
     */
    String MSG_406 = "数据读取失败";
    String MSG_407 = "数据修改失败";
    String MSG_408 = "数据删除失败";
    String MSG_409 = "数据添加失败";

    String MSG_410 = "成功修改部分数据";

    /**
     * 4. 网络异常
     * 600 - 799
     */

    /**
     * Spring 框架自身异常
     * 800 - 999
     */

    /**
     * 登录异常相关
     * 1000-1099
     */
    String MSG_1000 = "ip:{} 的用户尝试使用不支持的登录方式进行登录，使用登录方式: {}";
    String MSG_1001 = "用户: {} 在 ip: {} 尝试进行登录";
    String MSG_1010 = "找不到用户 userId: [{}]";

    String MSG_1020 = "修改用户状态时找不到用户数据， userId: [{}]; changeStatus: [{}]";
    String MSG_1021 = "修改用户登录状态，是否第一次登录： {}， userId: [{}]";

    /**
     * 加密错误
     */
    String MSG_1100 = "解析到没有使用平台微服务指定加密方式进行加密的字符串";

    /**
     * 数据接口
     */
    String MSG_1200 = "该凭证已被使用";
    String MSG_1201 = "上传成功";
    String MSG_1202 = "请求时间异常,请联系管理员";
    String MSG_1203 = "凭证验证失败,请联系管理员";
    String MSG_1204 = "请选择xlsx格式文件上传";
    String MSG_1205 = "错误:未知错误,请联系管理员";
    String MSG_1206 = "错误:参数列表中含有空参";
    String MSG_1207 = "错误:系统标识符为空";
}
