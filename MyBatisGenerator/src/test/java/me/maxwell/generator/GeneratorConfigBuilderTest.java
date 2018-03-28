package me.maxwell.generator;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.File;
import java.util.List;

/**
 * @author Maxwell.Lee
 * @date 2018-03-28 13:41
 * @since   1.0.0
 */
public class GeneratorConfigBuilderTest extends TestCase {

    public void testFromConfigFile() {
        List<ModuleCodeGenConfig>   rst;

        rst = GeneratorConfigBuilder.fromConfigFile(new File("D:\\PlatformProjects\\tools\\MyBatisGenerator\\src\\main\\resources\\code-gen-default.properties"));

        System.out.println(rst);
        Assert.assertNotNull(rst);
        Assert.assertEquals(2, rst.size());
    }
}