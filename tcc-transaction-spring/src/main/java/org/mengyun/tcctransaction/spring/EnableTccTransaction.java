package org.mengyun.tcctransaction.spring;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TccTransactionAutoConfiguration.class)
public @interface EnableTccTransaction {
}
