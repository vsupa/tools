package me.maxwell.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maxwell.Lee
 * @date 2018-03-28 14:10
 * @since   1.0.0
 */
public class ModuleCodeGenCmd {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("====按照配置文件生成全部模块的通用代码文件：ModuleCodeGenCmd 配置文件");
            System.out.println("====按照配置文件生成指定模块的通用代码文件：ModuleCodeGenCmd 配置文件 模块名1 模块名2 ... 模块名N");
            return;
        }

        Set<String>     targetModuleNames = null;
        if (args.length >= 2) {
            targetModuleNames = new HashSet<>();

            for(int i=1; i<args.length; i++) {
                String tmp = args[i];
                if (tmp == null || tmp.isEmpty()) continue;

                targetModuleNames.add(tmp.trim());
            }
        }

        try {
            File confFile = new File(args[0]).getCanonicalFile();
            List<ModuleCodeGenConfig> configs = GeneratorConfigBuilder.fromConfigFile(confFile);

            for (ModuleCodeGenConfig mcgConfig : configs) {
                if (targetModuleNames == null || targetModuleNames.contains(mcgConfig.getModuleName())) {
                    System.out.println(String.format("\n============模块[%s]：正在生成代码文件============", mcgConfig.getModuleName()));
                    ModuleCodeGenerator.generate(mcgConfig);
                    System.out.println(String.format("\n============模块[%s]：生成代码文件成功============", mcgConfig.getModuleName()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
