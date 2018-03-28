package me.maxwell.generator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 代码生成配置参数Builder；
 * @author Maxwell.Lee
 * @date 2018-03-28 10:24
 * @since   1.0.0
 */
public class GeneratorConfigBuilder {

    public static List<ModuleCodeGenConfig> fromConfigFile(File file) {

        Properties  p = new Properties();
        try {
            p.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(String.format("读取Properties文件【%s】时异常失败。", file.getAbsoluteFile()), e);
        }

        String  buildDir = file.getParent();

        String jdbcDrvClass = p.getProperty("jdbc.driverClassName");
        if (jdbcDrvClass == null || jdbcDrvClass.isEmpty()) throw new RuntimeException("配置文件缺少配置项：jdbc.driverClass");

        String jdbcUrl = p.getProperty("jdbc.url");
        if (jdbcUrl == null || jdbcUrl.isEmpty()) throw new RuntimeException("配置文件缺少配置项：jdbc.url");

        String jdbcUsername = p.getProperty("jdbc.username");
        if (jdbcUsername == null || jdbcUsername.isEmpty()) throw new RuntimeException("配置文件缺少配置项：jdbc.username");

        String jdbcPassword = p.getProperty("jdbc.password");
        if (jdbcPassword == null || jdbcPassword.isEmpty()) throw new RuntimeException("配置文件缺少配置项：jdbc.password");

        String authorName = p.getProperty("author.name", "");

        String tmpDir = p.getProperty("tmp.dir");
        if (tmpDir != null) {
            tmpDir = tmpDir.replace("${build.dir}", buildDir);
        } else {
            tmpDir = new File(buildDir, "tmp").getAbsolutePath();
        }

        //发现配置文件中包含的模块名称；
        Set<String> moduleNames = new TreeSet<>();
        for (String key : p.stringPropertyNames()) {
            if (!key.startsWith("module.")) continue;

            String moduleName = parseModuleName(key);
            if (moduleName != null && moduleName.length() > 0) {
                moduleNames.add(moduleName);
            }
        }

        //发现各个模块的配置参数；
        List<ModuleCodeGenConfig> rst = new ArrayList<>();
        for (String mn : moduleNames) {
            String key;

            key = "module." + mn + ".outputDir";
            String outputDir = p.getProperty(key);
            if (outputDir == null || outputDir.isEmpty()) {
                System.out.println(String.format("配置文件缺少配置项：%s，跳过模块%s。", key, mn));
                continue;
            }

            key = "module." + mn + ".packageName";
            String pkgName = p.getProperty(key);
            if (pkgName == null || pkgName.isEmpty()) {
                System.out.println(String.format("配置文件缺少配置项：%s，跳过模块%s。", key, mn));
                continue;
            }

            key = "module." + mn + ".tableNamePrefix";
            String tblNamePrefix = p.getProperty(key);
            if (tblNamePrefix == null) {
                System.out.println(String.format("配置文件缺少配置项：%s，跳过模块%s。", key, mn));
                continue;
            }

            key = "module." + mn + ".tables";
            String tables = p.getProperty(key);
            if (tables == null) {
                System.out.println(String.format("配置文件缺少配置项：%s，跳过模块%s。", key, mn));
                continue;
            }

            ModuleCodeGenConfig conf = new ModuleCodeGenConfig();
            conf.setModuleName(mn);

            conf.setDbUrl(jdbcUrl);
            conf.setDbDriverClass(jdbcDrvClass);
            conf.setDbUserName(jdbcUsername);
            conf.setDbPassword(jdbcPassword);

            conf.setAuthorName(authorName);
            conf.setTmpDir(tmpDir);

            conf.setOutputDir(outputDir);
            conf.setTablePrefix(tblNamePrefix);
            conf.setParentPackage(pkgName);
            conf.setTableNames(parseTableNames(tables));

            rst.add(conf);
        }

        return rst;
    }

    private static String[] parseTableNames(String tblNamesStr) {
        String[]  fields = tblNamesStr.split(",");
        if (fields.length == 0) return null;

        List<String> names = new ArrayList<>();
        for (String tmp : fields) {
            if (tmp != null && tmp.length() > 0) {
                names.add(tmp);
            }
        }

        String[] rst = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            rst[i] = names.get(i);
        }

        return rst;
    }

    /**
     * 提取模块名称；
     * @param key   格式：module.XXX.*
     * @return  模块名称：XXX；
     */
    private static String parseModuleName(String key) {
        int bIdx = key.indexOf('.');
        if (bIdx < 0 || bIdx>= key.length()) return null;

        int eIdx = key.indexOf('.', bIdx + 1);
        if (eIdx < 0) return null;

        return key.substring(bIdx + 1, eIdx);
    }
}
