package com.asdf.myhomeback.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class ControllerUtils {

    public static HttpHeaders createPageHeaderAttributes(Page<?> page) {
        HttpHeaders httpHeaderAttributes = new HttpHeaders();
        httpHeaderAttributes.set("total-elements", Long.toString(page.getTotalElements()));
        httpHeaderAttributes.set("total-pages", Long.toString(page.getTotalPages()));
        httpHeaderAttributes.set("current-page", Integer.toString(page.getNumber()));
        return httpHeaderAttributes;
    }

}
