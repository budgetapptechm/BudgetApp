package com.gene.app.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gene.app.bean.BudgetSummary;
import com.gene.app.bean.GtfReport;
import com.gene.app.util.BudgetConstants;
import com.gene.app.util.DBUtil;

@SuppressWarnings("serial")
public class AutoSaveData extends HttpServlet {
	DBUtil util = new DBUtil();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String keyNum = req.getParameter(BudgetConstants.KEY).toString();
		String cellValue = req.getParameter(BudgetConstants.CELL_VALUE)
				.toString();
		String cellNum = req.getParameter(BudgetConstants.CELL_NUM).toString();
		GtfReport gtfReportObj = null;
		Map<String, GtfReport> gtfReportMap = util
				.getAllReportDataFromCache(BudgetConstants.costCenter);
		if (keyNum != null && !"".equalsIgnoreCase(keyNum.trim())) {
			gtfReportObj = gtfReportMap.get(keyNum);
			if (gtfReportObj != null) {
				if (Integer.parseInt(cellNum) == BudgetConstants.CELL_REMARKS) {
					String remarks = cellValue;
					gtfReportObj.setRemarks(remarks);
					gtfReportMap.put(keyNum, gtfReportObj);
				} else {
					Map<String, Double> plannedMap = gtfReportObj
							.getPlannedMap();
					if (plannedMap != null) {
						plannedMap.put(BudgetConstants.months[Integer
								.parseInt(cellNum)], Double
								.parseDouble(cellValue));
						gtfReportObj.setPlannedMap(plannedMap);
						gtfReportMap.put(keyNum, gtfReportObj);
					}
				}
			}
		}

		String sessionKey = (String) session.getAttribute(BudgetConstants.KEY);
		util.saveAllReportDataToCache(BudgetConstants.costCenter, gtfReportMap);
		if (sessionKey != null && sessionKey.isEmpty()) {
			sessionKey = keyNum;
		}
		GtfReport sessionGtfReport = util.readReportDataFromCache(sessionKey,
				BudgetConstants.costCenter);
		if (keyNum == null && cellValue == null && cellNum == null
				&& sessionKey != null) {
			util.saveDataToDataStore(sessionGtfReport);
		}

		if ((keyNum != null && sessionKey != null)
				&& !(keyNum.equals(sessionKey))) {
			util.saveDataToDataStore(sessionGtfReport);
		}
		BudgetSummary summary = util
				.readBudgetSummary(BudgetConstants.costCenter);
		session.setAttribute(BudgetConstants.KEY, keyNum);
		req.setAttribute(BudgetConstants.REQUEST_ATTR_SUMMARY, summary);
	}

}
