package com.riversouther.HelloSpring.controller;

import com.google.common.base.Preconditions;
import com.riversouther.HelloSpring.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @auther chao
 * @date 16/4/24
 */
@Controller
public class WXController {
    private final Logger logger = LoggerFactory.getLogger(WXController.class);
    private final String TOKEN = "hello";

    //@RequestMapping(value = "/wx", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            logger.error("signature={} timestamp={} nonce={} echostr={}", signature, timestamp, nonce, echostr);

            Preconditions.checkArgument(signature != null && timestamp != null && nonce != null && echostr != null, "params invalid");
            String[] params = new String[]{TOKEN, timestamp, nonce};
            Arrays.sort(params);
            StringBuilder sb = new StringBuilder();
            for (String s : params) {
                sb.append(s);
            }
            MessageDigest md = MessageDigest.getInstance("sha-1");
            byte[] result = md.digest(sb.toString().getBytes());
            String signatureOut = StringUtil.byteToStr(result);

            PrintWriter out = response.getWriter();
            if (signature.equals(signatureOut)) {
                out.write(echostr);
            } else {
                logger.error("signature not matched input={} output={}", signature, signatureOut);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("WXController process error", e);
        }
    }



}
