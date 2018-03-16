package me.maxwell.ecsp;

import me.maxwell.GeneratorConfig;
import me.maxwell.MyBatisGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maxwell.Lee
 * @date 2018-01-31 16:13
 * @since   1.0.0
 */
public class EcspProjectGenerator {

    private static final String DB_URL = "jdbc:mysql://192.168.1.191:3306/ecsp2?" +
                                        "useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
    private static final String DB_USERNAME = "ecsp2_dev";
    private static final String DB_PASSWORD = "ESCP_dev-2018";
    private static final String AUTHOR = "Maxwell.Lee";
    /**
     * 生成文件的输出目录；
     */
    private static final String OUTPUT_DIR_TPL = "D:\\var\\mpg_ecsp\\%s";
    /**
     * 各个模块的工程根目录；
     */
    private static final String PROJ_DIR_TPL = "D:\\VSUPA\\code\\ecsp2\\backend\\%s\\service\\";
    /**
     * 包名模板；
     */
    private static final String PARENT_PACKAGE_TPL = "com.vsupa.ecsp.%s";

    public static String getModelOutputDir(String model) {
        return String.format(OUTPUT_DIR_TPL, model);
    }

    public static String getModelParentPackage(String model) {
        return String.format(PARENT_PACKAGE_TPL, model);
    }

    public static String getProjDir(String model) {
        return String.format(PROJ_DIR_TPL, model);
    }

    private static List<GeneratorConfig> initModuleConfigs() {
        List<GeneratorConfig> modelGenConfigs = new ArrayList<>();

        GeneratorConfig umCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "um_");
        umCfg.setOutputDir(getModelOutputDir("user"));
        umCfg.setProjDir(getProjDir("user"));
        umCfg.setParentPackage(getModelParentPackage("user"));
        modelGenConfigs.add(umCfg);

