package com.cjk.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 生成验证码的servlet:
 * 1.引入kaptcha依赖
 * 2.编写servlet，继承KaptchaServlet
 */
@WebServlet(urlPatterns = "/code.jpg",initParams = {

        @WebInitParam(name="kaptcha.image.width",value = "120"),
        @WebInitParam(name="kaptcha.image.height",value = "32"),
        @WebInitParam(name="kaptcha.textproducer.char.length",value = "4"),
        @WebInitParam(name = "kaptcha.textproducer.font.color",value = "black"),
        @WebInitParam(name = "kaptcha.background.clear.from",value = "yellow"),
        @WebInitParam(name = "kaptcha.background.clear.to",value = "green"),
        @WebInitParam(name = "kaptcha.noise.color",value = "white"),
        @WebInitParam(name = "kaptcha.session.key",value = "vcode"),
        @WebInitParam(name = "kaptcha.textproducer.font.size",value = "28"),
        @WebInitParam(name = "kaptcha.textproducer.char.string",value = "1234567890"),
        @WebInitParam(name = "kaptcha.textproducer.font.names",value = "Monospaced"),
        @WebInitParam(name = "kaptcha.obscurificator.impl",value = "com.google.code.kaptcha.impl.ShadowGimpy")
})
public class CodeServlet extends KaptchaServlet {

    public CodeServlet() {
        System.out.println("init....");
    }
}
