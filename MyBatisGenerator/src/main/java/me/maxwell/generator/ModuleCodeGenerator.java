package me.maxwell.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 定制化的Model层代码生成器
 * @author Maxwell.Lee
 * @date 2018-01-09 17:19
 * @since   1.0.0
 */
public class ModuleCodeGenerator {

    public static void      generate(ModuleCodeGenConfig config) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(config.getTmpDir());
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columnList
        gc.setAuthor(config.getAuthorName());
        gc.setOpen(false);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                if(fieldType.equals("char(1)")) {
                    return DbColumnType.CHARACTER;
                }

                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName(config.getDbDriverClass());
        dsc.setUsername(config.getDbUserName());
        dsc.setPassword(config.getDbPassword());
        dsc.setUrl(config.getDbUrl());
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(config.getTablePrefix());// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(config.getTableNames()); // 需要生成的表

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(config.getParentPackage());
        pc.setMapper("dao");
        pc.setXml("xml");

        mpg.setPackageInfo(pc);

        // 关闭默认XML生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        copyFilesInTmpDirToOutputDir(config.getTmpDir(), config.getOutputDir(), config.getParentPackage());
    }

    private static void copyFilesInTmpDirToOutputDir(String tmpDir, String outputDir, String packageName) {
        String packageDir = packageName.replace('.', '\\');

        File entitySrcDir = new File(tmpDir, packageDir + "\\entity");
        File entityDstDir = new File(outputDir, "src\\main\\java\\" + packageDir + "\\entity");

        File daoSrcDir = new File(tmpDir, packageDir + "\\dao");
        File daoDstDir = new File(outputDir, "src\\main\\java\\" + packageDir + "\\dao");

        File xmlSrcDir = new File(tmpDir, packageDir + "\\xml");
        File xmlDstDir = new File(outputDir, "\\src\\main\\resources\\mybatis\\xml");

        try {
            System.out.println(String.format("-->>复制Entity文件：[%s] -> [%s]", entitySrcDir.getCanonicalFile(), entityDstDir.getCanonicalFile()));
            FileUtils.copyDirectory(entitySrcDir, entityDstDir);

            System.out.println(String.format("-->>复制DAO文件：[%s] -> [%s]", daoSrcDir.getCanonicalFile(), daoDstDir.getCanonicalFile()));
            FileUtils.copyDirectory(daoSrcDir, daoDstDir);

            System.out.println(String.format("-->>复制MapperXML文件：[%s] -> [%s]", xmlSrcDir.getCanonicalFile(), xmlDstDir.getCanonicalFile()));
            FileUtils.copyDirectory(xmlSrcDir, xmlDstDir);
        } catch (IOException e) {
            throw new RuntimeException(String.format("从临时目录[%s]复制生成结果文件到输出目录[%s]时异常失败", tmpDir, outputDir), e);
        }

    }
}
