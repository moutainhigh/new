package com.huayu.thirdparty.web.controller;


import com.huayu.a.service.SecurityAuthenticationProvider;
import com.huayu.a.service.SsoUserMap;
import com.huayu.user.domain.SysUserMapping;
import com.huayu.user.domain.User;
import com.huayu.user.service.SysService;
import com.huayu.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * 对接Oa系统
 * Created by DengPeng on 2017/3/29.
 */
@Controller
@RequestMapping("/seeyon")
@SessionAttributes("SPRING_SECURITY_LAST_EXCEPTION")
public class SeeyonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysService userSysService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityAuthenticationProvider securityAuthenticationProvider;

    @Autowired
    private SsoUserMap ssoUserMap;


    @Value("#{configProperties['seeyonSsoLoginUrl']}")
    private String seeyonSsoLoginUrl;

    @Value("#{configProperties['ssoLoginSeeyonUrl']}")
    private String ssoLoginSeeyonUrl;

    @Value("#{configProperties['ssoLogoutSeeyonUrl']}")
    private String ssoLogoutSeeyonUrl;

    @Value("#{configProperties['ssoSeeyonPublicExponent']}")
    private String ssoSeeyonPublicExponent;

    @Value("#{configProperties['ssoSeeyonModulus']}")
    private String ssoSeeyonModulus;

    @Value("#{configProperties['ssoSeeyonRedirect']}")
    private String ssoSeeyonRedirect;

    private String modulus="ohuJnpLkPcs1ESAIL/PoH68YA0+MS3JXZluQjfzFDfS5opfB7rGmLz5dGwN89O+iAbs2j+kLF1i08+SLsrqt2IEEBX24aFNBXPC0+RtDseDxci6epg/eyPsDdxQ+d0wLg9//jcWwl3o+3/di92m0sgsg/65ECh9ewx6NO6oWNmM=";
    private String privateExponent="XerYW1+9DjP5xbcqJyYHOLwHjHt8y4UuL6Yi5LAqNkCvgAyuFa0km8Bkzq7BqWJgGOKu5MdeXqNNCvRBH0ZaSuWdTRAxyxuj1fh2UXeeGPo+rswW9H01C2eNW2v8M6CJUgOnU37F3fiKziyBs4XSHkcUaIcf3E+KWH4qfQAJqYk=";




    @RequestMapping(value = "/loginManual",method = RequestMethod.GET)
    @Deprecated
    public String getResource(String username, String password, Model model,HttpServletRequest request){
        try {
            if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)){
                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = context.getAuthentication();
                if (null==authentication || (authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal().toString()))){
                    Authentication token = new UsernamePasswordAuthenticationToken( username, password);
                    authentication = authenticationManager.authenticate(token);
                }
                if (authentication.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
                    return ".admin.index.default";
                }
            }
            return "/admin/index/login";
        } catch (AuthenticationException e) {
            logger.error("远程登陆认证失败", e);
            model.addAttribute("SPRING_SECURITY_LAST_EXCEPTION",e);
            return "/admin/index/login";
        }
    }

    private String toPage(String pageUrl){
        if (null!=pageUrl){
            return  "redirect:"+pageUrl;
        }
        return  "redirect:/admin/index/default";
    }


    @RequestMapping(value = "/ssoLogin")
    public String ssoLogin(String ticket, String pageUrl, HttpServletRequest request) throws IOException {
            if (StringUtils.isNotBlank(ticket)){
                HttpSession session = request.getSession();
                Object attribute = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                if (null!=attribute){
                    SecurityContext oldContext = (SecurityContext) attribute;
                    Authentication authentication = oldContext.getAuthentication();
                    if (authentication!=null && authentication.isAuthenticated()){
                        return  toPage(pageUrl);
                    }
                }
                BASE64Decoder decoder = new BASE64Decoder();
                String seeyonUsername= new String(decoder.decodeBuffer(ticket));
                if (!StringUtils.isBlank(seeyonUsername)){
                    SysUserMapping userMap = userSysService.findUserMap(null, seeyonUsername, null);
                    if (null!=userMap&&StringUtils.isNotBlank(userMap.getUserName())){
                        try {
                            User user = userService.getOneForLogin(userMap.getUserName());
                            if (user == null){
                                return  "redirect:/admin/login/input";
                            }
                            securityAuthenticationProvider.loadUserAuthorities(user);
                            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( user, null, user.getAuthorities());
                            token.setDetails(new WebAuthenticationDetails(request));
                            SecurityContext context = SecurityContextHolder.createEmptyContext();
                            context.setAuthentication(token);
                            SecurityContextHolder.setContext(context);
                            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
                            return  toPage(pageUrl);
                        } catch (Exception e) {
                            logger.info("单点登陆ERP系统失败：",e);
                            return ".admin.index.404";
                        }
                    }else{
                        return ".admin.index.404";
                    }
                }else{
                    return ".admin.index.404";
                }
            }else{
                return ".admin.index.404";
            }
    }


    @RequestMapping(value = "/ssoLoginSeeyon",method = RequestMethod.GET)
    public ModelAndView ssoLoginSeeyon(Model model,HttpSession session,  HttpServletRequest request){
        String url=null;
        String username = "dengpeng";
        try {
            //创建私钥
            byte[] ary_exponent=(new BASE64Decoder()).decodeBuffer(ssoSeeyonPublicExponent);
            byte[] ary_modulus=(new BASE64Decoder()).decodeBuffer(ssoSeeyonModulus);
            //注意构造函数，调用时指明正负值，1代表正值，否则报错
            BigInteger big_exponent = new BigInteger(1,ary_exponent);
            BigInteger big_modulus = new BigInteger(1,ary_modulus);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(big_modulus, big_exponent);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE,  keyFactory.generatePublic(keySpec));
            byte[] enBytes = cipher.doFinal(username.getBytes());
            String encode = (new BASE64Encoder()).encodeBuffer(enBytes);
            String ticket = URLEncoder.encode(encode, "UTF-8");
            System.out.println(ticket);
            ticket = URLEncoder.encode(ticket, "UTF-8");
            System.out.println(ticket);
            String page = URLEncoder.encode("collaboration/collaboration.do?method=summary&openFrom=listPending&affairId=799968783398289741","UTF-8");
            url = "http://oa.cqhyrc.com.cn/seeyon/thirdparty/ssoSeeyon.do?method=login&ticket="+ticket+"&sessionId="+session.getId()+"&page="+page;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:"+url);
    }


    @RequestMapping(value = "/ssoLogoutSeeyon",method = RequestMethod.GET)
    public ModelAndView ssoLogoutSeeyon(Model model,HttpSession session) throws Exception{
        String url;
        String username = "admin";
        byte[] ary_exponent=(new BASE64Decoder()).decodeBuffer(ssoSeeyonPublicExponent);
        byte[] ary_modulus=(new BASE64Decoder()).decodeBuffer(ssoSeeyonModulus);
        BigInteger big_exponent = new BigInteger(1,ary_exponent);
        BigInteger big_modulus = new BigInteger(1,ary_modulus);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(big_modulus, big_exponent);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE,  keyFactory.generatePublic(keySpec));
        byte[] enBytes = cipher.doFinal(username.getBytes());
        String encode = (new BASE64Encoder()).encodeBuffer(enBytes);
        String ticket = URLEncoder.encode(encode, "UTF-8");
        ticket = URLEncoder.encode(ticket, "UTF-8");
        url = "http://61.128.134.135:9100/seeyon/thirdparty/ssoSeeyon.do?method=logout&ticket="+ticket+"&sessionId="+session.getId();
        return new ModelAndView("redirect:"+url);
    }

    /**
     * 解密 信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getKey",method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public String ssoLogout(HttpServletRequest request) throws Exception {
        String ticket = request.getParameter("ticket");
        System.out.println(ticket);
        String decode = URLDecoder.decode(ticket, "UTF-8");
        byte[] ary_exponent=(new BASE64Decoder()).decodeBuffer(privateExponent);
        byte[] ary_modulus=(new BASE64Decoder()).decodeBuffer(modulus);
        BigInteger big_exponent = new BigInteger(1,ary_exponent);
        BigInteger big_modulus = new BigInteger(1,ary_modulus);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(big_modulus, big_exponent);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] ary_en=(new BASE64Decoder()).decodeBuffer(decode);
        byte[] deBytes = cipher.doFinal(ary_en);
        return new String(deBytes);
    }

}
