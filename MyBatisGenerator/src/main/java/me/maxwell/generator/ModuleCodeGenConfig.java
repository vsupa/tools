package me.maxwell.generator;

import java.util.Arrays;

/**
 * 生成配置项；
 * @author Maxwell.Lee
 * @date 2018-01-31 16:09
 * @since   1.0.0
 */
public class ModuleCodeGenConfig {

    /**
     * 模块名称；
     */
    private String  moduleName;

    /**
     * JDBC数据库链接；
     */
    private String  dbUrl;

    /**
     * 数据库用户名；
     */
    private String  dbUserName;

    /**
     * 数据库登录密码；
     */
    private String  dbPassword;

    /**
     * 数据库JDBC驱动；
     */
    private String  dbDriverClass;

    /**
     * 临时输出目录；
     */
    private String tmpDir;

    /**
     * 输出项目目录
     */
    private String outputDir;

    /**
     * 生成人员姓名；
     */
    private String  authorName;

    /**
     * 表名前缀；
     */
    private String  tablePrefix;

    /**
     * 父级包名；
     */
    private String  parentPackage;

    /**
     * 包括的表；
     */
    private String[] tableNames;

    public ModuleCodeGenConfig() {

    }

    public ModuleCodeGenConfig(String dbUrl, String dbUserName, String dbPassword, String authorName, String tablePrefix) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.authorName = authorName;
        this.tablePrefix = tablePrefix;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(String parentPackage) {
        this.parentPackage = parentPackage;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDbDriverClass() {
        return dbDriverClass;
    }

    public void setDbDriverClass(String dbDriverClass) {
        this.dbDriverClass = dbDriverClass;
    }

    @Override
    public String toString() {
        return "ModuleCodeGenConfig{" +
                "moduleName='" + moduleName + '\'' +
                ", dbUrl='" + dbUrl + '\'' +
                ", dbUserName='" + dbUserName + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", dbDriverClass='" + dbDriverClass + '\'' +
                ", tmpDir='" + tmpDir + '\'' +
                ", outputDir='" + outputDir + '\'' +
                ", authorName='" + authorName + '\'' +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", parentPackage='" + parentPackage + '\'' +
                ", tableNames=" + Arrays.toString(tableNames) +
                '}';
    }
}
