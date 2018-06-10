package com.yhxx.wxapp.web.api;

import com.yhxx.common.bean.CommonLogger;
import com.yhxx.common.utils.LoggerUtils;
import com.yhxx.wxapp.domain.TextTypeMsg;
import com.yhxx.wxapp.service.api.AutomaticResponseMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author: Wanglf
 * @Date: Created in 17:30 2018/5/1
 * @modified By:
 */
@Controller
@RequestMapping(value = "/programIndex")
public class ProgramIndexController {

    @Autowired
    private AutomaticResponseMsgService automaticResponseMsgService;

    @GetMapping("/home1")
    public void checkToken(HttpServletRequest req, HttpServletResponse resp) {

        //接收微信发送过来的消息，进行验证
        String result = "";
        PrintWriter out = null;

        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.containsKey("echostr")) {
            LoggerUtils.info(CommonLogger.BIZ, "请求参数。用于验证token,请求方式" + req.getMethod() + "请求内容" + parameterMap.toString());
            String[] echostrs = parameterMap.get("echostr");
            String echostr = echostrs[0];
            if (checkToken(parameterMap)) {
                result = echostr;
            }
        }

        try {
            out = resp.getWriter();
            out.write(result);
        } catch (IOException e) {

            LoggerUtils.info(CommonLogger.BIZ, "校验token异常信息：" + result);
        } finally {
            out.close();
        }

    }

    @PostMapping("/home1")
    public void paramIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String result = "";
        PrintWriter out = null;
        resp.setCharacterEncoding("utf-8");
        if (checkToken(parameterMap)) {
            result = automaticResponseMsgService.receiveAllInfo(req.getInputStream());
        }

        try {
            out = resp.getWriter();
            out.write(result);
        } catch (IOException e) {
            LoggerUtils.info(CommonLogger.BIZ, "回复消息信息异常：" + result);
        } finally {
            out.close();
        }

    }

    private boolean checkToken(Map<String, String[]> parameterMap) {

        String signature = "";
        String timestamp = "";
        String nonce = "";
        if (parameterMap.containsKey("signature")) {
            signature = parameterMap.get("signature")[0];
        }
        if (parameterMap.containsKey("timestamp")) {
            timestamp = parameterMap.get("timestamp")[0];
        }
        if (parameterMap.containsKey("nonce")) {
            nonce = parameterMap.get("nonce")[0];
        }

        String token = "myWeXin";
        LoggerUtils.info(CommonLogger.BIZ, "微信参数>>" + "signature:" + signature + ";timestamp:" + timestamp + ";nonce:" + nonce);

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            return false;
        }
        //按照字典顺序进行排序
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);

        String str = array[0].concat(array[1]).concat(array[2]);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.error(CommonLogger.ERROR, "验证权限失败" + e);
        }
        byte[] digest = md.digest(str.getBytes());

        if (signature.equals(byte2str(digest))) {
            return true;
        }

        return false;
    }

    private String byte2str(byte[] array) {
        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < array.length; i++) {
            shaHex = Integer.toHexString(array[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }
}
