package com.gene.app.util;

public interface BudgetConstants {

	// DBUtil.java
	public static final String[] months = { "JAN", "FEB", "MAR", "APR", "MAY",
			"JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC", "TOTAL" };
	public static final int GTF_Percent_Total = 100;
	public static final String total = "TOTAL";
	public static final String seperator = " - ";
	public static final String GTFReportFilterString = "key == keyParam";
	public static final String GTFReportParameters = "String keyParam";
	public static final String GTFReportOrderingParameters = "flag asc";
	public static final String GTFReportOrderingParameters_getReport = "flag asc, projectName asc, gMemoryId asc";
	public static final String GTFReportOrderingParameters_updateReports = "costCenter asc";

	// This variable is used in UI layer and service layer
	public static final String costCenter = "307673";
	public static final String dataYEAR = "2015";

	// StoreReport.java
	public static final String contentType = "text/plain";
	public static final String objArray = "objarray";
	public static final String loggedInUser = "loggedInUser";
	public static final String status_New = "New";
	public static final String status_Active = "Active";
	public static final String GTFReport_ProjectWBS = "4";
	public static final String GTFReport_ProjectName = "1";
	public static final String GTFReport_Status = "2";
	public static final String GTFReport_Requestor = "3";
	public static final String GTFReport_WBS_Name = "5";
	public static final String GTFReport_SubActivity = "6";
	public static final String GTFReport_Brand = "7";
	public static final String GTFReport_Percent_Allocation = "8";
	public static final String GTFReport_PoNumber = "9";
	public static final String GTFReport_PoDesc = "10";
	public static final String GTFReport_Vendor = "11";
	public static final String GTFReport_Remarks = "25";
	public static final String REQUEST_ATTR_GTFReports = "gtfreports";
	public static final String REQUEST_ATTR_SUMMARY = "summary";
	public static final String GetReport_REDIRECTURL = "/listProjects";
	public static final String USER_ROLE_PRJ_OWNER = "prjOwner";
	public static final String KEY = "key";
	public static final String CELL_VALUE = "cellValue";
	public static final String CELL_NUM = "celNum";

	public static final int CELL_PONUMBER = -3;
	public static final int CELL_REMARKS = 13;
	public static final int CELL_PNAME = -11;
	public static final int CELL_BRAND = -10;
	public static final int CELL_PWBS = -6;
	public static final int CELL_SUBACTVTY = -5;
	public static final int CELL_VENDOR = -2;
	public static final int CELL_UNIT = -1;
	public static final int CELL_GMEMORI_ID = -8;
	public static final String isMultiBrand = "37";
	public static final String multiBrandInput = "36";
	public static final String MAP_TYPE = "mapType";

	// New Store Report

	public static final String New_GTFReport_gMemoriId = "0";
	public static final String New_GTFReport_ProjectOwner = "1";
	public static final String New_GTFReport_ProjectName = "2";
	public static final String New_GTFReport_Project_WBS = "3";
	public static final String New_GTFReport_Status = "New";
	public static final String New_GTFReport_SubActivity = "5";
	public static final String New_GTFReport_Brand = "6";
	public static final String New_GTFReport_Percent_Allocation = "7";
	public static final String New_GTFReport_PoNumber = "8";
	public static final String New_GTFReport_PoDesc = "9";
	public static final String New_GTFReport_Vendor = "10";
	public static final String New_GTFReport_Unit = "49";
	public static final String New_GTFReport_Remarks = "25";
	public static final String New_isMultiBrand = "37";
	public static final String New_multiBrandInput = "36";
	public static final String GMBT_SUMMARY = "summary";

	public static final String OLD = "old";
	public static final String NEW = "new";

	// cell numbers edit projects
	public static final String NUMBER_OF_HDN_COLS = "6";

