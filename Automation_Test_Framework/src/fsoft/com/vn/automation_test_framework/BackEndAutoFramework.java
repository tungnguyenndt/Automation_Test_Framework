package fsoft.com.vn.automation_test_framework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BackEndAutoFramework {
	public static Logger log = Logger.getLogger(BackEndAutoFramework.class.getName());
	private String sBEKeywordRetVal = "";
	private String sKeywordFileName = "";
	private String sKeywordTab = "";
	private String sTCFileNames = null;
	private String sTCTab = null;
	private String sTestCaseStr = null;
	private String sUserId = null;
	private String sPassword = null;
	private int iMaxParams = 0;
	private int iInParamCol = 0;
	private int iOutParamCol = 0;
	private int iRunId = 0;
	private int iTestCaseId = 0;
	private String sMoreInParams = null;
	private Map<String, Object> mStoreMultiData;
	private int iWebFrameWaitTime;
	private int iWebStaticExplicitWait;
	private int iMaxWebDrAttempts;
	private HashMap<String, String> sStoreKeys = new HashMap<String, String>();

	@Test
	public void mainMethod() throws Throwable {
		Properties prop = new Properties();

		try {
			// load a properties file
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

			sTCFileNames = prop.getProperty("TestSuitePath");
			sTCTab = prop.getProperty("TestCasesTab");
			iMaxParams = (int) Integer.parseInt(prop.getProperty("FPT.Automation.BE.MaxParams"));
			iInParamCol = (int) Integer.parseInt(prop.getProperty("FPT.Automation.BE.InputParamsColumn"));
			sMoreInParams = prop.getProperty("FPT.Automation.BE.IsMoreInputParams");
			mStoreMultiData = new HashMap<String, Object>();

			if (prop.getProperty("FPT.Automation.keywordFile") != null) {
				sKeywordFileName = prop.getProperty("FPT.Automation.keywordFile");
				sKeywordTab = prop.getProperty("FPT.Automation.keywordTab");
			}

			if (prop.getProperty("FPT.Automation.UI.MaxSwitchFrameWaitTime") != null)
				iWebFrameWaitTime = (int) Integer
						.parseInt(prop.getProperty("FPT.Automation.UI.MaxSwitchFrameWaitTime"));
			else
				iWebFrameWaitTime = 10;

			if (prop.getProperty("FPT.Automation.UI.ExtendExplicitWaitTime") != null)
				iWebStaticExplicitWait = (int) Integer
						.parseInt(prop.getProperty("FPT.Automation.UI.ExtendExplicitWaitTime"));
			else
				iWebStaticExplicitWait = 5;

			if (Character.toUpperCase(sMoreInParams.charAt(0)) == 'Y') {
				iOutParamCol = (int) Integer.parseInt(prop.getProperty("FPT.Automation.BE.OutputParamsColumnExt"));
			} else {
				iOutParamCol = (int) Integer.parseInt(prop.getProperty("FPT.Automation.BE.OutputParamsColumn"));
			}

			// Load Portal login info from config file
			if (prop.getProperty("FPT.Automation.UserName") != null
					&& prop.getProperty("FPT.Automation.Password") != null) {
				sUserId = prop.getProperty("FPT.Automation.UserName");
				sPassword = prop.getProperty("FPT.Automation.UserName");
				// store Login user name in param store key
				addKeyToList("user_name", sUserId);
			}

			if (sUserId == null || sPassword == null || sUserId.isEmpty()
					|| sPassword.isEmpty()) {
				log.fatal(
						"config.properties - AutomationDB.PortalUserId/Password property is not set. Set it with your Portal login Info. Program Terminated.");
				return;
			}
			
			log.debug("Test Cases Tab: " + sTCTab);
			// log.debug("Access information file path : " + sAccessInfoFileName);

		} catch (IOException e) {
			log.fatal("Issue loading config.properties");
			e.printStackTrace();
		}
		log.info("Root class Path :" + getCleanPath());
	}
	/*
	 * ========================================================================= ======================= MethodName :
	 * addKeyToList() Purpose : Used to add the new output key value to the output parameters list. Also used to update
	 * the value it it has been changed. Arguments : Key and its value. Returns : None
	 * ========================================================================= =======================
	 */
	public int addKeyToList(String sKey, String sKeyValue) {
		if (sStoreKeys.containsKey(sKey)) {
			sStoreKeys.put(sKey, sKeyValue);
			log.debug("Key value already exist and it has been overwritten with new value");
			return 1; // Returns 1 if its already exist.
		}

		sStoreKeys.put(sKey, sKeyValue);
		log.debug("Key=" + sKey + "----Key Value=" + sStoreKeys.get(sKey));

		return 0; // Returns 0 if its a new variable added.
	}
	/*
	 * ========================================================================= ======================= MethodName :
	 * getCleanPath() Purpose : Used to get the root directory. Arguments : None Returns : Root directory
	 * ========================================================================= =======================
	 */
	public static String getCleanPath() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File classpathRoot = new File(classLoader.getResource("").getPath());

		return classpathRoot.getPath();
	}
	/*
	 * ========================================================================= ======================= MethodName :
	 * getKeywordDetails() Purpose : Read the Keywords.xlsx file and store the details in the HashMap. Arguments :
	 * Keyword file name and the tab. Returns : None
	 * ========================================================================= =======================
	 */
	
}
