package com.reddit.clone.Application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * This class is used to call the HTML template with the give message dynamically using template object and context
 * class of thym leaf
 */
@Service
@AllArgsConstructor
public class MailContentBuilder {


    private final TemplateEngine templateEngine;
    String build(String message){
        Context context = new Context();
        context.setVariable("message",message);
        return templateEngine.process("mailTemplate",context);
    }
}
