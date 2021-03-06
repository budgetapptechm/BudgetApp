package com.gene.app.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gene.app.dao.DBUtil;
import com.gene.app.model.BudgetSummary;
import com.gene.app.util.BudgetConstants;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class GetSummaryFromCache extends HttpServlet {
	DBUtil util = new DBUtil();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cCentre = "";
		String brand = "";
		if (req.getParameter("costCentre") != null) {
			cCentre = req.getParameter("costCentre").toString();
		}
		if (req.getParameter("brand") != null) {
			brand = req.getParameter("brand").toString();
		}
		BudgetSummary summary = new BudgetSummary();
		if(util.getSummaryFromCache(cCentre) != null){
			summary = util.getSummaryFromCache(cCentre);
		}
		session.setAttribute(BudgetConstants.REQUEST_ATTR_SUMMARY, summary);
		req.setAttribute("brandValue",brand);
		Gson gson = new Gson();
		resp.getWriter().write(gson.toJson(summary));
	}

}
