package com.piu.thymeleafDialect;

import com.piu.sys.utils.DictUtils;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

public class DictDialect extends AbstractDialect implements IExpressionObjectDialect {


    public DictDialect() {
        super("DictUtils");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("DictUtils");
            }

            @Override
            public Object buildObject(IExpressionContext context,
                                      String expressionObjectName) {
                return new DictUtils();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}
