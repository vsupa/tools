package me.maxwell;

/**
 * @author Maxwell.Lee
 * @date 2018-01-31 16:09
 * @since   1.0.0
 */
public class GeneratorConfig {

    private String  outputDir;

    private String  projDir;

    private String  authorName;

    private String  dbUserName;

    private String  dbPassword;

    private String  dbUrl;

    private String  tablePrefix;

    private String  parentPackage;

    private String[]  tableNames;

    public GeneratorConfig() {

    }

    public GeneratorConfig(String dbUrl, String dbUserName, String dbPassword, String authorName, String tablePrefix) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.authorName = authorName;
        this.tablePrefix = tablePrefix;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getProjDir() {
        return projDir;
    }

    public void setProjDir(String projDir) {
        this.projDir = projDir;
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
}