	public static final String STATUS_CELL = "0";
	public static final String PROJECT_NAME_CELL = "1";
	public static final String BRAND_CELL = "2";
	public static final String $_IN_THOUSAND_CELL = "3";
	public static final String GMEMORI_ID_CELL = "4";
	public static final String PROJECT_OWNER_CELL = "5";
	public static final String PROJECT_WBS_CELL = "6";
	public static final String SUBACTIVITY_CELL = "7";
	public static final String ALLOCATION_PERCENTAGE_CELL = "8";
	public static final String PO_NUMBER_CELL = "9";
	public static final String VENDOR_CELL = "10";
	public static final String UNIT_CELL = "11";
	public static final String JAN_CELL = "12";
	public static final String FEB_CELL = "13";
	public static final String MAR_CELL = "14";
	public static final String APR_CELL = "15";
	public static final String MAY_CELL = "16";
	public static final String JUN_CELL = "17";
	public static final String JUL_CELL = "18";
	public static final String AUG_CELL = "19";
	public static final String SEP_CELL = "20";
	public static final String OCT_CELL = "21";
	public static final String NOV_CELL = "22";
	public static final String DEC_CELL = "23";
	public static final String TOTAL_CELL = "24";
	public static final String REMARK_CELL = "25";

	// field numbers edit projects

	public static final String STATUS_FIELD = "30";
	public static final String PROJECT_NAME_FIELD = "2";
	public static final String BRAND_FIELD = "6";
	public static final String $_IN_1000_FIELD = "11";
	public static final String GMEMORI_ID_FIELD = "0";
	public static final String PROJECT_OWNER_FIELD = "1";
	public static final String PROJECT_WBS_FIELD = "3";
	public static final String SUBACTIVITY_FIELD = "5";
	public static final String ALLOCATION_PERCENTAGE_FIELD = "7";
	public static final String PO_NUMBER_FIELD = "8";
	public static final String VENDOR_FIELD = "10";
	public static final String UNIT_FIELD = "49";
	public static final String JAN_FIELD = "12";
	public static final String FEB_FIELD = "13";
	public static final String MAR_FIELD = "14";
	public static final String APR_FIELD = "15";
	public static final String MAY_FIELD = "16";
	public static final String JUN_FIELD = "17";
	public static final String JUL_FIELD = "18";
	public static final String AUG_FIELD = "19";
	public static final String SEP_FIELD = "20";
	public static final String OCT_FIELD = "21";
	public static final String NOV_FIELD = "22";
	public static final String DEC_FIELD = "23";
	public static final String TOTAL_FIELD = "24";
	public static final String REMARK_FIELD = "25";
	public static final String PROJECT_NAME_SEARCH_FIELD = "29";
	public static final String PROJECT_NAME_GID_FIELD = "42";
	public static final String BRAND_SEARCH_FIELD = "28";
	public static final String BRAND_DUPLICATE_FIELD = "44";

	// cell numbers edit project multiple brand pop up
	public static final String MB_CHECKBOX_CELL = "0";
	// public static final String MB_PROJECT_NAME_CELL = "1";
	public static final String MB_PROJECT_OWNER_CELL = "4";
	// public static final String MB_GMEMORI_ID_CELL = "3";
	public static final String MB_BRAND_CELL = "1";
	public static final String MB_$_IN_THOUSAND_CELL = "2";
	public static final String MB_ALLOCATION_PERCENTAGE_CELL = "3";
	public static final String GMEMORI_COLLECTION = "gMemoriIdCollection";
	public static final String USER_ROLE_BRAND_OWNER = "brandOwner";
	public static final String [] costCenterList = {"7527","7034","7035","7121","7712","7135","7713","7428","7512","7574","7136","7138"};//"7004","7512","7138","7136"};

	
	public static final String FORECAST = "Forecast";
	public static final String ACCRUAL = "Accrual";
	public static final String ANNUAL_TARGET = "Quarterly Target"; 
	public static final String FORECAST_LTS = "Quarterly LTS";
	public static final String QUARTERLY_TARGET = "Quarterly Target"; 
	public static final String QUARTERLY_LTS = "Quarterly LTS";
	public static final String BUCKET_NAME = "budget-mgmt-tool-bucket";
	public static final int TIME_OUT_PERIOD = 20000;
	
	// RecoveryServlet.java
	public static final String FILENAME = "fileName";
	public static final String STATUS = "status";
	public static final String COUNT = "count";
	

}