        GeneratorConfig pvCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "pv_");
        pvCfg.setOutputDir(getModelOutputDir("provider"));
        pvCfg.setProjDir(getProjDir("provider"));
        pvCfg.setParentPackage(getModelParentPackage("provider"));
        modelGenConfigs.add(pvCfg);

        GeneratorConfig psCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "ps_");
        psCfg.setOutputDir(getModelOutputDir("product"));
        psCfg.setProjDir(getProjDir("product"));
        psCfg.setParentPackage(getModelParentPackage("product"));
        modelGenConfigs.add(psCfg);

        GeneratorConfig stCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "st_");
        stCfg.setOutputDir(getModelOutputDir("trade"));
        stCfg.setProjDir(getProjDir("trade"));
        stCfg.setParentPackage(getModelParentPackage("trade"));
        modelGenConfigs.add(stCfg);

        GeneratorConfig pmCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "pm_");
        pmCfg.setOutputDir(getModelOutputDir("payment"));
        pmCfg.setProjDir(getProjDir("payment"));
        pmCfg.setParentPackage(getModelParentPackage("payment"));
        modelGenConfigs.add(pmCfg);

        GeneratorConfig bsCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "bs_");
        bsCfg.setOutputDir(getModelOutputDir("support"));
        bsCfg.setProjDir(getProjDir("support"));
        bsCfg.setParentPackage(getModelParentPackage("support"));
        modelGenConfigs.add(bsCfg);

        GeneratorConfig smCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "sm_");
        smCfg.setOutputDir(getModelOutputDir("system"));
        smCfg.setProjDir(getProjDir("system"));
        smCfg.setParentPackage(getModelParentPackage("system"));
        modelGenConfigs.add(smCfg);

        GeneratorConfig mmCfg = new GeneratorConfig(DB_URL, DB_USERNAME, DB_PASSWORD, AUTHOR, "mm_");
        mmCfg.setOutputDir(getModelOutputDir("message"));
        mmCfg.setProjDir(getProjDir("message"));
        mmCfg.setParentPackage(getModelParentPackage("message"));
        modelGenConfigs.add(mmCfg);

        /**
         *通过SQL生成各个模块包含的表名；
         SELECT CONCAT('umCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'um_%'
         UNION
         SELECT CONCAT('pvCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'pv_%'
         UNION
         SELECT CONCAT('psCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'ps_%'
         UNION
         SELECT CONCAT('stCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'st_%'
         UNION
         SELECT CONCAT('pmCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'pm_%'
         UNION
         SELECT CONCAT('bsCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'bs_%'
         UNION
         SELECT CONCAT('smCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'sm_%'
         UNION
         SELECT CONCAT('mmCfg.setTableNames(new String[] {', GROUP_CONCAT('"', t.TABLE_NAME, '"'), '});')
         FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = 'ecsp2' AND t.TABLE_NAME LIKE 'mm_%';
         */
        umCfg.setTableNames(new String[] {"um_buyer_enterprise","um_feedback","um_permission","um_role","um_role_permission","um_user","um_user_log","um_user_profile","um_user_role"});
        pvCfg.setTableNames(new String[] {"pv_enterprise_band_account","pv_enterprise_profile","pv_invoice_config","pv_personal_profile","pv_profile_review_record","pv_provider","pv_provider_contact","pv_provider_default_region","pv_sales_summary_of_provider","pv_sales_summary_of_provider_hist","pv_sp_admin_auth","pv_sp_default_region","pv_sp_qualification"});
        psCfg.setTableNames(new String[] {"ps_prod_comment","ps_provider_category","ps_sales_summary_of_service","ps_sales_summary_of_service_hist","ps_service_apply_region","ps_service_category","ps_service_introduction","ps_service_price_item","ps_service_sku"});
        stCfg.setTableNames(new String[] {"st_invoice","st_prod_item_in_cart","st_refund_record","st_service_contract","st_service_order","st_service_order_final_payment_record","st_service_order_memo","st_service_order_process","st_service_order_stage","st_subs_order","st_subs_order_item","st_subs_order_message"});
        pmCfg.setTableNames(new String[] {"pm_extract_cash","pm_payment","pm_recharge","pm_transaction_record"});
        bsCfg.setTableNames(new String[] {"bs_content","bs_content_catalog","bs_cs_dialog_record","bs_cs_work_order","bs_question"});
        smCfg.setTableNames(new String[] {"sm_config","sm_config_item","sm_dict","sm_dicttype","sm_district","sm_file"});
        mmCfg.setTableNames(new String[] {"mm_sms_checknum","mm_sms_log"});

        return modelGenConfigs;
    }

    public static void main(String[] args) {
        List<GeneratorConfig> modelGenConfigs = initModuleConfigs();
        Set<String> workingSets = new HashSet<>();

//        workingSets.add("um_");
//        workingSets.add("pv_");
//        workingSets.add("ps_");
        workingSets.add("st_");
//        workingSets.add("bs_");
//        workingSets.add("sm_");
//        workingSets.add("pm_");
//        workingSets.add("mm_");

        for (GeneratorConfig cfg : modelGenConfigs) {
            if (!workingSets.contains(cfg.getTablePrefix())) continue;

            System.out.println(String.format("========%s=========", cfg.getTablePrefix()));
            try {
                System.out.println(String.format("========Clean Dir = %s=========", cfg.getOutputDir()));
                FileUtils.deleteDirectory(new File(cfg.getOutputDir()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            MyBatisGenerator.generate(cfg);
            copyFileToProj(cfg.getOutputDir(), cfg.getProjDir(), cfg.getParentPackage());
        }
    }

    private static void copyFileToProj(String outputDir, String projDir, String packageName) {
        String packageDir = packageName.replace('.', '\\');

        String entitySrcDir = outputDir + '\\' + packageDir + "\\entity";
        String entityDstDir = projDir + "\\src\\main\\java\\" + packageDir + "\\entity";

        String daoSrcDir = outputDir + '\\' + packageDir + "\\dao";
        String daoDstDir = projDir + "\\src\\main\\java\\" + packageDir + "\\dao";

        String xmlSrcDir = outputDir + '\\' + packageDir + "\\xml";
        String xmlDstDir = projDir + "\\src\\main\\resources\\mybatis\\xml";

        try {
            FileUtils.copyDirectory(new File(entitySrcDir), new File(entityDstDir));
            FileUtils.copyDirectory(new File(daoSrcDir), new File(daoDstDir));
            FileUtils.copyDirectory(new File(xmlSrcDir), new File(xmlDstDir));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
