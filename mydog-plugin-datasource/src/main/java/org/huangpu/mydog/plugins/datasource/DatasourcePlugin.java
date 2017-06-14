package org.huangpu.mydog.plugins.datasource;


import com.alibaba.fastjson.JSONObject;
import org.huangpu.mydog.core.*;
import org.huangpu.mydog.core.propresolver.MyDogFunction;

/**
 * Created by shenli on 2017/6/2.
 */
public class DatasourcePlugin implements MyDogPlugin {

    private JSONObject dependencyProps;
    private MetaDataPropResolver resolver = new DatasourcePropResolver();
    private static final ClassLoader classLoader = DatasourcePlugin.class.getClassLoader();

    @Override
    public void init() {
        dependencyProps = MyDogFunction.getDepFunc.apply(getMetadataType(),classLoader);
    }

    @Override
    public String getMetadataType() {
        return "datasource";
    }

    @Override
    public JSONObject getDependencyProps() {
        return dependencyProps;
    }

    @Override
    public MetaDataPropResolver getPropResolver() {
        return resolver;
    }

    @Override
    public OutputDef getOutputDef() {

        return MyDogFunction.renderOutputDef("datasource",classLoader);
    }

    @Override
    public GeneratorDecorator getGeneratorDecorator(Generator generator) {
        return new GeneratorDecorator(generator) {
            @Override
            public OutputItem generate(Metadata metaInstance, OutputItemDef outputItemDef) {
                return generator.generate(metaInstance, outputItemDef);
            }
        };
    }
    //TODO: 还要支持多种数据源的配置
}
