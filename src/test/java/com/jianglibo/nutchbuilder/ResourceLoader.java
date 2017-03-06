package com.jianglibo.nutchbuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

/**
 * @author jianglibo@gmail.com
 *
 */
public class ResourceLoader {

    public static String load(String name) {
//        "cc/openscanner/tbrs/rest/loophole.json"
        String localName = name;
        try {
            return Resources.asCharSource(Resources.getResource(localName), StandardCharsets.UTF_8).read();
        } catch (IOException e) {
        }
        return null;
    }
}
