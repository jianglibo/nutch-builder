/**
 * 2016 jianglibo@gmail.com
 *
 */
package com.jianglibo.nutchbuilder.domain.projection;

import org.springframework.data.rest.core.config.Projection;

import com.jianglibo.nutchbuilder.domain.Foo;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
@Projection(name = "foo-simple", types = Foo.class)
public interface FooSimple {
    String getName();
}
